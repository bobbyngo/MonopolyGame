public class Property extends Square{
    private int price;
    private boolean isOwned;
    private String color;

    public Property(String name, int index, String color, int price){
        super(name, index);
        isOwned = false;
        this.color = color;
        this.price = price;
    }

    public void addOwner(){
        isOwned = true;
    }

    public void removeOwner(){
        isOwned = false;
    }

    public int getPrice() {
        return price;
    }

    public boolean getIsOwned(){
        return isOwned;
    }

    public String getColor() {
        return color;
    }

    public static void createDefaultProperty(){
        Business AnarchyAcres = new Business("Anarchy Acres", 1, "red", 50);
        Business DustyDepot = new Business("Dusty Depot", 2, "red", 100);
        Rails SouthRail = new Rails("South Rail", 3, "white", 150);
        Business FatalFields = new Business("Fatal Fields", 4, "red", 80);
        Business FlushFactory = new Business("Flush Factory", 6, "blue", 60);
        Business FrostyFlights = new Business("Frosty Flights", 7, "blue", 110);
        Business GreasyGrove = new Business("Greasy Grove", 8, "blue", 200);
        Business HappyHamlet = new Business("Happy Hamlet", 10, "orange", 80);
        Rails WestRail = new Rails("West Rail", 11, "white", 150);
        Business HauntedHills = new Business("Haunted Hills", 12, "orange", 120);
        Business JunkJunction = new Business("Junk Junction", 13, "orange", 220);
        Business LazyLagoon = new Business("Lazy Lagoon", 15, "green", 80);
        Business LeakyLake = new Business("Leaky Lake", 16, "green", 60);
        Business LonelyLodge = new Business("Lonely Lodge", 17, "green", 50);
        Business LootLake = new Business("Loot Lake", 20, "black", 200);
        Business LuckyLanding = new Business("Lucky Landing", 21, "black", 300);
        Business MegaMall = new Business("Mega Mall", 22, "black", 360);
        Business TiltedTowers = new Business("Tilted Towers", 24, "yellow", 250);
        Rails NorthRail = new Rails("North Rail", 25, "white", 150);
        Business ParadisePalms = new Business("Paradise Palms", 26, "yellow", 380);
        Business SaltySprings = new Business("Salty Springs", 27, "yellow", 400);
        Business MoistyMire = new Business("Moisty Mire", 30, "purple", 110);
        Business PolarPeak = new Business("Polar Peak", 31, "purple", 80);
        Business ShiftyShafts = new Business("Shifty Shafts", 32, "purple", 150);
        Business TomatoTown = new Business("Tomato Town", 34, "pink", 140);
        Business LazyLinks = new Business("Lazy Links", 35, "pink", 240);
        Rails EastRail = new Rails("East Rail", 36, "white", 150);
        Business SnobbyShores = new Business("Snobby Shores", 37, "pink", 120);
    }
}
