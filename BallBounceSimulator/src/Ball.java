import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {
  private static final int XSIZE = 20;
  private static final int YSIZE = 20;
  private final BallCanvas _canvas;
  private final Color _color;
  private int x;
  private int y;
  private int dx = 1;
  private int dy = 1;

  public Ball(BallCanvas c, Color color, boolean placeInCenter) {
    _canvas = c;
    _color = color;

    if (!placeInCenter) {
      if (Math.random() < 0.5) {
        x = new Random().nextInt(_canvas.getWidth());
        y = 0;
      } else {
        x = 0;
        y = new Random().nextInt(_canvas.getHeight());
      }
    } else {
      x = _canvas.getWidth() / 2;
      y = _canvas.getHeight() / 2;
    }
  }

  public void draw(Graphics2D g2) {
    g2.setColor(_color);
    g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
  }

  public void move() {
    x += dx;
    y += dy;
    if (x < 0) {
      x = 0;
      dx = -dx;
    }
    if (x + XSIZE >= _canvas.getWidth()) {
      x = _canvas.getWidth() - XSIZE;
      dx = -dx;
    }
    if (y < 0) {
      y = 0;
      dy = -dy;
    }
    if (y + YSIZE >= _canvas.getHeight()) {
      y = _canvas.getHeight() - YSIZE;
      dy = -dy;
    }
    _canvas.repaint();
  }

  public boolean checkCollision() {
    for (Pot p : _canvas.pots) {
      if (x >= p.getX()
          && x + XSIZE <= p.getX() + Pot.SIZE
          && y >= p.getY()
          && y + YSIZE <= p.getY() + Pot.SIZE) {
        p.increment();
        _canvas.removeBall(this);
        return true;
      }
    }
    return false;
  }
}
