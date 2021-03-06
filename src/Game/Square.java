package Game;

import Game.Player;

import java.util.ArrayList;

public class Square {
    private String name;
    private int index;
    private ArrayList<Player> playersCurrentlyOn;



    /**
     * Constructor for Game.Square class
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
    public void removePlayersCurrentlyOn(Player p) {
        if (this.playersCurrentlyOn != null) {
            this.playersCurrentlyOn.remove(this.playersCurrentlyOn.size() - 1);
        } else {
            System.out.println("No players currently on tile");
        }
    }

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
        //Format Square toString: name-index
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("-")
                .append(index);
        return sb.toString();
    }

    public String toXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<Square>");
        //toString
        sb.append(this);
        sb.append("</Square>\n");
        return sb.toString();
    }

    public static Square readFile(String aString) {
        //Format Square toString: name-index
        String[] list = aString.split("\\-");

        return new Square(list[0], Integer.parseInt(list[1]));
    }

    public void setName(String name) {
        this.name = name;
    }

}
