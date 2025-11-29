public class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int compareTo(Card other) {
        int rankCompare = Integer.compare(this.rank.getOrder(), other.rank.getOrder());
        if (rankCompare != 0) {
            return rankCompare;
        }
        return Integer.compare(this.suit.getOrder(), other.suit.getOrder());
    }

    @Override
    public String toString() {
        return suit.getSymbol() + "[" + rank.getSymbol() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return suit.hashCode() * 31 + rank.hashCode();
    }

    public static Card fromString(String str) {
        // Format: S[3] or C[10]
        int bracketStart = str.indexOf('[');
        int bracketEnd = str.indexOf(']');
        String suitSymbol = str.substring(0, bracketStart);
        String rankSymbol = str.substring(bracketStart + 1, bracketEnd);
        return new Card(Suit.fromSymbol(suitSymbol), Rank.fromSymbol(rankSymbol));
    }
}
