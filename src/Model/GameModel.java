package Model;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import java.util.stream.IntStream;

import Model.Tetrominos.GamePiece;

import static Model.TetriminoFactory.makePiece;
import static Model.TetrisEnum.I;
import static Model.TetrisEnum.J;
import static Model.TetrisEnum.O;
import static Model.TetrisEnum.S;
import static Model.TetrisEnum.Z;
import static Model.TetrisEnum.L;
import static Model.TetrisEnum.T;

/**
 * The implementations of the Game interface's operations. One GameModel is a game of TetrisGame.
 */
public class GameModel implements Game {
  private int score;
  private final int col;
  private final int row;
  private Color[][] board;
  private Color[][] tempBoard;
  private final GamePiece[] bucket;
  private GamePiece[] workBucket;
  private GamePiece currentPiece;
  private GamePiece savedPiece;
  private Point currPosition;
  private boolean gameOver;
  private boolean canHold;
  private int linesRemoved;

  /**
   * A default GameModel constructor that has no parameters.
   */
  public GameModel() {
    score = 0;
    linesRemoved = 0;
    col = 10;
    row = 18;
    bucket = new GamePiece[7];
    workBucket = new GamePiece[0];
    board = new Color[row][col];
    tempBoard = new Color[row][col];
    currPosition = new Point(3, -4);
    gameOver = false;
    canHold = true;
    initBlockBucket();
    currentPiece = randomPiece();
    initBoard();
  }

  private void initBoard() {
    Arrays.stream(board).forEach(r -> Arrays.fill(r, Color.BLACK));
  }

  private void initBlockBucket() {
    TetrisEnum[] list = new TetrisEnum[]{I, J, O, S, Z, T, L};

    IntStream.range(0, 7).forEach(i -> bucket[i] = makePiece(list[i]));
  }

  private GamePiece randomPiece() {
    Random ran = new Random();

    if (workBucket.length == 0) workBucket = Arrays.copyOf(bucket, 7);

    int index = ran.nextInt(workBucket.length);
    GamePiece toReturn = workBucket[index];
    List<GamePiece> temp = new ArrayList<>(Arrays.asList(workBucket));
    temp.remove(index);
    workBucket = temp.toArray(new GamePiece[workBucket.length - 1]);

    return toReturn;
  }

  @Override
  public void rotateRight() {
    this.currentPiece.rotateR();

    if (attachable(0, 1)) wallTest();

    if (attachable(0, 1)) {
      currentPiece.rotateR();
      currentPiece.rotateR();
      currentPiece.rotateR();
    }
  }

  private void wallTest() {
    if (!attachable(1, 0) && attachable(0, 0)) {
      currPosition.x += 1;
      if (attachable(0, 0)) currPosition.x -= 1;
    }

    if (!attachable(-1, 0) && attachable(0, 0)) {
      currPosition.x -= 1;
      if (attachable(0, 0)) currPosition.x += 1;
    }
  }

  @Override
  public int getScore() {
    return this.score;
  }

  @Override
  public Color[][] getGameState() {
    IntStream.range(0, board.length).forEach(i -> tempBoard[i]
            = Arrays.copyOf(board[i], 10));

    attachToThis(false);
    return this.tempBoard;
  }

  @Override
  public GamePiece getSavedPiece() {
    return this.savedPiece;
  }

  @Override
  public void save() {
    if (canHold) {
      GamePiece storeCurr = currentPiece.copy();

      if (savedPiece == null) {
        currentPiece = randomPiece();
        currPosition.setLocation(3, -4);
      } else {
        currentPiece = savedPiece.copy();
      }

      savedPiece = storeCurr;
      canHold = false;
    }
  }

  @Override
  public void moveX(boolean dir) {
    int x = dir ? 1 : -1;

    if (!attachable(x, 0)) {
      currPosition.x += x;
    }

  }

  @Override
  public void softDrop() {
    currPosition.y += 1;

    if (attachable(0, 1)) installAndReset();
  }

  @Override
  public void hardDrop() {
    while (!attachable(0, 1)) {
      currPosition.y += 1;
    }

    installAndReset();
  }

  private void installAndReset() {
    attachToThis(true);
    removeLines();
    currentPiece = randomPiece();
    currPosition.setLocation(3, -4);

    if (!canHold) canHold = true;
  }

  @Override
  public boolean getIsGameOver() {
    return this.gameOver;
  }

  @Override
  public void clear() {
    savedPiece = null;
    initBoard();
    workBucket = Arrays.copyOf(bucket, 7);
    currentPiece = randomPiece();
    canHold = true;
    score = 0;
    currPosition.setLocation(3, -4);
  }

  private void attachToThis(boolean b) {
    boolean[][] piece = currentPiece.getBlock();

    Color[][] well = b ? this.board : this.tempBoard;

    for (int i = 0; i < piece.length; i++) {
      for (int j = 0; j < piece.length; j++) {

        //check for game-over here
        if (b && piece[i][j] && currPosition.y + i < 0) {
          gameOver = true;
          return;
        }

        if (piece[i][j] && currPosition.y + i >= 0) {
          well[currPosition.y + i][currPosition.x + j] = currentPiece.getColor();
        }
      }
    }

  }

  private boolean attachable(int x, int y) {
    boolean[][] piece = currentPiece.getBlock();
    int yTest = currPosition.y + y;
    int xTest = currPosition.x + x;

    for (int i = 0; i < piece.length; i++) {
      for (int j = 0; j < piece.length; j++) {
        int to_x = j + xTest;
        int to_y = i + yTest;
        if (piece[i][j]) {
          if (0 > to_x || to_x >= col || to_y >= row ||
                  to_y >= 0 && !board[to_y][to_x].equals(Color.BLACK)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private void removeLines() {
    linesRemoved = 0;

    for (int i = 0; i < board.length; i++) {
      if (canRemove(board[i])) {
        linesRemoved++;
        removeAndUpdate(i);
      }
    }

    addPoints(linesRemoved);
  }

  private boolean canRemove(Color[] row) {
    for (Color x : row) {
      if (Color.BLACK.equals(x)) {
        return false;
      }
    }

    return true;
  }

  private void removeAndUpdate(int row) {
    Color[] blank = new Color[col];
    Arrays.fill(blank, Color.BLACK);

    for (int i = row; i >= 0; i--) {
      board[i] = i == 0 ? blank : Arrays.copyOf(board[i - 1], col);
    }
  }

  private void addPoints(int num) {
    switch (num) {
      case 1:
        score += 100;
        break;
      case 2:
        score += 300;
        break;
      case 3:
        score += 500;
        break;
      case 4:
        score += 800;
        break;
    }
  }

  @Override
  public int getLinesRemoved() {
    return this.linesRemoved;
  }

  @Override
  public void setGameOver(boolean b) {
    this.gameOver = b;
  }

}
