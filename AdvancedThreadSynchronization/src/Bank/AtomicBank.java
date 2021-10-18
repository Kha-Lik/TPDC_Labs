package Bank;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicBank implements IBank {
    public static final int NTEST = 10000;
    private final AtomicIntegerArray accounts;
    private final AtomicLong ntransacts = new AtomicLong(0);

    public AtomicBank(int n, int initialBalance) {
        accounts = new AtomicIntegerArray(new int[n]);
        int i;
        for (i = 0; i < accounts.length(); i++) accounts.set(i, initialBalance);
        ntransacts.set(0);
    }

    @Override
    public void transfer(int fromAccount, int toAccount, int amount) throws InterruptedException {
        accounts.addAndGet(fromAccount, -amount);
        accounts.addAndGet(toAccount, amount);
        ntransacts.incrementAndGet();
        if (ntransacts.get() % NTEST == 0) test();
    }

    public int size() {
        return accounts.length();
    }

    public void test() {
        AtomicInteger sum = new AtomicInteger(0);
        for (int i = 0; i < accounts.length(); i++) sum.addAndGet(accounts.get(i));
        System.out.println("[ATOMIC] Transactions:" + ntransacts.get() + " Sum: " + sum.get());
    }
}
