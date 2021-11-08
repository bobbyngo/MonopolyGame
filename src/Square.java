import java.util.ArrayList;

public class Square {
    private String name;
    private int index;
    private ArrayList<Player> playersCurrentlyOn;

    /**
     * Constructor for Square class
     * @param name
     * @param index
     */
    public Square(String name, int index){
        this.name = name;
        this.index = index;
        this.playersCurrentlyOn = new ArrayList<>();
    }

    /**
     * Accessor method for playersCurrentlyOn this square
     * @author Gabriel Benni Kelley Evensen 101119814
     */
    public ArrayList<Player> getPlayersCurrentlyOn(){
        return this.playersCurrentlyOn;
    }

    /**
     * Mutator method to add ONE player to playersCurrentlyOn this
     * The playersCurrentlyOn ArrayList is FIFO, to keep order in which players arrived
     *
     * @author Gabriel Benni Kelley Evensen 101119814
     */
    public void addPlayersCurrentlyOn(Player p){ this.playersCurrentlyOn.add(0, p); }

    /**
     * Mutator method to remove ONE player to playersCurrentlyOn this
     * The playersCurrentlyOn ArrayList is FIFO, to keep order in which players arrived
     *
     * @author Gabriel Benni Kelley Evensen 101119814
     */
    public void removePlayersCurrentlyOn(Player p){ this.playersCurrentlyOn.remove(this.playersCurrentlyOn.size() - 1); }

    /**
     * Getter for name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for index
     * @return int
     */
    public int getIndex(){
        return index;
    }

    /**
     * Setter for index
     * @params index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", playersCurrentlyOn=" + playersCurrentlyOn +
                '}';
    }
}
