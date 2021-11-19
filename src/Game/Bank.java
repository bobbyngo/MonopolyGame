package Game;

import java.util.HashMap;
import java.util.Map;

public class Bank implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private int bankBalance;
    private HashMap<Integer, Integer>bankWallet;

    /**
     * Game.Bank Class constructor
     */
    public Bank () {
        //Initialize the bank's money
        bankBalance = 999999999;
        bankWallet = new HashMap<>();
    }

    /**
     * The addMoney method is used to add more money to the bank's ballance
     * @param amount
     */
    @Override
    public void addMoney(int amount) {
        bankBalance += amount;
    }

    /**
     * The removeMoney method is used to remove money to the bank's ballance
     * @param amount
     */
    @Override
    public void removeMoney(int amount) {
        if (bankBalance > amount) {
            bankBalance -= amount;
        }else {
            //This will never happen
            System.out.println("Game.Bank balance is not enough");
        }
    }

    /**
     * Getter for bankBalance attribute
     * @return bankBalance, int
     */
    public int getBankBalance() {
        return bankBalance;
    }

    /**
     * Method to return the number of bills in a persons wallet
     * @author Gabriel Benni Kelley Evensen 101119814
     * @return String
     */
    @Override
    public String walletToString(){
        String s = "The bank has:- \n";
        for (Map.Entry<Integer, Integer> entry : this.bankWallet.entrySet()){
            s.concat(entry.getValue() + "x $" + entry.getKey() + "\n");
        }
        return s;
    }

    /**
     * Getter for bankWallet
     * @return HashMap
     */
    public HashMap<Integer, Integer> getBankWallet() {
        return bankWallet;
    }
}
