package View;

import java.awt.Color;

import Model.Tetrominos.GamePiece;

/**
 * The operations on a drawable class implementing this interface.
 */
public interface IDrawing {

  /**
   * Graphically draws out the given {@code saved} and {@code board} on this panel.
   *
   * @param saved the current saved piece
   * @param board the current state of the board
   */
  void draw(GamePiece saved, Color[][] board);
}
