package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.J;

/**
 * A class representing the J piece.
 */
public class JPiece extends AbstractGamePiece {

  /**
   * A default constructor for the J piece with the blue color.
   */
  public JPiece() {
    this.color = Color.BLUE;
    this.type = J;
  }

  @Override
  protected void initBlock() {
    // creates the J-shaped block
    block[0][1] = true;
    block[1][1] = true;
    block[2][1] = true;
    block[2][0] = true;
  }
}
