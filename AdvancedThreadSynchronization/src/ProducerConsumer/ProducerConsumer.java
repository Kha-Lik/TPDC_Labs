package ProducerConsumer;

import java.sql.Array;

public class ProducerConsumer {
  public static void main(String[] args) {
    Number[] importantInfo = new Number[10];
    for (int i = 0; i < importantInfo.length; i++) importantInfo[i] = i + 1;

    Drop<Number> drop = new Drop<Number>();
    (new Thread(new Producer<Number>(drop, importantInfo, 0))).start();
    (new Thread(new Consumer<Number>(drop, 0))).start();
  }
}
