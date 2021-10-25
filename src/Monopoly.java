import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {

    public static void main(String[] args) {

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
                    }*/
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

    }
}
