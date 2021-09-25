package Symbols;

public class SymbolPrinterAsync implements Runnable{
    private final char _symbol;

    public SymbolPrinterAsync(char symbol) {
        _symbol = symbol;
    }

    @Override
    public void run() {
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 20; j++) {
        System.out.print(_symbol);
      }
      System.out.println();
    }
    }
}
