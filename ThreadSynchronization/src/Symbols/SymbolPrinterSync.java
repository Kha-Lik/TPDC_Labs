package Symbols;

public class SymbolPrinterSync implements Runnable {
  private static final int symbolNum = 50;
  private final char _symbol;
  private final boolean _printBreak;
  private final ControlFlag _control;

  public SymbolPrinterSync(char symbol, ControlFlag controlFlag, boolean printBreak) {
    _symbol = symbol;
    _printBreak = printBreak;
    _control = controlFlag;
  }

  @Override
  public void run() {
    synchronized (_control) {
      for (int i = 0; i < 100; i++) {
        for (int j = 0; j < symbolNum; j++) {

          while (_control.flag && _symbol == '-'){
            try {
              _control.wait();
            }
            catch (InterruptedException ignored){}
          }

          while (!_control.flag && _symbol == '|'){
            try {
              _control.wait();
            }
            catch (InterruptedException ignored){}
          }

          System.out.print(_symbol);
          if (_printBreak && j == symbolNum - 1) {
            System.out.println();
          }

          _control.flag = !_control.flag;
          _control.notifyAll();
        }
      }
    }
  }
}
