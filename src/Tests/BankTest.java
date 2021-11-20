package Tests;

import Game.*;
import org.junit.Before;
import static org.junit.Assert.*;

public class BankTest {
    Bank bank;

    @Before
    public void init() {
        bank = new Bank();
    }

    @org.junit.Test
    public void testAddMoney() {
        int previousBalance = bank.getBankBalance();
        bank.addMoney(200);
        int currentBalance = bank.getBankBalance();
        assertEquals(previousBalance + 200, currentBalance);
    }

    @org.junit.Test
    public void testRemoveMoney(){
        int previousBalance = bank.getBankBalance();
        bank.removeMoney(200);
        int currentBalance = bank.getBankBalance();
        assertEquals(previousBalance - 200, currentBalance);
    }

}
