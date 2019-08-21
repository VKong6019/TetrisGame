package View;

import java.awt.Color;

import Controller.ViewListener;
import Model.Tetrominos.GamePiece;

/**
 * The operations available on the View.
 */
public interface IView {

  /**
   * Displays a running game with the current Tetris board and saved GamePiece.
   *
   * @param saved the current saved piece to be drawn
   * @param board the current state of the board to be drawn
   */
  void display(GamePiece saved, Color[][] board);

  /**
   * Adds a listener to this View, usually a controller, to communicate with the model.
   *
   * @param listener the ViewListener to be added to this View
   */
  void addListener(ViewListener listener);

  /**
   * Tells this View to show the game over state of this game.
   */
  void showGameOver();
}
