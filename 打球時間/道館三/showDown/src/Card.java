public class Card {
    private Suit suit;
    private Rank rank;

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

    public int compare(Card c) {
        if (this.rank.getValue() > c.rank.getValue()) {
            return 1;
        } else if (this.rank.getValue() < c.rank.getValue()) {
            return -1;
        } else {
            if (this.suit.ordinal() > c.suit.ordinal()) {
                return 1;
            } else if (this.suit.ordinal() < c.suit.ordinal()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return suit + " " + rank;
    }
}
