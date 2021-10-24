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
     * collectMoney method is used when player steps on the BankProperty
     * it will call the addMoney method to add the amount that user need to pay to the bankBalance
     * @param amount
     */
    @Override
    public void collectMoney(int amount){
        bank.addMoney(amount);
    }

    /**
     * Getter for taxValue
     * @return int
     */
    public int getTaxValue() {
        return taxValue;
    }

}
