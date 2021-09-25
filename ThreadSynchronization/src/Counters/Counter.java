package Counters;

public class Counter implements ICounter {
  private int _counter;

  @Override
  public void increment() {
    ++_counter;
  }

  @Override
  public void decrement() {
    --_counter;
  }

  @Override
  public void print() {
    System.out.println(_counter);
  }
}
