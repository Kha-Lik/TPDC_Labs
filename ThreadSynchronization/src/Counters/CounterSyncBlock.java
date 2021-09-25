package Counters;

public class CounterSyncBlock implements ICounter {
  private int _counter;

  public void increment() {
    synchronized (this) {
      ++_counter;
    }
  }

  public void decrement() {
    synchronized (this) {
      --_counter;
    }
  }

  public void print() {
    System.out.println(_counter);
  }
}
