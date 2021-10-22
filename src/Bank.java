import java.util.HashMap;

public class Bank {
    private HashMap<Integer, Integer> bankWallet;

    public Bank () {
        bankWallet = new HashMap<>();
        //Initialize the bank's money
        bankWallet.put(1, 1000000000);
        bankWallet.put(5, 1000000000);
        bankWallet.put(10, 1000000000);
        bankWallet.put(50, 1000000000);
        bankWallet.put(100, 1000000000);
        bankWallet.put(500, 1000000000);
    }

    public void addMoney(int moneyValue, int amount) {
        if (bankWallet.containsKey(moneyValue)) {
            int oldAmount = bankWallet.get(moneyValue);
            //Update the wallet
            bankWallet.put(moneyValue, oldAmount + amount);
        }
    }

    public void removeMoney(int value, int amount) {
        if (bankWallet.containsKey(value)) {
            int oldAmount = bankWallet.get(value);
            //Update the wallet
            bankWallet.put(value, oldAmount - amount);
        }
    }

    public HashMap<Integer, Integer> getBankWallet() {
        return bankWallet;
    }
}
