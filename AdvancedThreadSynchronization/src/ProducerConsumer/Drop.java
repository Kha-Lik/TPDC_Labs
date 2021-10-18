package ProducerConsumer;

public class Drop<T> {
  private T message;
  private boolean empty = true;

  public synchronized T take() {
    while (empty) {
      try {
        wait();
      } catch (InterruptedException ignored) {
      }
    }

    empty = true;
    notifyAll();
    return message;
  }

  public synchronized void put(T message) {
    while (!empty) {
      try {
        wait();
      } catch (InterruptedException ignored) {
      }
    }
    empty = false;
    this.message = message;
    notifyAll();
  }
}
