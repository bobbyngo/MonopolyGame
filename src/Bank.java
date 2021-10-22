import java.util.HashMap;
import java.util.Map;

public class Bank implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private int bankBalance;

    public Bank () {
        //Initialize the bank's money
        bankBalance = 999999999;
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

<<<<<<< HEAD
    public int getBankBalance() {
        return bankBalance;
=======
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
>>>>>>> 5d95c63fa70fcab25d7d79e9f8231a3f56477f6b
    }
}
