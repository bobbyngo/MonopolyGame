import java.util.HashMap;
import java.util.Map;

public class Bank implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private int bankBalance;
    private HashMap<Integer, Integer>bankWallet;

    public Bank () {
        //Initialize the bank's money
        bankBalance = 999999999;
        bankWallet = new HashMap<>();
    }

    @Override
    public void addMoney(int amount) {
        bankBalance += amount;
    }

    @Override
    public void removeMoney(int amount) {
        if (bankBalance > amount) {
            bankBalance -= amount;
        }else {
            //This will never happen
            System.out.println("Bank balance is not enough");
        }
    }


    public int getBankBalance() {
        return bankBalance;
    }

    @Override
    public String walletToString(){
        String s = "The bank has:- \n";
        for (Map.Entry<Integer, Integer> entry : this.bankWallet.entrySet()){
            s.concat(entry.getValue() + "x $" + entry.getKey() + "\n");
        }
        return s;
    }

    public HashMap<Integer, Integer> getBankWallet() {
        return bankWallet;
    }
}
