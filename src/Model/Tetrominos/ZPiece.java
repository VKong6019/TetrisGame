package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.T;

/**
 * A class representing the Z piece.
 */
public class ZPiece extends AbstractGamePiece {

  /**
   * A default constructor for the Z piece with the red color.
   */
  public ZPiece() {
    this.color = Color.RED;
    this.type = T;
  }

  @Override
  protected void initBlock() {
    // creates the Z-shaped block
    block[1][1] = true;
    block[1][2] = true;
    block[0][0] = true;
    block[0][1] = true;
  }
}
