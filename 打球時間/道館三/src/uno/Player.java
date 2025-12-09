package uno;
public class Player {
    protected String name;
    protected Hand hand;

    public Player() {
        this.hand = new Hand();
    }

    public void nameSelf() {
        System.out.println("I am " + name);
    }

    public Card takeTurn(Card topCard) {
        // 基本實作：隨機出第一張可以出的牌
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (card.canPlayOn(topCard)) {
                return hand.removeCard(i);
            }
        }

        return null;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }
}
