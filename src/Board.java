public class Board {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private static final int  LENGTH = 38;
    private Square aBoard[];

    public Board() {
        aBoard = new Square[LENGTH];

        //Initialize a board
        aBoard[0] = new Square("GO", 0);
        aBoard[1] = new Business("Anarchy Acres", 1,"red", 50);
        aBoard[2] = new Business("Dusty Depot", 2, "red", 100);
        aBoard[3] = new Rail("South Rail", 3, 150);
        aBoard[4] = new Business("Fatal Fields", 4, "red", 80);
        aBoard[5] = new BankProperty("Electric Tax", 5, 200);
        aBoard[6] = new Business("Flush Factory", 6, "blue", 60);
        aBoard[7] = new Business("Frosty Flights", 7, "blue", 110);
        aBoard[8] = new Business("Greasy Grove", 8, "blue", 200);
        aBoard[9] = new Square("Prison", 9);

        aBoard[10] = new Business("Happy Hamlet", 10, "orange", 80);
        aBoard[11] = new Rail("West Rail", 11,  150);
        aBoard[12] = new Business("Haunted Hills", 12, "orange", 120);
        aBoard[13] = new Business("Junk Junction", 13, "orange", 220);
        aBoard[14] = new BankProperty("Internet Tax", 14, 20);
        aBoard[15] = new Business("Lazy Lagoon", 15, "green", 80);
        aBoard[16] = new Business("Leaky Lake", 16, "green", 60);
        aBoard[17] = new Business("Lonely Lodge", 17, "green", 50);
        aBoard[18] = new BankProperty("Insurance Tax", 18, 140);
        aBoard[19] = new Square("Free Parking Lot", 19);

        aBoard[20] = new Business("Loot Lake", 20, "black", 200);
        aBoard[21] = new Business("Lucky Landing", 21, "black", 300);
        aBoard[22] = new Business("Mega Mall", 22, "black", 360);
        aBoard[23] = new BankProperty("Luxury Tax", 23, 280);
        aBoard[24] = new Business("Tilted Towers", 24, "yellow", 250);
        aBoard[25] = new Rail("North Rail", 25,  150);
        aBoard[26] = new Business("Paradise Palms", 26, "yellow", 380);
        aBoard[27] = new Business("Salty Springs", 27, "yellow", 400);
        aBoard[28] = new Square("Go To Jail", 28);

        aBoard[29] = new BankProperty("Health Tax", 29, 250);
        aBoard[30] = new Business("Moisty Mire", 30, "purple", 110);
        aBoard[31] = new Business("Polar Peak", 31, "purple", 80);
        aBoard[32] = new Business("Shifty Shafts", 32, "purple", 150);
        aBoard[33] = new BankProperty("Water Tax", 33, 90);
        aBoard[34] = new Business("Tomato Town", 34, "pink", 140);
        aBoard[35] = new Business("Lazy Links", 35, "pink", 240);
        aBoard[36] = new Rail("East Rail", 36, 150);
        aBoard[37] = new Business("Snobby Shores", 37, "pink", 120);

    }
}
