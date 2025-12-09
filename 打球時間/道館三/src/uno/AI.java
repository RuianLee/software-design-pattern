package uno;
import java.util.ArrayList;

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
    public Card takeTurn(Card topCard) {
        // AI 隨機選擇可以出的牌
        ArrayList<Integer> validCardIndices = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.getCard(i).canPlayOn(topCard)) {
                validCardIndices.add(i);
            }
        }

        if (!validCardIndices.isEmpty()) {
            // 隨機選擇一張可以出的牌
            int randomIndex = (int) (Math.random() * validCardIndices.size());
            int cardIndex = validCardIndices.get(randomIndex);
            Card playedCard = hand.removeCard(cardIndex);
            System.out.println(name + " played: " + playedCard);
            return playedCard;
        }

        return null;
    }
}
