import java.util.ArrayList;

public class ColorGroup {
    /**
     * Author Ngo Huu Gia Bao
     * 101163137
     */

    private int countRed;
    private int countBlue;
    private int countOrange;
    private int countGreen;
    private int countBlack;
    private int countYellow;
    private int countPurple;
    private int countPink;

    /**
     * Constructor for Color Group
     */
    public ColorGroup() {
        countRed = 0;
        countBlue = 0;
        countOrange = 0;
        countGreen = 0;
        countBlack = 0;
        countYellow = 0;
        countPurple = 0;
        countPink = 0;
    }

    /**
     * This method will take in the PrivateProperty ArrayList of the Player
     * It will check whether the Player owned a set of color (3 business that have
     * the same color)
     * @param propertyList
     * @return boolean
     */
    public boolean isOwningASet(ArrayList<PrivateProperty> propertyList){
        for (PrivateProperty property : propertyList) {
            if (property instanceof Business) {
                //Down casting to Business class
                // Color is a String
                switch (((Business) property).getColor()) {
                    case "red" -> countRed += 1;
                    case "blue" -> countBlue += 1;
                    case "orange" -> countOrange += 1;
                    case "green" -> countGreen += 1;
                    case "black" -> countBlack += 1;
                    case "yellow" -> countYellow += 1;
                    case "purple" -> countPurple += 1;
                    case "pink" -> countPink += 1;
                }
            }
            //Must be Rail (does not have color attribute)
            else {
                continue;
            }
        }

        if (countRed == 3 || countBlue == 3 || countOrange == 3 || countGreen == 3
        || countBlack == 3 || countYellow == 3 || countPurple == 3 || countPink == 3){
            return true;
        }

        return false;
    }
}
