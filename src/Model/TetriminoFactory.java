package Model;

import Model.Tetrominos.GamePiece;
import Model.Tetrominos.IPiece;
import Model.Tetrominos.JPiece;
import Model.Tetrominos.LPiece;
import Model.Tetrominos.OPiece;
import Model.Tetrominos.SPiece;
import Model.Tetrominos.TPiece;
import Model.Tetrominos.ZPiece;

/**
 * A factory class for producing Tetrominos.
 */
public class TetriminoFactory {

  /**
   * A factory method to create Tetrominos given an enum identifier.
   *
   * @param e the type of GamePiece desired
   * @return A GamePiece with default values
   */
  public static GamePiece makePiece(TetrisEnum e) {
    switch (e) {
      case I: return new IPiece();
      case J: return new JPiece();
      case O: return new OPiece();
      case L: return new LPiece();
      case S: return new SPiece();
      case Z: return new ZPiece();
      case T: return new TPiece();
      default:
        throw new IllegalArgumentException("Invalid Piece");
    }
  }
}
