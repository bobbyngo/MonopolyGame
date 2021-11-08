import java.util.Random;

public class Dice {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private final int NUM_DICE = 2;
    private int[] dice;
    private Random rand;
    private static int NUM_SIDE = 1;

    /**
     * Constructor for Dice class
     */
    public Dice() {
        dice = new int[NUM_DICE];
        rand = new Random();
    }

    /**
     * Roll method will generate 2 random numbers and store it to a fixed array dice size 2
     */
    public void roll(){
        for (int i = 0; i < dice.length; i ++) {

            //Before
            //Random value between 1 -> 6
            //dice[i] = rand.nextInt(6) + 1;

            //side would be 6
            dice[i] = rand.nextInt(NUM_SIDE) + 1;
        }
    }

    /**
     * This method will check whether 2 elements of the dice array are the same or not
     * @return boolean
     */
    public boolean isDouble() {
        return dice[0] == dice[1];
    }

    /**
     * This method sum the 2 elements of the dice array
     * @return
     */
    public int getTotal() {
        return dice[0] + dice[1];
    }

    /**
     * Getter of dice array
     * @return array
     */
    public int[] getDice() {
        return dice;
    }

    /**
     * Getter of size array
     * @return int
     */
    public int getNUM_DICE() {
        return NUM_DICE;
    }

    /**
     * Setter for NUM_SIDE
     * @param numSide
     */
    public static void setNumSide(int numSide) {
        NUM_SIDE = numSide;
    }
}
