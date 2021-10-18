package Bank;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantBank implements IBank {
    private final ReentrantLock lock = new ReentrantLock();
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    public ReentrantBank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) accounts[i] = initialBalance;
        ntransacts = 0;
    }

    @Override
    public void transfer(int fromAccount, int toAccount, int amount) throws InterruptedException {
        lock.lock();
        accounts[fromAccount] -= amount;
        accounts[toAccount] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) test();
        lock.unlock();
    }

    public int size() {
        return accounts.length;
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) sum += account;
        System.out.println("[REENTRANT] Transactions:" + ntransacts + " Sum: " + sum);
    }
}
