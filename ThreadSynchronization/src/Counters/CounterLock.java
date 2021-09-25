package Counters;

public class CounterLock implements ICounter {
  private final Object lock = new Object();
  private int _counter;

  public void increment() {
    synchronized (lock) {
      ++_counter;
    }
  }

  public void decrement() {
    synchronized (lock) {
      --_counter;
    }
  }

  public void print() {
    System.out.println(_counter);
  }
}
