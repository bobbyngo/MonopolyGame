import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {

    public static void main(String[] args) {

        // Patrick
        // just some general logic, flow of the game, needs improvement for sure
        Board board = new Board();
        boolean winnerDetermined = false;
        ArrayList<Player> playerList = new ArrayList<>();

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter number of players: ");

        int numPlayer = myObj.nextInt();

        for(int i = 0; i < numPlayer; i++){
            System.out.println("Enter player" + i +"'s name: ");

            String playerName = myObj.nextLine();

            playerList.add(new Player(playerName, board.getSQUARE(0)));
        }

        while(!winnerDetermined){

            int bankruptCount = 0;
            for(Player p: playerList){
                boolean endTurn = false;

                if (p.getInJail()) endTurn = p.handleInJail(); //Gabriel Benni: if the player is in jail, then run handleInJail()


                while(!endTurn && !p.isBankrupt()){
                    //throw dice
                    //check for double, if yes, roll again
                    // move to square
                    // land on a square, check if they have enough money to buy
                    // if they have land, check if they are allowed to build houses/hotel
                    // check if they landed in "go to jail"
                    if (p.getCurrLocation().getIndex() == 28){ //Gabriel Benni: check if the player is on the jail square
                        p.setJail(true);
                        endTurn = p.handleInJail();
                    }
                    // player should have an option to end turn
                }

                if(p.isBankrupt()){
                    bankruptCount ++;
                }
            }

            if(bankruptCount == numPlayer - 1){
                winnerDetermined = true;
            }
        }

    }
}
