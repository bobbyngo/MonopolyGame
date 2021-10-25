import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Patrick Liu 101142730
 * @author Zakaria Ismail XXXXXXXX
 * @author Ngo Huu Gia Bao 101163137
 * @author Gabriel Benni Kelley Evensen 101119814
 *
 * Most functional code written by Patrick
 */

public class MonopolyView {
    private MonopolyController controller;

    /**
     * MonopolyView constructor.
     *
     * Gets the number of players and their respective names in an ArrayList<Player> and passes that to new instance of MonoPolyController
     */
    public MonopolyView(){
        //this.players = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayer = myObj.nextInt();

        for(int i = 0; i < numPlayer; i++){
            System.out.print("Enter player" + i +"'s name: ");
            String playerName = myObj.nextLine();

            players.add(new Player(playerName, null));      //Note: The players currLocation is set to null
        }

        this.controller = new MonopolyController(players);              //Passing ArrayList<Player> to new MonopolyController
    }


    /**
     * Method to start the game
     *
     * Feel free to change anything @Zak
     */
    public void play(){
        boolean loserExists = false;
        int state = 0;
        Player currentPlayer;

        while (!loserExists) {
            switch (state) {
                case 0:
                    // Select next player for their turn
                    currentPlayer = controller.getNextPlayer();

                    // Check if Player is in jail and has been in jail for < 3 turns
                    //if (currentPlayer.isInJail())
                case 1:
                    // Player rolls dice

                case 2:
                    // Player goes to jail

                case 3:
                    // Move player forward k steps

                case 4:
                    // If PrivateProperty, check that Player owns the property

                case 5:
                    // Check that Player can afford rent/tax

                case 6:
                    // Prompt Player to:
                    //  - pay rent/tax,
                    //  - sell properties,
                    //  - display status

                case 7:
                    // Player pays rent/tax to PlayerY/Bank

                case 8:
                    // Check that Player can afford purchase

                case 9:
                    // Prompt Player to:
                    //  - purchase and end turn
                    //  - end turn
                    //  - display status

                case 10:
                    // Player purchases property

                case 11:
                    // Prompt Player to:
                    //  - End turn
                    //  - Sell properties
                    //  - Display status

                case 12:
                    // Check that Player rolled double

            }
        }
    }

    /**
     * Method which prompts and confirms sale of selected property
     * @param player; the player who is doing the selling
     */
    private void promptSale(Player player){
        Scanner myObj = new Scanner(System.in);
        System.out.println("You own the following properties:-");
        for (PrivateProperty pp : player.getPropertyList()){
            int sellPrice = 0;
            if (pp instanceof Rail)             sellPrice = (pp.getPrice()/2);
            else if (pp instanceof Business)    sellPrice = ((Business) pp).getTotalAssetValue();
            System.out.println("Index: " + pp.getIndex() + " - Name: '" + pp.getName() + "' - Sells for $" + sellPrice);
        }
        System.out.println("Please provide the index of the property you wish to sell: ");
        int propertyIndex = myObj.nextInt();
        if (player.getPropertyList().get(propertyIndex) instanceof Rail) this.controller.sellProperty((Rail) player.getPropertyList().get(propertyIndex));
        else if (player.getPropertyList().get(propertyIndex) instanceof Business) this.controller.sellProperty((Business) player.getPropertyList().get(propertyIndex));
    }

    private void displayStatus(Player player){
        System.out.println("Name:- " + player.getName() + "\n"
                + "Balance:- " + player.getPlayerBalance() + "\n"
                + "Total assets:- " + player.getPlayerTotalAsset() + "\n"
                + "Properties you own:- \n \n"
        );
        for (PrivateProperty pp : player.getPropertyList()){
            int sellPrice = 0;
            if (pp instanceof Rail)             sellPrice = (pp.getPrice()/2);
            else if (pp instanceof Business)    sellPrice = ((Business) pp).getTotalAssetValue();
            System.out.println("Index: " + pp.getIndex() + " - Name: '" + pp.getName() + "' - Sells for $" + sellPrice);
        }

    }


    public static void main(String[] args) {

        MonopolyView v = new MonopolyView();
        v.play();
    }


}
