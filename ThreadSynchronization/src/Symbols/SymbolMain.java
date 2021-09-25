package Symbols;

public class SymbolMain {
  public static void main(String[] args) {
    //Thread thread1 = new Thread(new SymbolPrinterAsync('-'));
    //Thread thread2 = new Thread(new SymbolPrinterAsync('|'));

    Synchronizer sync1 = new Synchronizer(1);
    Synchronizer sync2 = new Synchronizer(0);

    Thread thread1 = new Thread(new SymbolPrinterSync('-', sync1, sync2, false));
    Thread thread2 = new Thread(new SymbolPrinterSync('|', sync2, sync1, true));

    thread1.start();
    thread2.start();
  }
}
