package showDown;
public enum Suit {
    CLUB(1),
    DIAMOND(2),
    HEART(3),
    SPADE(4);

    private int value;

    Suit(int value) {
        this.value = value;
    }

    public int getOrder() {
        return value;
    }
}
