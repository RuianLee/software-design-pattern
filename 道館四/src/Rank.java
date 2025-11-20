public enum Rank {
    THREE("3", 0),
    FOUR("4", 1),
    FIVE("5", 2),
    SIX("6", 3),
    SEVEN("7", 4),
    EIGHT("8", 5),
    NINE("9", 6),
    TEN("10", 7),
    JACK("J", 8),
    QUEEN("Q", 9),
    KING("K", 10),
    ACE("A", 11),
    TWO("2", 12);

    private final String symbol;
    private final int order;

    Rank(String symbol, int order) {
        this.symbol = symbol;
        this.order = order;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getOrder() {
        return order;
    }

    public static Rank fromSymbol(String symbol) {
        for (Rank rank : values()) {
            if (rank.symbol.equals(symbol)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown rank symbol: " + symbol);
    }
}
