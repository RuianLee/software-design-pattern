public class MapObject {
    private Crood crood;
    private char symbol;

    public MapObject(char symbol) {
        this.symbol = symbol;
    }

    public Crood getCrood() {
        return crood;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public void setCrood(Crood crood) {
        this.crood = crood;
    }
}