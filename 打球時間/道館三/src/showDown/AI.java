package showDown;

public class AI extends Player {
    private static int aiCounter = 0;

    public AI() {
        super();
    }

    @Override
    public void nameSelf() {
        aiCounter++;
        this.name = "AI_" + aiCounter;
        System.out.println("I am " + name + " (AI)");
    }

    @Override
    public Card takeTurn() {
        // AI 隨機選擇一張牌
        int randomIndex = (int) (Math.random() * hand.size());
        return hand.removeCard(randomIndex);
    }
}
