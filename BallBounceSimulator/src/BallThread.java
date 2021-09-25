public class BallThread extends Thread {
  private final Ball b;
  private final BallThread _threadToJoin;

  public BallThread(Ball ball, int priority) {
    b = ball;
    this.setPriority(priority);
    _threadToJoin = null;
  }

  public BallThread(Ball ball, int priority, BallThread threadToJoin) {
    b = ball;
    this.setPriority(priority);
    _threadToJoin = threadToJoin;
  }

  @Override
  public void run() {
    try {
      if (_threadToJoin != null) {
        _threadToJoin.join();
      }
      for (int i = 1; i < 10000; i++) {
        b.move();
        if (b.checkCollision()) {
          return;
        }
        Thread.sleep(3);
      }
    } catch (InterruptedException ignored) {

    }
  }
}
