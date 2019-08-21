package Model;

import java.awt.Color;

import Model.Tetrominos.GamePiece;

/**
 * The operations for a game of TetrisGame; One Game object is its own TetrisGame game.
 */
public interface Game {

  /**
   * Rotates the current piece to the right clockwise 90 degrees.
   */
  void rotateRight();

  /**
   * Gets the current score of this game.
   *
   * @return the current score
   */
  int getScore();

  /**
   * Gets the current game state represented in an array of Colors, where black is an empty space
   * and all others are GamePieces attached to the board.
   *
   * @return a 2D Color array of the board
   */
  Color[][] getGameState();

  /**
   * Gets the current saved GamePiece.
   *
   * @return the saved GamePiece
   */
  GamePiece getSavedPiece();

  /**
   * Switches the current and saved GamePiece to each other. If the saved game piece is null,
   * meaning no piece have been saved before, then the saved game piece takes the current game piece
   * and the current game piece is given a new random game piece.
   */
  void save();

  /**
   * Moves the current GamePiece position horizontally given the {@code dir} value. If {@code dir}
   * is true, then the piece attempts to move right and left otherwise.
   *
   * @param dir the direction the current GamePiece should move.
   */
  void moveX(boolean dir);

  /**
   * Moves the current GamePiece's position down by 1.
   */
  void softDrop();

  /**
   * Moves the current GamePiece position down until it reaches collision with another piece or
   * the bottom of the board.
   */
  void hardDrop();

  /**
   * Gets the game over state, where true represents the game is done and false
   * represents the game continues for play.
   *
   * @return the game over boolean state
   */
  boolean getIsGameOver();

  /**
   * Clears this game of Tetris by resetting the board, GamePieces, and score.
   */
  void clear();

  /**
   * Gets the number of lines removed from the board.
   *
   * @return the number of lines removed
   */
  int getLinesRemoved();

  /**
   * Sets the game over state to the given {@code b} state.
   *
   * @param b the new game over state of this game
   */
  void setGameOver(boolean b);

}
