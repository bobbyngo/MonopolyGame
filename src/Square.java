public class Square {
    private String name;
    private int index;

    /**
     * Constructor for Square class
     * @param name
     * @param index
     */
    public Square(String name, int index){
        this.name = name;
        this.index = index;
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
}
