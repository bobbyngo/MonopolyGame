import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Patrick Liu 101142730
 * @author Zakaria Ismail 101143497
 * @author Ngo Huu Gia Bao 101163137 handles case 10, winner
 * @author Gabriel Benni Kelley Evensen 101119814
 * Most functional code written by Patrick
 */

public class MonopolyView {
    private MonopolyController controller;

    private enum PromptType {
        PURCHASE, PAY_RENT, NO_CHOICE, PAY_TAX
    }

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
                        System.out.println(String.format(String.format("%s is in jail. They have %d turns of jail-time left!",
                                currentPlayer.getName(), turnsLeft)));

                        if (hasServedTime) {
                            System.out.println(String.format("%s has served their jail time and has returned to the game.", currentPlayer.getName()));
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
                    currentPlayer.makeTurn();
                    // FIXME: init issue
                    System.out.println(String.format("%s has rolled a %d and %d", currentPlayer.getName(), roll[0], roll[1]));
                    if (controller.isSpeeding()) {
                        // is 3rd consecutive double
                        System.out.println(String.format("%s has been caught SPEEDING!", currentPlayer.getName()));
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

                    Square location = currentPlayer.getCurrLocation();
                    if(location instanceof BankProperty){
                        state = 5;
                    }else if(location instanceof PrivateProperty){
                        if(currentPlayer.equals(((PrivateProperty) location).getOwner())) {
                            state = 11;
                        } else {
                            state = 5;
                        }
                    }
                    break;

                case 5:
                    // Check that Player can afford rent/tax
                    location = currentPlayer.getCurrLocation();
                    if(location instanceof BankProperty){
                        if(currentPlayer.getPlayerTotalAsset() >= ((BankProperty) location).getTaxValue()){
                            state = 6;
                        }else{
                            loserExists = true;
                        }
                    }
                    else if(location instanceof Business){
                        if(currentPlayer.getPlayerTotalAsset() >= ((Business) location).getRentAmount()){
                            state = 6;
                        }else{
                            loserExists = true;
                        }
                    }
                    else if(location instanceof Rail){
                        if(currentPlayer.getPlayerTotalAsset() >= ((Rail) location).getRentAmount()){
                            state = 6;
                        }else{
                            loserExists = true;
                        }
                    }

                    break;

                case 6:
                    // Prompt Player to:
                    //  - pay rent/tax,
                    //  - sell properties,
                    //  - display status
                    if (currentPlayer.getCurrLocation() instanceof BankProperty) {
                        promptDecision(currentPlayer, PromptType.PAY_TAX);
                    } else {
                        promptDecision(currentPlayer, PromptType.PAY_RENT);
                    }
                    state = 7;
                    break;

                case 7:
                    // Player pays rent/tax to PlayerY/Bank
                    location = currentPlayer.getCurrLocation();
                    if(location instanceof Business){
                        ((Business) location).collectMoney(currentPlayer);
                    }else if(location instanceof Rail){
                        ((Rail) location).collectMoney(currentPlayer);
                    }else if(location instanceof BankProperty){
                        ((BankProperty) location).collectMoney(currentPlayer);
                        controller.getBank().addMoney(((BankProperty) location).getTaxValue());
                    }
                    state = 12;
                    break;

                case 8:
                    // Check that Player can afford purchase
                    int netWorth = currentPlayer.getPlayerTotalAsset();
                    PrivateProperty sq = (PrivateProperty)currentPlayer.getCurrLocation();

                    if (netWorth >= sq.getPrice()) {
                        // can afford price
                        state = 9;
                    } else {
                        // cannot afford price
                        state = 11;
                    }
                    break;

                case 9:
                    // Prompt Player to:
                    //  - purchase and end turn
                    //  - end turn
                    //  - display status
                    int result = promptDecision(currentPlayer, PromptType.PURCHASE);
                    if (result == 1) {
                        state = 10;
                    } else {
                        state = 12;
                    }
                    break;

                case 10:
                    // Player purchases property
                    currentPlayer = controller.getCurrentPlayer();
                    // Check if the current location of the player is a Private Property
                    if (currentPlayer.getCurrLocation() instanceof PrivateProperty) {
                        // Down casting Square to Private Property to the method in the controller
                        controller.purchaseProperty((PrivateProperty) currentPlayer.getCurrLocation());
                    }
                    state = 12;
                    break;

                case 11: //Player prompts
                    // Prompt Player to:
                    //  - End turn
                    //  - Sell properties
                    //  - Display status
                    promptDecision(currentPlayer, PromptType.NO_CHOICE);
                    state = 12;
                    break;

                case 12: //Check if Player rolled double
                    if (controller.getDie().isDouble()) {
                        state = 1;
                    } else {
                        state = 0;
                    }
                    break;

            }
        }

        // The game is over, there is a bankrupt player
        Player winner = controller.determineWinner();
        System.out.println("The winner is: \n");
        displayStatus(winner);

        // Quit the program
        System.exit(-1);
    }

    /**
     * Prompts the user to purchase property,
     * pay tax, pay rent, sell properties,
     * and display status.
     * Returns 0 if end turn was selected,
     * 1 if action + end turn.
     * @param currentPlayer     Player, current player
     * @param promptType        PromptType, enum for prompt type
     * @return                  boolean, Action was selected
     */
    private int promptDecision(Player currentPlayer, PromptType promptType) {
        Integer i = 1;
        boolean turn_ended = false;
        String type = null;
        Square sq = currentPlayer.getCurrLocation();
        int price = -1;
        HashMap<Integer, String> options = new HashMap<>();
        int exitval = -1;


        if (promptType == PromptType.PURCHASE) {
            options.put(i, String.format("\t%d. Purchase and end turn", i));
            type = "price";
            price = ((PrivateProperty) sq).getPrice();
            i++;
            options.put(i, String.format("\t%d. End turn", i));
            i++;
        } else if (promptType == PromptType.NO_CHOICE) {
            options.put(i, String.format("\t%d. End turn", i));
            i++;
        } else if (promptType == PromptType.PAY_RENT) {
            options.put(i, String.format("\t%d. Pay rent and end turn", i));
            type = "rent";
            price = (sq instanceof Business) ? ((Business)sq).getRentAmount() : ((Rail)sq).getRentAmount();
            i++;
        } else if (promptType == PromptType.PAY_TAX) {
            options.put(i, String.format("\t%d. Pay tax and end turn", i));
            price = ((BankProperty)sq).getTaxValue();
            type = "tax";
            i++;
        }
        //options.put(i, String.format("\t%d. End turn", i));
        //i++;
        options.put(i, String.format("\t%d. Sell properties", i));
        i++;
        options.put(i, String.format("\t%d. Display properties", i));

        Scanner in = new Scanner(System.in);
        int choice;
        while (!turn_ended) {

            String info;
            info = String.format("%s, you have %d$ cash, your total assets are %d$",
                    currentPlayer.getName(), currentPlayer.getPlayerBalance(), currentPlayer.getPlayerTotalAsset());
            if (promptType != PromptType.NO_CHOICE) {
               info = info.concat(String.format(info + ", and the %s of %s is %d$", type, sq.getName(), price));
            }
            System.out.println(info);
            for (String option : options.values()) {
                System.out.println(option);
            }

            System.out.print("Enter choice: ");
            choice = in.nextInt();
            in.nextLine();

            if (choice > 4) {
                System.out.println("Select a valid option!");
                continue;
            }

            if (promptType == PromptType.PURCHASE) {
                switch (choice) {
                    case 1:
                        if (currentPlayer.getPlayerTotalAsset() >= ((PrivateProperty)currentPlayer.getCurrLocation()).getPrice() ) {
                            turn_ended = true;
                            exitval = 1;
                        } else {
                            System.out.println("YOU DONT HAVE ENOUGH CASH!");
                        }
                        break;
                    case 2:
                        turn_ended = true;
                        exitval = 0;
                        break;
                    case 3:
                        promptSale(currentPlayer);
                        break;
                    case 4:
                        displayStatus(currentPlayer);
                        break;
                }
            } else {

                switch (choice) {
                    case 1:
                        if (sq instanceof BankProperty) {
                            if (currentPlayer.getPlayerTotalAsset() >= ((BankProperty)currentPlayer.getCurrLocation()).getTaxValue() ) {
                                turn_ended = true;
                                exitval = 1;
                            } else {
                                System.out.println("YOU DONT HAVE ENOUGH CASH!");
                            }

                        } else {
                            if (sq instanceof Business) {
                                if (currentPlayer.getPlayerTotalAsset() >= ((Business)currentPlayer.getCurrLocation()).getRentAmount() ) {
                                    turn_ended = true;
                                    exitval = 1;
                                } else {
                                    System.out.println("YOU DONT HAVE ENOUGH CASH!");
                                }
                            } else if (sq instanceof Rail) {
                                // Rails
                                if (currentPlayer.getPlayerTotalAsset() >= ((Rail)currentPlayer.getCurrLocation()).getRentAmount() ) {
                                    turn_ended = true;
                                    exitval = 1;
                                } else {
                                    System.out.println("YOU DONT HAVE ENOUGH CASH!");
                                }
                            } else if (sq instanceof Square) {

                            }
                        }

                        break;
                    case 2:
                         promptSale(currentPlayer);
                        break;
                    case 3:
                        displayStatus(currentPlayer);
                        break;
                }
            }

        }

        return exitval;





    }


    /**
     * Method which prompts and confirms sale of selected property
     * @param player; the player who is doing the selling
     */
    private void promptSale(Player player){
        Scanner myObj = new Scanner(System.in);
        System.out.println("You own the following properties:-");
        int i = 0;
        for (PrivateProperty pp : player.getPropertyList()){
            int sellPrice = 0;
            if (pp instanceof Rail)             sellPrice = (pp.getPrice()/2);
            else if (pp instanceof Business)    sellPrice = (((Business) pp).getTotalAssetValue())/2;
            System.out.println("Index: " + i + " - Name: '" + pp.getName() + "' - Sells for $" + sellPrice);
            i++;
        }
        System.out.println("Please provide the index of the property you wish to sell (-1 for no selling): ");
        int propertyIndex = myObj.nextInt();
        myObj.nextLine();
        if (propertyIndex != -1) {
            if (player.getPropertyList().get(propertyIndex) instanceof Rail)
                this.controller.sellProperty((Rail) player.getPropertyList().get(propertyIndex));
            else if (player.getPropertyList().get(propertyIndex) instanceof Business)
                this.controller.sellProperty((Business) player.getPropertyList().get(propertyIndex));
        }
    }

    private void displayStatus(Player player){
        System.out.println("Name:- " + player.getName() + "\n"
                + "Balance:- " + player.getPlayerBalance() + "\n"
                + "Total assets:- " + player.getPlayerTotalAsset() + "\n"
                + "Properties " + player.getName() + "owns:- \n \n"
        );
        for (PrivateProperty pp : player.getPropertyList()){
            int sellPrice = 0;
            if (pp instanceof Rail)             sellPrice = (pp.getPrice()/2);
            else if (pp instanceof Business)    sellPrice = (((Business) pp).getTotalAssetValue())/2;
            System.out.println("Index: " + pp.getIndex() + " - Name: '" + pp.getName() + "' - Sells for $" + sellPrice);
        }

    }




    public static void main(String[] args) {

        MonopolyView v = new MonopolyView();
        v.play();
    }


}
