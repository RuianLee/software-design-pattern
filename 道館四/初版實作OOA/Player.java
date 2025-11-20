import java.util.*;

public class Player {
    private String name;
    private List<Card> handCards;

    public Player(String name) {
        this.name = name;
        this.handCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        handCards.add(card);
        Collections.sort(handCards);
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public CardPattern playCards(int[] indices) {
        List<Card> selectedCards = new ArrayList<>();
        for (int index : indices) {
            if (index < 0 || index >= handCards.size()) {
                return null;
            }
            selectedCards.add(handCards.get(index));
        }

        CardPattern pattern = CardPattern.createPattern(selectedCards);
        if (pattern == null) {
            return null;
        }

        // Remove cards from hand (remove from highest index first)
        Integer[] sortedIndices = new Integer[indices.length];
        for (int i = 0; i < indices.length; i++) {
            sortedIndices[i] = indices[i];
        }
        Arrays.sort(sortedIndices, Collections.reverseOrder());
        for (int index : sortedIndices) {
            handCards.remove(index);
        }

        return pattern;
    }

    public boolean hasFinished() {
        return handCards.isEmpty();
    }

    public void printHandCards() {
        StringBuilder indexLine = new StringBuilder();
        StringBuilder cardLine = new StringBuilder();

        for (int i = 0; i < handCards.size(); i++) {
            String cardStr = handCards.get(i).toString();
            String indexStr = String.valueOf(i);

            if (i > 0) {
                indexLine.append(" ");
                cardLine.append(" ");
            }

            // Pad index to match card string length
            indexLine.append(indexStr);
            for (int j = indexStr.length(); j < cardStr.length(); j++) {
                indexLine.append(" ");
            }
            cardLine.append(cardStr);
        }

        System.out.println(indexLine.toString());
        System.out.println(cardLine.toString());
    }
}
