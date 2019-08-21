import Controller.Controller;
import Controller.GameController;
import Model.Game;
import Model.GameModel;
import View.GameView;
import View.IView;

/**
 * The main class where the Tetris game will start.
 */
public class TetrisGame {

  /**
   * Initiates a game of Tetris.
   *
   * @param args arguments for the main method but are not required here
   */
  public static void main(String... args) {
    Game model = new GameModel();
    IView view = new GameView();
    Controller controller = new GameController(model, view);

    controller.play();
  }
}
