import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.atomic.AtomicInteger;

public class Pot {
  public static final int SIZE = 40;
  private final int x;
  private final int y;
  private final AtomicInteger counter = new AtomicInteger(0);
  private final JTextField textField;

  public Pot(int x, int y, JTextField textField) {
    this.x = x;
    this.y = y;
    this.textField = textField;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(Color.green);
    g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void increment() {
    textField.setText(String.valueOf(counter.incrementAndGet()));
  }
}
