package Game;

import java.util.ArrayList;
import java.util.Comparator;

public class AIPlayer extends Player {

    /**
     * Constructor for Game.Player class
     *
     * @param name
     * @param currLocation
     */
    public AIPlayer(String name, Square currLocation) {
        super(name, currLocation);
    }

    public AIPlayer(String name, int playerBalance, boolean inJail, int turnsInJail, int turnsPlayed, Square currLocation) {
        super(name, playerBalance, inJail, turnsInJail, turnsPlayed, currLocation);
    }

    public void autoplay() {
        /*
        1. If needs to pay tax/rent
            a. Until has enough, sell properties from cheapest to expensive
            b. Pay rent/tax
        2. Else if on unowned property and can afford
            a. Toss 50/50 coin, buy if true

         */
        Square loc = getCurrLocation();
        // I feel this is an acceptable amount of instance of? who knows...
        if (loc instanceof BankProperty) {
            // pay tax
            BankProperty bp = (BankProperty) loc;
            int tax = bp.getTaxValue();
            sellPropertiesUntilMin(tax);
            bp.collectMoney(this);
        } else if (loc instanceof PrivateProperty) {
            // if owned elif unowned
            PrivateProperty pp = (PrivateProperty) loc;
            if (pp.isOwned()) {
                // pay rent
                int rent = ((RentableAPI)pp).getRentAmount();
                sellPropertiesUntilMin(rent);
                ((PropertyAPI)pp).collectMoney(this);
                removeProperty(pp);     // QUESTION: are hotels/houses handled? make Business override removeOwner()?
            } else {
                // buy property at a certain probability if they have the cash (100% - full greed)
                int price = pp.getPrice();
                if (getPlayerBalance() >= price) {
                    buyPrivateProperty(pp);

                }
            }

        }
    }

    private void sellPropertiesUntilMin(int min) {
        // it sorts by price i guess?
        ArrayList<PrivateProperty> properties = getPropertyList();
        properties.sort(Comparator.comparingInt(PrivateProperty::getPrice));    // this sort  could be faulty...
        while (getPlayerBalance() < min) {
            // we know AI player has enough to keep playing
            // sell the cheapest property? (not sure about sort order...)
            PrivateProperty removed = properties.get(0);
            removeProperty(removed);
            addMoney(((RentableAPI)removed).getSalePrice());
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<AIPlayer>");
        //Using parent toString
        sb.append(this);
        sb.append("</AIPlayer>");
        return sb.toString();
    }


    public static AIPlayer readFile(String aString, Board board) {
        // The format is name-playerBalance-inJail-turnsInJail-turnsPlayed-currLoc-
        // propertyList
        String[] list = aString.split("\\-");
        Square location = board.getSQUARE(Integer.parseInt(list[5]));
        AIPlayer newPlayer = new AIPlayer(list[0], Integer.parseInt(list[1]), Boolean.parseBoolean(list[2]),
                Integer.parseInt(list[3]), Integer.parseInt(list[4]),location);

        String[] propertyIndex = list[6].split("\\#");
        for (String index : propertyIndex) {
            int i = Integer.parseInt(index);
            Square property = board.getSQUARE(i);
            newPlayer.addPropertyList((PrivateProperty) property);
            ((PrivateProperty)property).setOwner(newPlayer);
        }
        //return new Player(list[0], list[1], list[2], list[3], list[4]);
        return newPlayer;
    }
}
