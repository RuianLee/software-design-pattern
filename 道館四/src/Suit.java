public enum Suit {
    CLUB("C", "梅花", 0),
    DIAMOND("D", "菱形", 1),
    HEART("H", "愛心", 2),
    SPADE("S", "黑桃", 3);

    private final String symbol;
    private final String chineseName;
    private final int order;

    Suit(String symbol, String chineseName, int order) {
        this.symbol = symbol;
        this.chineseName = chineseName;
        this.order = order;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getChineseName() {
        return chineseName;
    }

    public int getOrder() {
        return order;
    }

    public static Suit fromSymbol(String symbol) {
        for (Suit suit : values()) {
            if (suit.symbol.equals(symbol)) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Unknown suit symbol: " + symbol);
    }
}
