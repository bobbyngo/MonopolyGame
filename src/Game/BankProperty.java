package Game;

public class BankProperty extends Square implements PropertyAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */
    private int taxValue;

    /**
     * Constructor for Game.BankProperty Class
     * @param name
     * @param index
     * @param taxValue
     */
    public BankProperty(String name, int index, int taxValue) {
        super(name, index);
        this.taxValue = taxValue;
    }

    /**
     * @author Gabriel Benni Kelley Evensen 101119814
     *
     * Method removes the money from the player who lands on this Game.BankProperty
     */
    @Override
    public void collectMoney(Player player){
        player.removeMoney(taxValue);  //Removes the rent from the player who landed on the square
    }

    /**
     * Getter for taxValue
     * @return int
     */
    public int getTaxValue() {
        return taxValue;
    }

    @Override
    public String toString() {

        //Format name-index-taxValue

        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append("-")
                .append(getIndex()).append("-")
                .append(taxValue);

        return sb.toString();
    }
}
