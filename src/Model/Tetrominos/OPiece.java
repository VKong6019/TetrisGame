package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.O;

/**
 * A class representing the O piece.
 */
public class OPiece extends AbstractGamePiece {

  /**
   * A default constructor for the O piece with the color yellow.
   */
  public OPiece() {
    this.color = Color.YELLOW;
    this.type = O;
  }

  protected void initBlock() {
    // creates the O shaped block
    block[2][1] = true;
    block[2][2] = true;
    block[3][1] = true;
    block[3][2] = true;
  }

  // rotating the O piece will simply "return" the same state
  @Override
  public void rotateR() {
  }
}
