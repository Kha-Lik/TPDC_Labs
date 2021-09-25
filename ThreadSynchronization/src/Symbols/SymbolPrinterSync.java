package Symbols;

public class SymbolPrinterSync implements Runnable {
  private static final int symbolNum = 50;
  private final char _symbol;
  private final Synchronizer _in;
  private final Synchronizer _out;
  private final boolean _printBreak;

  public SymbolPrinterSync(char symbol, Synchronizer in, Synchronizer out, boolean printBreak) {
    _symbol = symbol;
    _in = in;
    _out = out;
    _printBreak = printBreak;
  }

  @Override
  public void run() {
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < symbolNum; j++) {
        _in.lease();
        System.out.print(_symbol);
        if (_printBreak && j == symbolNum - 1) {
          System.out.println();
        }
        _out.release();
      }
    }
  }
}
