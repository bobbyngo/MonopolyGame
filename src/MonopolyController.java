public class MonopolyController {
    Board board;
    Player player;

    public MonopolyController() {
        board = new Board();
    }


    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail
    // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail

}
