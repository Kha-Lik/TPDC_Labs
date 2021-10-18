package ProducerConsumer;

import java.util.Random;

public class Consumer<T> implements Runnable {
  private final Drop<T> drop;
  private final T stopMessage;

  public Consumer(Drop<T> drop, T stopMessage) {
    this.drop = drop;
    this.stopMessage = stopMessage;
  }

  public void run() {
    Random random = new Random();
    for (var message = drop.take(); !message.equals(stopMessage); message = drop.take()) {
      System.out.format("MESSAGE RECEIVED: %s%n", message);
      try {
        Thread.sleep(random.nextInt(2000));
      } catch (InterruptedException ignored) {
      }
    }
  }
}
