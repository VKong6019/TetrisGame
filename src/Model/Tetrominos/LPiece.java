package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.L;

/**
 *  A class representing the L piece.
 */
public class LPiece extends AbstractGamePiece {

  /**
   * A default constructor for the L Piece with the orange color.
   */
  public LPiece() {
    this.color = Color.ORANGE;
    this.type = L;
  }

  @Override
  protected void initBlock() {
    // creates the L-shaped block
    block[0][1] = true;
    block[1][1] = true;
    block[2][1] = true;
    block[2][2] = true;
  }
}
