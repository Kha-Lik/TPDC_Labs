package Counters;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock implements ICounter {
  private final Lock lock = new ReentrantLock();
  private int _counter;

  public void increment() {
    lock.lock();
      ++_counter;
    lock.unlock();
  }

  public void decrement() {
    lock.lock();
      --_counter;
    lock.unlock();
  }

  public void print() {
    System.out.println(_counter);
  }
}
