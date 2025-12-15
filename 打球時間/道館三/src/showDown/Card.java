package showDown;

import showDown.Card;

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

    public boolean isGreaterThan(Card c) {
        if(this.rank.getOrder() - c.rank.getOrder() > 0){
            return true;
        }

        if(this.rank.getOrder() - c.rank.getOrder() < 0){
            return false;
        }

        if(this.suit.getOrder() - c.suit.getOrder() > 0){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return suit + " " + rank;
    }

    public boolean canPlayOn(Card topCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canPlayOn'");
    }
}
