public class BankProperty extends Square implements PropertyAPI{
    private Bank bank;
    private int taxValue;

    public BankProperty(String name, int index, int taxValue) {
        super(name, index);
        this.bank = new Bank();
        this.taxValue = taxValue;
    }
    @Override
    public void collectMoney(int moneyValue,int amount){
        bank.addMoney(moneyValue, amount);
    }

    public int getTaxValue() {
        return taxValue;
    }

}
