import java.util.Random;

public class Obstacle extends MapObject {
    public Obstacle(int mapWidth, int mapHeight) {
        super('□', generateRandomCoords(mapWidth, mapHeight));
    }

    private static Coords generateRandomCoords(int mapWidth, int mapHeight) {
        Random random = new Random();
        int x = random.nextInt(mapWidth);
        int y = random.nextInt(mapHeight);
        return new Coords(x, y);
    }
}
