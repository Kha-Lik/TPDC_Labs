package ProducerConsumer;

import java.util.Random;

public class Producer<T> implements Runnable {
  private final Drop<T> drop;
  private final T[] importantInfo;
  private final T stopMessage;

  public Producer(Drop<T> drop, T[] importantInfo, T stopMessage) {
    this.drop = drop;
    this.importantInfo = importantInfo;
    this.stopMessage = stopMessage;
  }

  public void run() {
    Random random = new Random();

    for (var i : importantInfo) {
      drop.put(i);
      try {
        Thread.sleep(random.nextInt(2000));
      } catch (InterruptedException ignored) {
      }
    }
    drop.put(stopMessage);
  }
}

/*
MESSAGE RECEIVED: Mares eat oats
MESSAGE RECEIVED: Does eat oats
MESSAGE RECEIVED: Little lambs eat ivy
MESSAGE RECEIVED: A kid will eat ivy too
 */
