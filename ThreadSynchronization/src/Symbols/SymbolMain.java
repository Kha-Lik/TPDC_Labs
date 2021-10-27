package Symbols;

public class SymbolMain {
  public static void main(String[] args) {
    //Thread thread1 = new Thread(new SymbolPrinterAsync('-'));
    //Thread thread2 = new Thread(new SymbolPrinterAsync('|'));

    ControlFlag controlFlag = new ControlFlag();

    Thread thread1 = new Thread(new SymbolPrinterSync('-', controlFlag, false));
    Thread thread2 = new Thread(new SymbolPrinterSync('|', controlFlag, true));

    thread1.start();
    thread2.start();
  }
}
