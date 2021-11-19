public class Board {


    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private final int  LENGTH = 38;
    private Square aBoard[];
    private Square goToJail;
    private Square jail;
    private Square go;
    private Square freeParking;

    public Square getGoToJail() {
        return goToJail;
    }

    public Square getFreeParking() {
        return freeParking;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public Square getGo() {
        return go;
    }

    public Square getJail() {
        return jail;
    }

    /**
     * Constructor for Board Class
     */
    public Board() {
        aBoard = new Square[LENGTH];

        // Special squares
        goToJail = new Square("Go To Jail", 28);
        freeParking = new Square("Free Parking Lot", 19);
        jail = new Square("Prison", 9);
        go = new Square("GO", 0);


        //Initialize a board
        aBoard[0] = go;
        aBoard[1] = new Business("Anarchy Acres", 1,50);
        aBoard[2] = new Business("Dusty Depot", 2, 100);
        aBoard[3] = new Rail("South Rail", 3, 150);
        aBoard[4] = new Business("Fatal Fields", 4, 80);
        aBoard[5] = new BankProperty("Electric Tax", 5, 200);
        aBoard[6] = new Business("Flush Factory", 6, 60);
        aBoard[7] = new Business("Frosty Flights", 7, 110);
        aBoard[8] = new Business("Greasy Grove", 8, 200);
        aBoard[9] = jail;

        aBoard[10] = new Business("Happy Hamlet", 10, 80);
        aBoard[11] = new Rail("West Rail", 11,  150);
        aBoard[12] = new Business("Haunted Hills", 12, 120);
        aBoard[13] = new Business("Junk Junction", 13, 220);
        aBoard[14] = new BankProperty("Internet Tax", 14, 20);
        aBoard[15] = new Business("Lazy Lagoon", 15, 80);
        aBoard[16] = new Business("Leaky Lake", 16, 60);
        aBoard[17] = new Business("Lonely Lodge", 17, 50);
        aBoard[18] = new BankProperty("Insurance Tax", 18, 140);
        aBoard[19] = freeParking;

        aBoard[20] = new Business("Loot Lake", 20, 200);
        aBoard[21] = new Business("Lucky Landing", 21, 300);
        aBoard[22] = new Business("Mega Mall", 22, 360);
        aBoard[23] = new BankProperty("Luxury Tax", 23, 280);
        aBoard[24] = new Business("Tilted Towers", 24, 250);
        aBoard[25] = new Rail("North Rail", 25,  150);
        aBoard[26] = new Business("Paradise Palms", 26, 380);
        aBoard[27] = new Business("Salty Springs", 27, 400);
        aBoard[28] = goToJail;

        aBoard[29] = new BankProperty("Health Tax", 29, 250);
        aBoard[30] = new Business("Moisty Mire", 30, 110);
        aBoard[31] = new Business("Polar Peak", 31, 80);
        aBoard[32] = new Business("Shifty Shafts", 32, 150);
        aBoard[33] = new BankProperty("Water Tax", 33, 90);
        aBoard[34] = new Business("Tomato Town", 34, 140);
        aBoard[35] = new Business("Lazy Links", 35, 240);
        aBoard[36] = new Rail("East Rail", 36, 150);
        aBoard[37] = new Business("Snobby Shores", 37, 120);

    }

    /**
     * Get index in the aBoard array
     * @param index
     * @return Square
     */
    public Square getSQUARE(int index){
        return aBoard[index];
    }
}
