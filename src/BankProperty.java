public class BankProperty extends Square implements PropertyAPI{
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private Bank bank;
    private int taxValue;

    /**
     * Constructor for BankProperty Class
     * @param name
     * @param index
     * @param taxValue
     */
    public BankProperty(String name, int index, int taxValue) {
        super(name, index);
        this.bank = new Bank();
        this.taxValue = taxValue;
    }

    /**
     * @author Gabriel Benni Kelley Evensen 101119814
     *
     * Method to collect money from a player who lands on a square that is owned by the bank, and charge the player rent, giving the rent to the bank
     * rent is 10% of the total bank property price
     */
    @Override
    public void collectMoney(){
        this.getPlayersCurrentlyOn().get(0).removeMoney(taxValue);  //Removes the rent from the player who landed on the square
        this.bank.addMoney(taxValue);                               //Adds the rent money to the bank
    }

    /**
     * Getter for taxValue
     * @return int
     */
    public int getTaxValue() {
        return taxValue;
    }

}
