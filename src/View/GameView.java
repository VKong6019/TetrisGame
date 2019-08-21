package View;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import Controller.ViewListener;
import Model.Tetrominos.GamePiece;

/**
 * A class representing the Tetris game's view to the user.
 */
public class GameView extends JFrame implements IView, ActionListener {
  private final Drawing panel;
  private ViewListener listener;
  private final JPanel scoreboard;
  private final JLabel points;
  private final JLabel finalPoint;
  private final JPanel optionPanel;
  private boolean canPress;
  private boolean canHear;
  private Clip clip;
  private final CardLayout cardPanels;
  private final Container c;
  private final JPanel gameOverPanel;
  private final JPanel startPanel;
  private final JButton soundButton;

  /**
   * A default GameView constructor that initializes the components for user visibility.
   */
  public GameView() {
    super();
    clip = null;
    panel = new Drawing();
    panel.setBackground(new Color(155, 195, 212));
    cardPanels = new CardLayout();
    optionPanel = new JPanel();
    scoreboard = new JPanel();
    startPanel = new JPanel();
    gameOverPanel = new JPanel();
    soundButton = new JButton();
    points = new JLabel("0");
    finalPoint = new JLabel("0");
    c = getContentPane();
    c.setLayout(cardPanels);
    initScoreboard();
    initStartPanel();
    JPanel panel2 = new JPanel(new BorderLayout());
    panel2.add(panel, BorderLayout.CENTER);
    panel2.add(scoreboard, BorderLayout.SOUTH);
    panel2.add(optionPanel, BorderLayout.EAST);
    c.add(startPanel);
    c.add(panel2);
    c.add(gameOverPanel);

    setSize(550, 600);
    setTitle("Vera's Tetris Game");
    setIconImage(new ImageIcon("resources//tetris-icon.png").getImage());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

    InputMap buttonMap = (InputMap) UIManager.get("Button.focusInputMap");
    buttonMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
    buttonMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

    InputMap im = panel2.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = panel2.getActionMap();

    im.put(KeyStroke.getKeyStroke("DOWN"), "Down");
    im.put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
    im.put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
    im.put(KeyStroke.getKeyStroke("UP"), "Rotate");
    im.put(KeyStroke.getKeyStroke("C"), "Drop");
    im.put(KeyStroke.getKeyStroke("X"), "Hold");

    am.put("Down", new TetrisAction("Down"));
    am.put("Move Right", new TetrisAction("Move Right"));
    am.put("Move Left", new TetrisAction("Move Left"));
    am.put("Rotate", new TetrisAction("Rotate"));
    am.put("Drop", new TetrisAction("Drop"));
    am.put("Hold", new TetrisAction("Hold"));

    File file = new File("resources\\tetris-themesong.wav");
    try {
      AudioInputStream audio = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(audio);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      // don't do anything if exception is caught
    }

    initGameOverPanel();
    initOptionPanel();
    canPress = false;
    canHear = true;
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String selectSound = "resources\\selection.wav";
    switch (e.getActionCommand()) {
      case "Pause":
        listener.pause();
        canPress = false;
        openSound(selectSound);
        break;
      case "Resume":
        listener.resume();
        canPress = true;
        openSound(selectSound);
        break;
      case "New Game":
        listener.restart();
        canPress = true;
        openSound(selectSound);
        break;
      case "Sound":
        canHear = !canHear;
        String path = canHear ? "resources\\speaker.png" : "resources\\no-sound.png";
        soundButton.setIcon(new ImageIcon(path));
        openSound(selectSound);
        playSound();
        break;
      case "Restart":
        cardPanels.next(c);
        listener.canStart(false);
        listener.restart();
        openSound(selectSound);
        break;
      case "Start":
        cardPanels.next(c);
        canPress = true;
        listener.canStart(true);
        openSound(selectSound);
        break;
    }
  }

  private class TetrisAction extends AbstractAction {

    String command;

    TetrisAction(String command) {
      this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (canPress) {
        switch (command) {
          case "Down":
            listener.moveDown();
            removedLineSound();
            updatePanel();
            break;
          case "Move Right":
            listener.moveRight();
            updatePanel();
            break;
          case "Move Left":
            listener.moveLeft();
            updatePanel();
            break;
          case "Rotate":
            listener.rotate();
            updatePanel();
            break;
          case "Drop":
            listener.drop();
            updatePanel();
            openSound("resources\\fall.wav");
            removedLineSound();
            break;
          case "Hold":
            listener.hold();
            updatePanel();
            break;
        }
      }
    }
  }

  @Override
  public void display(GamePiece saved, Color[][] board) {
    panel.draw(saved, board);
  }

  @Override
  public void addListener(ViewListener listener) {
    this.listener = listener;
  }

  @Override
  public void showGameOver() {
    clip.stop();
    cardPanels.next(c);
    canPress = false;
    openSound("resources//gameover.wav");
  }

  private void initScoreboard() {
    points.setFont(new Font("Impact", Font.BOLD, 18));
    scoreboard.add(points);
    scoreboard.setBackground(new Color(113, 238, 184));
  }

  private void initOptionPanel() {
    optionPanel.setBackground(new Color(155, 195, 212));
    optionPanel.setLayout(new GridLayout(2, 1));
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

    JButton pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause");
    pauseButton.addActionListener(this);

    JButton resumeButton = new JButton("Resume");
    resumeButton.setActionCommand("Resume");
    resumeButton.addActionListener(this);

    JButton restartButton = new JButton("New Game");
    restartButton.setActionCommand("New Game");
    restartButton.addActionListener(this);

    ImageIcon sound = new ImageIcon("resources\\speaker.png");
    soundButton.setIcon(sound);
    soundButton.setActionCommand("Sound");
    soundButton.addActionListener(this);

    JPanel panelInfo = new JPanel(new GridLayout(2, 1));
    panelInfo.setBackground(new Color(155, 195, 212));

    JLabel controlInfo = new JLabel("Controls");
    controlInfo.setFont(new Font("Impact", Font.BOLD, 18));
    controlInfo.setForeground(Color.RED);

    String msg = "<html>Up - Rotate Block<br>Down - Soft Drop<br>Right - Move Right<br>Left - " +
            "Move Left<br>C - Hard Drop<br>X- Hold Block</html>";
    JLabel controlInfo2 = new JLabel(msg);
    controlInfo2.setFont(new Font("Impact", Font.BOLD, 16));
    controlInfo2.setForeground(new Color(0, 0, 50));

    panelInfo.add(controlInfo);
    panelInfo.add(controlInfo2);

    buttonPanel.add(pauseButton);
    buttonPanel.add(resumeButton);
    buttonPanel.add(restartButton);
    buttonPanel.add(soundButton);
    optionPanel.add(panelInfo);
    optionPanel.add(buttonPanel);
  }

  private void updatePanel() {
    panel.repaint();
    points.setText(Integer.toString(listener.score()));
    finalPoint.setText(Integer.toString(listener.score()));
  }

  private void playSound() {
    if (canHear) {
      clip.start();
    } else {
      clip.stop();
    }
  }

  private void initGameOverPanel() {
    gameOverPanel.setBackground(Color.BLACK);
    gameOverPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel gmLogo = new JLabel();
    gmLogo.setIcon(new ImageIcon("resources\\gameover-logo.jpg"));
    JLabel finalScore = new JLabel("Final Score:");
    finalScore.setForeground(Color.RED);
    finalScore.setFont(new Font("Impact", Font.BOLD, 18));
    finalPoint.setFont(new Font("Impact", Font.BOLD, 18));
    finalPoint.setForeground(Color.RED);
    JButton restart = new JButton("Back to Title Screen");
    restart.setActionCommand("Restart");
    restart.addActionListener(this);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gameOverPanel.add(gmLogo, gbc);
    gbc.gridy = 1;
    gameOverPanel.add(finalScore, gbc);
    gbc.gridy = 2;
    gameOverPanel.add(finalPoint, gbc);
    gbc.gridy = 3;
    gameOverPanel.add(restart, gbc);
  }

  private void initStartPanel() {
    startPanel.setBackground(Color.BLACK);
    startPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel logo = new JLabel();
    logo.setIcon(new ImageIcon("resources\\logo.png"));
    JButton start = new JButton("Play Game");
    start.setActionCommand("Start");
    start.addActionListener(this);
    JLabel msg = new JLabel("Can you get the highest score?");
    msg.setFont(new Font("Impact", Font.BOLD, 16));
    msg.setForeground(Color.ORANGE);

    gbc.gridx = 1;
    gbc.gridy = 0;
    startPanel.add(logo, gbc);
    gbc.gridy = 1;
    startPanel.add(start, gbc);
    gbc.gridy = 2;
    startPanel.add(msg, gbc);
  }

  private void openSound(String path) {
    File pathFile = new File(path);
    try {
      AudioInputStream input = AudioSystem.getAudioInputStream(pathFile);
      Clip clip = AudioSystem.getClip();
      clip.open(input);
      clip.start();
    } catch (Exception e) {
      //do nothing since no sound will play if file isn't found
    }
  }

  private void removedLineSound() {
    if (listener.lines() == 4) {
      openSound("resources\\clear.wav");
    } else if (listener.lines() != 0) {
      openSound("resources\\line.wav");
    }
  }
}
