package Bank;

public class SyncBank implements IBank{
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    public SyncBank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) accounts[i] = initialBalance;
        ntransacts = 0;
    }

    @Override
    public synchronized void transfer(int fromAccount, int toAccount, int amount) throws InterruptedException {
        accounts[fromAccount] -= amount;
        accounts[toAccount] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) test();
    }

    public int size() {
        return accounts.length;
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) sum += account;
        System.out.println("[SYNC] Transactions:" + ntransacts + " Sum: " + sum);
    }
}
