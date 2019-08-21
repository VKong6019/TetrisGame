package Controller;

/**
 * The operations available on a controller listener for the View.
 */
public interface ViewListener {

  /**
   * Moves the current GamePiece to the right a block.
   */
  void moveRight();

  /**
   * Moves the current GamePiece to the left a block.
   */
  void moveLeft();

  /**
   * Moves the current GamePiece down a block.
   */
  void moveDown();

  /**
   * Drops the current GamePiece until it collides with another block or the bottom of the board.
   */
  void drop();

  /**
   * Rotates the current GamePiece clockwise.
   */
  void rotate();

  /**
   * Pauses the timer to stop the game temporarily.
   */
  void pause();

  /**
   * Switches the current GamePiece with the currently held GamePiece or a new block is generated.
   */
  void hold();

  /**
   * Gets the current score of the game.
   *
   * @return the user's score
   */
  int score();

  /**
   * Starts the timer again to resume the game from the paused state.
   */
  void resume();

  /**
   * Restarts the game with a new board and score.
   */
  void restart();

  /**
   * Sets the start value to the given {@code b} value.
   *
   * @param b the value to be set
   */
  void canStart(boolean b);

  /**
   * Finds the amount of lines cleared after a block is placed.
   *
   * @return the number of lines cleared on the board
   */
  int lines();
}
