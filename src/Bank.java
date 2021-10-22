import java.util.HashMap;

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

    public int getBankBalance() {
        return bankBalance;
    }
}
