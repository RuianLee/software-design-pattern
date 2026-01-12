import java.util.Random;

public class Treasure extends MapObject {
    private String treasureType;

    public Treasure(int mapWidth, int mapHeight) {
        super('x', generateRandomCoords(mapWidth, mapHeight));
        this.treasureType = generateTreasureType();
    }

    private static Coords generateRandomCoords(int mapWidth, int mapHeight) {
        Random random = new Random();
        int x = random.nextInt(mapWidth);
        int y = random.nextInt(mapHeight);
        return new Coords(x, y);
    }

    private static String generateTreasureType() {
        Random random = new Random();
        double rand = random.nextDouble();
        if (rand < 0.1) {
            return "Super Star";
        } else if (rand < 0.35) {
            return "Poison";
        } else if (rand < 0.55) {
            return "Accelerating Potion";
        } else if (rand < 0.7) {
            return "Healing Potion";
        } else if (rand < 0.8) {
            return "Devil Fruit";
        } else if (rand < 0.9) {
            return "King's Rock";
        } else {
            return "Dokodemo Door";
        }
    }

    public String getTreasureType() {
        return treasureType;
    }
}
