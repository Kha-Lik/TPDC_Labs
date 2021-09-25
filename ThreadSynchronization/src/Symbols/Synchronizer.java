package Symbols;

import java.util.concurrent.atomic.AtomicInteger;

public class Synchronizer {
  private AtomicInteger _threadCounter;

  public Synchronizer(int threads) {
    _threadCounter = new AtomicInteger(threads);
  }

  public void lease() {
    do {
      if (_threadCounter.get() <= 0) continue;
      else _threadCounter.decrementAndGet();
      break;
    } while (true);
  }

  public void release() {
    _threadCounter.incrementAndGet();
  }
}
