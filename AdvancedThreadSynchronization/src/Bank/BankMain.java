package Bank;

import java.io.IOException;

public class BankMain {
    public static final int NACCOUNTS = 5;
    public static final int INITIAL_BALANCE = 5000;

    public static void main(String[] args) throws IOException {
        IBank asyncBank = new AsyncBank(NACCOUNTS, INITIAL_BALANCE);
        IBank reentrantBank = new ReentrantBank(NACCOUNTS, INITIAL_BALANCE);
        IBank syncBank = new SyncBank(NACCOUNTS, INITIAL_BALANCE);
        IBank atomicBank = new AtomicBank(NACCOUNTS, INITIAL_BALANCE);

        runTest(asyncBank);
        runTest(reentrantBank);
        runTest(syncBank);
        runTest(atomicBank);

        //noinspection ResultOfMethodCallIgnored
        System.in.read();
    }

    private static void runTest(IBank bank){
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThread t = new TransferThread(bank, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }
}
