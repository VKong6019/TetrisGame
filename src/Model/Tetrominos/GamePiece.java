package Model.Tetrominos;

import java.awt.Color;

/**
 * Operations on a TetrisGame game piece.
 */
public interface GamePiece {

  /**
   * Gets the color of this GamePiece.
   *
   * @return this piece's color
   */
  Color getColor();

  /**
   * Gets the boolean array representation of this GamePiece.
   *
   * @return a boolean array with spaces as false and the parts of the GamePiece as true
   */
  boolean[][] getBlock();

  /**
   * Rotates this GamePiece 90 degrees clockwise.
   */
  void rotateR();

  /**
   * Gets the array representation of this GamePiece in a string form.
   *
   * @return the String representation of the GamePiece's array form.
   */
  String getBlockString();

  /**
   * Creates a copy of this GamePiece's fields.
   *
   * @return a copy of this GamePiece
   */
  GamePiece copy();

  /**
   * Sets this GamePiece's block representation to the given block {@code toSet}.
   *
   * @param toSet the boolean array to be set from
   */
  void setBlock(boolean[][] toSet);
}
