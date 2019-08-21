package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.S;

/**
 * A class representing the S piece.
 */
public class SPiece extends AbstractGamePiece{

  /**
   * A default constructor for the S piece with the green color.
   */
  public SPiece() {
    this.color = Color.GREEN;
    this.type = S;
  }

  @Override
  protected void initBlock() {
    // creates the S-shaped block
    block[1][1] = true;
    block[1][0] = true;
    block[0][1] = true;
    block[0][2] = true;
  }
}
