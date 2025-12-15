package gameFramework;

public abstract class Player<T extends Card> {
    protected String name;
    protected Hand<T> hand;

    public Player() {
        this.hand = new Hand<T>();
    }

    public void nameSelf() {
        System.out.println("I am " + name);
    }

    public abstract T takeTurn(T topCard);

    public Hand<T> getHand() {
        return hand;
    }

    public void addHandCard(T card){
        hand.addCard(card);
    }

    public String getName() {
        return name;
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }
}
