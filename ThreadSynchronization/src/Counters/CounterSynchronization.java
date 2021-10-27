package Counters;

public class CounterSynchronization {
  public static void main(String[] args) {
    ICounter counter = new Counter();
    ICounter counterLock = new CounterLock();
    ICounter counterSynchronizedMethod = new CounterSynchronizedMethod();
    ICounter counterSyncBlock = new CounterSyncBlock();

    IncrementThread incrementThread = new IncrementThread(counter);
    DecrementThread decrementThread = new DecrementThread(counter);
    IncrementThread incrementThread1 = new IncrementThread(counterLock);
    DecrementThread decrementThread1 = new DecrementThread(counterLock);
    IncrementThread incrementThread2 = new IncrementThread(counterSynchronizedMethod);
    DecrementThread decrementThread2 = new DecrementThread(counterSynchronizedMethod);
    IncrementThread incrementThread3 = new IncrementThread(counterSyncBlock);
    DecrementThread decrementThread3 = new DecrementThread(counterSyncBlock);

    incrementThread.start();
    decrementThread.start();
    incrementThread1.start();
    decrementThread1.start();
    incrementThread2.start();
    decrementThread2.start();
    incrementThread3.start();
    decrementThread3.start();

    try {
      incrementThread.join();
      decrementThread.join();
      incrementThread1.join();
      decrementThread1.join();
      incrementThread2.join();
      decrementThread2.join();
      incrementThread3.join();
      decrementThread3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    counter.print();
    counterLock.print();
    counterSyncBlock.print();
    counterSynchronizedMethod.print();
  }
}
