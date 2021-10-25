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
    private ArrayList<Player> players;


    /**
     * MonopolyView constructor.
     *
     * Gets the number of players and their respective names in an ArrayList<Player> and passes that to new instance of MonoPolyController
     */
    public MonopolyView(){
        this.players = new ArrayList<>();

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        int numPlayer = myObj.nextInt();

        for(int i = 0; i < numPlayer; i++){
            System.out.println("Enter player" + i +"'s name: ");
            String playerName = myObj.nextLine();

            controller.addPlayer(new Player(playerName, null));      //Note: The players currLocation is set to null
        }

        this.controller = new MonopolyController(players);              //Passing ArrayList<Player> to new MonopolyController
    }


    /**
     * Method to start the game
     *
     * Feel free to change anything @Zak
     */
    public void play(){
        boolean winnerDetermined = false;

        while (!winnerDetermined){
            for(Player p: players){
                Scanner  myObj = new Scanner(System.in);
                boolean endTurn = false;
                boolean rollAgain;

                if (p.getInJail()) endTurn = p.handleInJail();          //If the player is in jail, set handle wheter they should be allowed to play or not in handleInJail method


                while(!endTurn && !p.isBankrupt()){
                    //throw dice
                    this.controller.getDie().roll();
                    System.out.println("You have rolled: " + this.controller.getDie().getDice()[0] + ", and " + this.controller.getDie().getDice()[1]);

                    //check for double, if yes, roll again
                    rollAgain = this.controller.getDie().isDouble();
                    if (rollAgain) System.out.println("You have rolled doubles; you get another turn");

                    // move to square
                    p.moveTo(this.controller.getDie().getTotal());
                    System.out.println("You are currently on square #" + p.getCurrLocation().getIndex() + " '" + p.getCurrLocation().getName() + "'");

                    // check if they landed in "go to jail"
                    if (p.getCurrLocation().getIndex() == 28){                                                          //If player landed on square #28, they go to jail
                        p.setJail(true);                                                                                //Set players jail status to true
                        endTurn = p.handleInJail();                                                                     //End their turn (handled in handleInJail method)
                    }

                    // land on a square, check if they have enough money to buy
                    if (p.getCurrLocation() instanceof PrivateProperty){
                        if ((((PrivateProperty) p.getCurrLocation()).getPrice() <= p.getPlayerBalance())) {
                            System.out.println("You have (" + p.getPlayerBalance() +") enough to purchase (" + ((PrivateProperty) p.getCurrLocation()).getPrice() + ") this property : (y/n)");
                            if (myObj.nextLine().equals("y")) p.buyPrivateProperty((PrivateProperty) p.getCurrLocation());
                        }
                    }

                    // if they have land, check if they are allowed to build houses/hotel

                    // player should have an option to end turn
                }

                // If the current player bankrupts, immediately break out of the for loop
                if(p.isBankrupt()){
                    winnerDetermined = true;
                    break;
                }
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


        /*
        // Patrick
        // just some general logic, flow of the game, needs improvement for sure
        Board board = new Board();
        boolean winnerDetermined = false;
        ArrayList<Player> playerList = new ArrayList<>();
        Dice die = new Dice();

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter number of players: ");

        int numPlayer = myObj.nextInt();

        for(int i = 0; i < numPlayer; i++){
            System.out.println("Enter player" + i +"'s name: ");

            String playerName = myObj.nextLine();

            playerList.add(new Player(playerName, board.getSQUARE(0)));
        }

        // The game ends when one player goes bankrupt, the winner is determined by their total asset value
        while(!winnerDetermined){
            for(Player p: playerList){
                boolean endTurn = false;
                int moveBy = 0;
                boolean rollAgain = false;

                if (p.getInJail()) endTurn = p.handleInJail(); //Gabriel Benni: if the player is in jail, then run handleInJail()


                while(!endTurn && !p.isBankrupt()){
                    //throw dice
                    die.roll();
                    //check for double, if yes, roll again
                    rollAgain = die.isDouble();
                    // move to square
                    p.moveTo(die.getTotal());
                    // land on a square, check if they have enough money to buy
                    // if they have land, check if they are allowed to build houses/hotel
                    // check if they landed in "go to jail"
                    if (p.getCurrLocation().getIndex() == 28){ //Gabriel Benni: check if the player is on the jail square
                        p.setJail(true);
                        endTurn = p.handleInJail();
                    }
                    /*while(rollAgain && !endTurn){
                        p.moveTo();
                    }
                    // player should have an option to end turn
                }

                // If the current player bankrupts, immediately break out of the for loop
                if(p.isBankrupt()){
                    winnerDetermined = true;
                    break;
                }
            }
        }

        // Patrick
        // Determine the winning, who ever has the greatest asset value + wallet amount wins
        int winnerAmount = 0;
        String winner = "";
        for(Player p: playerList){
            if(!p.isBankrupt()){
                if (winnerAmount < p.getPlayerTotalAsset()){
                    winnerAmount = p.getPlayerTotalAsset();
                    winner = p.getName();
                }
            }
        }

        System.out.println("The winner of the game is: " + winner);
    */
    }
}
