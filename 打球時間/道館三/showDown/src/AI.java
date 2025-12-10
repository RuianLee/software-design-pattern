public class AI extends Player {

    public AI() {
        super();
    }

    @Override
    public Card takeTurn() {
        Card card = hand.removeCard(0);
        return card;
    }
}
