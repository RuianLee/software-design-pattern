public class MapObject {
    protected char symbol;
    protected Coords coords;

    public MapObject(char symbol, Coords coords) {
        this.symbol = symbol;
        this.coords = coords;
    }

    public char showMapObject() {
        return symbol;
    }

    public Coords getCoords() {
        return coords;
    }
}
