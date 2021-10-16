import java.util.Random;

public class Dice {
    private final int SIZE = 2;
    int[] dice;
    Random rand;

    public Dice() {
        dice = new int[SIZE];
        rand = new Random();
    }

    public void roll(){
        for (int i = 0; i < dice.length; i ++) {
            //Random value between 1 -> 6
            dice[i] = rand.nextInt(6) + 1;
        }
    }

    public boolean isDouble() {
        return dice[0] == dice[1];
    }

    public int getTotal() {
        return dice[0] + dice[1];
    }

    public int[] getDice() {
        return dice;
    }
}
