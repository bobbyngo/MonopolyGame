public class BankProperty extends Square implements PropertyAPI{
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private Bank bank;
    private int taxValue;

    public BankProperty(String name, int index, int taxValue) {
        super(name, index);
        this.bank = new Bank();
        this.taxValue = taxValue;
    }
    @Override
    public void collectMoney(int amount){
        bank.addMoney(amount);
    }

    public int getTaxValue() {
        return taxValue;
    }

}
