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
}
