package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.T;

/**
 * A class representing the T piece.
 */
public class TPiece extends AbstractGamePiece{

  /**
   * A default constructor for the T piece with the color magenta.
   */
  public TPiece() {
    this.color = Color.MAGENTA;
    this.type = T;
  }

  @Override
  protected void initBlock() {
    // creates the T-shaped block
    block[1][1] = true;
    block[1][0] = true;
    block[1][2] = true;
    block[0][1] = true;
  }
}
