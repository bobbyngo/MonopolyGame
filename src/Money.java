import java.util.HashMap;

/**
 * @author Gabriel Benni Kelley Evensen 101119814
 * @version 1.0.0
 */
public class Money {

    private HashMap<Integer, Integer> wallet;

    /**
     * Constructor for special entities (i.e., the bank), which can have a specified amount of money.
     * @param fiveHundreds
     * @param oneHundreds
     * @param fifties
     * @param twenties
     * @param tens
     * @param fives
     * @param ones
     */
    public Money(int fiveHundreds, int oneHundreds, int fifties, int twenties, int tens, int fives, int ones){
        wallet.put(500, fiveHundreds);
        wallet.put(100, oneHundreds);
        wallet.put(50, fifties);
        wallet.put(20, twenties);
        wallet.put(10, tens);
        wallet.put(5, fives);
        wallet.put(1, ones);
    }

    /**
     * Constructor for the average player;
     * 2x $500
     * 2x $100
     * 2x $50
     * 6x $20
     * 5x $10
     * 5x $5
     * 5x $1
     */
    public Money(){
        wallet.put(500, 2);
        wallet.put(100, 2);
        wallet.put(50, 2);
        wallet.put(20, 6);
        wallet.put(10, 5);
        wallet.put(5, 5);
        wallet.put(1, 5);
    }
}
