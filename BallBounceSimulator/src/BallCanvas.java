import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BallCanvas extends JPanel {
  public final ArrayList<Pot> pots = new ArrayList<>();
  private final ConcurrentLinkedQueue<Ball> balls = new ConcurrentLinkedQueue<>();

  public void addBall(Ball b) {
    this.balls.add(b);
  }

  public void removeBall(Ball b) {
    this.balls.remove(b);
  }

  public void addPot(Pot p) {
    this.pots.add(p);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    for (Ball b : balls) {
      b.draw(g2);
    }

    for (Pot p : pots) {
      p.draw(g2);
    }
  }
}
