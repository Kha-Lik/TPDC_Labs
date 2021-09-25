import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class BounceFrame extends JFrame {
  public static final int WIDTH = 960;
  public static final int HEIGHT = 540;
  private final BallCanvas canvas;
  private final JTextField firstPotField = new JTextField(3);
  private final JTextField secondPotField = new JTextField(3);
  private final JTextField thirdPotField = new JTextField(3);
  private final JTextField fourthPotField = new JTextField(3);
  private final JTextField blueCountField = new JTextField("100", 4);

  public BounceFrame() {
    this.setSize(WIDTH, HEIGHT);
    this.setResizable(false);
    this.setTitle("Bounce program");

    this.canvas = new BallCanvas();
    System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
    Container content = this.getContentPane();
    content.add(this.canvas, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.lightGray);

    JButton buttonStart = new JButton("Start");
    JButton buttonStop = new JButton("Stop");
    JButton buttonRed = new JButton("Spawn red ball");
    JButton buttonBlue = new JButton("Spawn blue ball");
    JButton buttonExperiment = new JButton("Start experiment");
    JButton buttonJoin = new JButton("Demonstrate join()");

    buttonStart.addActionListener(
        e -> {
          Ball b = new Ball(canvas, Color.darkGray, false);
          canvas.addBall(b);

          BallThread thread = new BallThread(b, 1);
          thread.start();
          System.out.println("Thread name = " + thread.getName());
        });

    buttonRed.addActionListener(
        e -> {
          Ball b = new Ball(canvas, Color.RED, false);
          canvas.addBall(b);

          BallThread thread = new BallThread(b, 10);
          thread.start();
          System.out.println("Thread name = " + thread.getName());
        });

    buttonBlue.addActionListener(
        e -> {
          Ball b = new Ball(canvas, Color.BLUE, false);
          canvas.addBall(b);

          BallThread thread = new BallThread(b, 1);
          thread.start();
          System.out.println("Thread name = " + thread.getName());
        });

    buttonExperiment.addActionListener(
        e -> {
          LinkedList<BallThread> blueThreads = new LinkedList<>();
          var count = Integer.parseInt(blueCountField.getText());
          for (int i = 0; i < count; i++) {
            Ball b = new Ball(canvas, Color.BLUE, true);
            canvas.addBall(b);
            blueThreads.add(new BallThread(b, 1));
          }

          Ball rb = new Ball(canvas, Color.RED, true);
          canvas.addBall(rb);

          for (BallThread bt : blueThreads) {
            bt.start();
          }
          new BallThread(rb, 10).start();
        });

    buttonJoin.addActionListener(
        e -> {
          Ball b1 = new Ball(canvas, Color.cyan, false);
          Ball b2 = new Ball(canvas, Color.black, false);
          Ball b3 = new Ball(canvas, Color.red, false);

          canvas.addBall(b1);
          canvas.addBall(b2);
          canvas.addBall(b3);

          BallThread bt1 = new BallThread(b1, Thread.NORM_PRIORITY);
          BallThread bt2 = new BallThread(b2, Thread.NORM_PRIORITY, bt1);
          BallThread bt3 = new BallThread(b3, Thread.NORM_PRIORITY, bt2);

          bt3.start();
          bt2.start();
          bt1.start();
        });

    buttonStop.addActionListener(e -> System.exit(0));

    buttonPanel.add(new JLabel("Blue balls count:"));
    buttonPanel.add(blueCountField);
    buttonPanel.add(buttonExperiment);
    buttonPanel.add(buttonStart);
    buttonPanel.add(buttonRed);
    buttonPanel.add(buttonBlue);
    buttonPanel.add(buttonJoin);
    buttonPanel.add(buttonStop);

    JPanel counterPanel = new JPanel();
    counterPanel.setBackground(Color.cyan);

    JLabel firstPotText = new JLabel("Pot 1:");
    counterPanel.add(firstPotText);
    counterPanel.add(firstPotField);
    JLabel secondPotText = new JLabel("Pot 2:");
    counterPanel.add(secondPotText);
    counterPanel.add(secondPotField);
    JLabel thirdPotText = new JLabel("Pot 3:");
    counterPanel.add(thirdPotText);
    counterPanel.add(thirdPotField);
    JLabel fourthPotText = new JLabel("Pot 4:");
    counterPanel.add(fourthPotText);
    counterPanel.add(fourthPotField);

    content.add(buttonPanel, BorderLayout.SOUTH);
    content.add(counterPanel, BorderLayout.NORTH);
  }

  public void initPots() {
    canvas.addPot(new Pot(0, 0, firstPotField));
    canvas.addPot(new Pot(0, canvas.getHeight() - Pot.SIZE, secondPotField));
    canvas.addPot(new Pot(canvas.getWidth() - Pot.SIZE, 0, thirdPotField));
    canvas.addPot(
        new Pot(canvas.getWidth() - Pot.SIZE, canvas.getHeight() - Pot.SIZE, fourthPotField));
  }
}
