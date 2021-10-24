import java.util.Random;

public class Dice {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private final int SIZE = 2;
    int[] dice;
    Random rand;

    /**
     * Constructor for Dice class
     */
    public Dice() {
        dice = new int[SIZE];
        rand = new Random();
    }

    /**
     * Roll method will generate 2 random numbers and store it to a fixed array dice size 2
     */
    public void roll(){
        for (int i = 0; i < dice.length; i ++) {
            //Random value between 1 -> 6
            dice[i] = rand.nextInt(6) + 1;
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
}
