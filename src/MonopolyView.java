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
        myObj.nextLine();
        for(int i = 0; i < numPlayer; i++){
            System.out.print("Enter player" + i +"'s name: ");
            String playerName = myObj.nextLine();

            players.add(new Player(playerName, null));      //Note: The players currLocation is set to null
        }

        this.controller = new MonopolyController(players);              //Passing ArrayList<Player> to new MonopolyController
    }


    /**
     * Text UI game loop for
     * MonopolyView
     * @return  void
     * @author  Zakaria Ismail, 101143497
     */
    public void play(){
        boolean loserExists = false;
        int state = 0;
        Player currentPlayer = null;

        while (!loserExists) {
            switch (state) {
                case 0:
                    // Select next player for their turn
                    currentPlayer = controller.getNextPlayer();
                    System.out.println(String.format("---It is %s's turn!---", currentPlayer.getName()));

                    // Check if Player is in jail and has been in jail for < 3 turns
                    //if (currentPlayer.isInJail() && currentPlayer.getTurnsInJail() < 3) {
                    if (currentPlayer.isInJail()) {
                        // Skip Player's turn
                        boolean hasServedTime = currentPlayer.serveJailTime();
                        int turnsLeft = 3 - currentPlayer.getTurnsInJail();
                        System.out.println(String.format("%s is in jail. They have %d turns of jail-time left!",
                                currentPlayer.getName(), turnsLeft));

                        if (hasServedTime) {
                            state = 1;
                        }
                    } else {
                        // allow Player to roll dice
                        state = 1;
                    }
                    break;
                case 1:
                    // Player rolls dice
                    int[] roll = controller.rollDie();
                    // FIXME: init issue
                    System.out.println(String.format("%s has rolled a %d and %d", currentPlayer.getName(), roll[0], roll[1]));
                    if (controller.isSpeeding()) {
                        // is 3rd consecutive double
                        System.out.println("%s has been caught SPEEDING!");
                        state = 2;
                    } else {
                        state = 3;
                    }
                    break;

                case 2:
                    // Player goes to jail
                    System.out.println(String.format("%s has been sent to Jail.", currentPlayer.getName()));
                    controller.sendCurrentPlayerToJail();
                    state = 0;
                    break;

                case 3:
                    // Move player forward k steps
                    //  - collect Go money if passed
                    String start, end;
                    int total = controller.getDie().getTotal();
                    boolean passed_go;

                    start = currentPlayer.getCurrLocation().getName();
                    passed_go = controller.moveCurrentPlayer();
                    end = currentPlayer.getCurrLocation().getName();
                    System.out.println(String.format("%s has moved forward %d steps from %s to %s",
                            currentPlayer.getName(), total, start, end));

                    if (passed_go) {
                        // Player passed GO
                        System.out.println(String.format("%s has received %d$ for passing GO",
                                currentPlayer.getName(), controller.getGO_REWARD()));
                    }

                    if (controller.currentPlayerIsOnGoToJail()) {
                        // Player landed on GoToJail
                        state = 2;

                    } else if (controller.currentPlayerIsOnBlank()) {
                        // Player landed on Free Parking or GO or (Visiting) Jail
                        state = 11;
                    } else if (controller.currentPlayerIsOnOwnedProperty()) {
                        // Player landed on owned property
                        state = 4;
                    } else {
                        // FIXME: logic has a chance of being erroneous
                        // Player landed on unowned property
                        state = 8;
                    }
                    break;

                case 4:
                    // If PrivateProperty, check that Player owns the property
                    break;

                case 5:
                    // Check that Player can afford rent/tax
                    break;

                case 6:
                    // Prompt Player to:
                    //  - pay rent/tax,
                    //  - sell properties,
                    //  - display status
                    break;

                case 7:
                    // Player pays rent/tax to PlayerY/Bank
                    break;

                case 8:
                    // Check that Player can afford purchase
                    break;

                case 9:
                    // Prompt Player to:
                    //  - purchase and end turn
                    //  - end turn
                    //  - display status
                    break;

                case 10:
                    // Player purchases property
                    break;

                case 11:
                    // Prompt Player to:
                    //  - End turn
                    //  - Sell properties
                    //  - Display status
                    break;

                case 12:
                    // Check that Player rolled double
                    break;

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
