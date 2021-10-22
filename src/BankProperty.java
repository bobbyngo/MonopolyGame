public class BankProperty extends Square{
    private Bank bank;
    private int taxValue;

    public BankProperty(String name, int index, int taxValue) {
        super(name, index);
        this.bank = new Bank();
        this.taxValue = taxValue;
    }

    public void collectTax(int moneyValue,int amount){
        bank.addMoney(moneyValue, amount);
    }

    public int getTaxValue() {
        return taxValue;
    }
}
