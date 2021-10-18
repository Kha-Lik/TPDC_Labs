package Bank;

public class AsyncBank implements IBank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    public AsyncBank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) accounts[i] = initialBalance;
        ntransacts = 0;
    }

    @Override
    public void transfer(int fromAccount, int toAccount, int amount) {
        accounts[fromAccount] -= amount;
        accounts[toAccount] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) test();
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) sum += account;
        System.out.println("[ASYNC] Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}
