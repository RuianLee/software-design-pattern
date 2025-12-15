package showDown;

public class Player {
    protected int point;
    protected String name;
    protected Hand hand;

    public Player() {
        this.point = 0;
        this.hand = new Hand();
    }

    public void nameSelf() {
        System.out.println("I am " + name);
    }

    public void gainPoint() {
        point++;
    }

    public Card takeTurn() {
        // 出手牌的第一張牌
        return hand.removeCard(0);
    }

    public Hand getHand() {
        return hand;
    }

    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }
}
