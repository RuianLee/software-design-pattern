import java.util.*;

public class CardPattern {
    protected List<Card> cards;
    protected String patternName;

    public CardPattern(List<Card> cards, String patternName) {
        this.cards = new ArrayList<>(cards);
        Collections.sort(this.cards);
        this.patternName = patternName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getPatternName() {
        return patternName;
    }

    // 違反 OCP：所有牌型的比較邏輯都集中在這裡
    // 新增牌型時必須修改這個方法
    public boolean isGreaterThan(CardPattern other) {
        if (!this.patternName.equals(other.patternName)) {
            return false;
        }
        if (this.cards.size() != other.cards.size()) {
            return false;
        }

        // 根據不同牌型使用不同的比較邏輯
        if (patternName.equals("單張")) {
            return this.cards.get(0).compareTo(other.cards.get(0)) > 0;
        }

        if (patternName.equals("對子")) {
            Card thisMax = this.cards.get(1);
            Card otherMax = other.cards.get(1);
            return thisMax.compareTo(otherMax) > 0;
        }

        if (patternName.equals("順子")) {
            Card thisMax = this.cards.get(4);
            Card otherMax = other.cards.get(4);
            return thisMax.compareTo(otherMax) > 0;
        }

        if (patternName.equals("葫蘆")) {
            Card thisTripleMax = getTripleMaxCard(this.cards);
            Card otherTripleMax = getTripleMaxCard(other.cards);
            return thisTripleMax.compareTo(otherTripleMax) > 0;
        }

        return false;
    }

    private Card getTripleMaxCard(List<Card> cards) {
        Map<Rank, List<Card>> rankGroups = new HashMap<>();
        for (Card card : cards) {
            if (!rankGroups.containsKey(card.getRank())) {
                rankGroups.put(card.getRank(), new ArrayList<>());
            }
            rankGroups.get(card.getRank()).add(card);
        }

        for (Rank rank : rankGroups.keySet()) {
            List<Card> group = rankGroups.get(rank);
            if (group.size() == 3) {
                Card max = group.get(0);
                for (Card card : group) {
                    if (card.compareTo(max) > 0) {
                        max = card;
                    }
                }
                return max;
            }
        }
        return null;
    }

    public String getCardsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(cards.get(i).toString());
        }
        return sb.toString();
    }

    public static CardPattern createPattern(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return null;
        }

        List<Card> sortedCards = new ArrayList<>(cards);
        Collections.sort(sortedCards);

        int size = sortedCards.size();

        if (size == 1) {
            return new SingleCard(sortedCards);
        }

        if (size == 2) {
            if (isPair(sortedCards)) {
                return new Pair(sortedCards);
            }
        }

        if (size == 5) {
            if (isFullHouse(sortedCards)) {
                return new FullHouse(sortedCards);
            }
            if (isStraight(sortedCards)) {
                return new Straight(sortedCards);
            }
        }

        return null;
    }

    protected static boolean isPair(List<Card> cards) {
        return cards.get(0).getRank() == cards.get(1).getRank();
    }

    protected static boolean isFullHouse(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            int count = rankCount.containsKey(card.getRank()) ? rankCount.get(card.getRank()) : 0;
            rankCount.put(card.getRank(), count + 1);
        }

        if (rankCount.size() != 2) {
            return false;
        }

        boolean hasThree = false;
        boolean hasTwo = false;
        for (Rank rank : rankCount.keySet()) {
            int count = rankCount.get(rank);
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

    protected static boolean isStraight(List<Card> cards) {
        List<Integer> orders = new ArrayList<>();
        for (Card card : cards) {
            orders.add(card.getRank().getOrder());
        }
        Collections.sort(orders);

        // Normal consecutive sequence
        boolean isConsecutive = true;
        for (int i = 1; i < orders.size(); i++) {
            if (orders.get(i) - orders.get(i - 1) != 1) {
                isConsecutive = false;
                break;
            }
        }
        if (isConsecutive) {
            return true;
        }

        // Special wrap-around straights
        int[] orderArray = new int[5];
        for (int i = 0; i < 5; i++) {
            orderArray[i] = orders.get(i);
        }

        // Q-K-A-2-3: [0, 9, 10, 11, 12]
        if (orderArray[0] == 0 && orderArray[1] == 9 && orderArray[2] == 10 && orderArray[3] == 11 && orderArray[4] == 12) {
            return true;
        }

        // K-A-2-3-4: [0, 1, 10, 11, 12]
        if (orderArray[0] == 0 && orderArray[1] == 1 && orderArray[2] == 10 && orderArray[3] == 11 && orderArray[4] == 12) {
            return true;
        }

        // A-2-3-4-5: [0, 1, 2, 11, 12]
        if (orderArray[0] == 0 && orderArray[1] == 1 && orderArray[2] == 2 && orderArray[3] == 11 && orderArray[4] == 12) {
            return true;
        }

        // 2-3-4-5-6: [0, 1, 2, 3, 12]
        if (orderArray[0] == 0 && orderArray[1] == 1 && orderArray[2] == 2 && orderArray[3] == 3 && orderArray[4] == 12) {
            return true;
        }

        return false;
    }
}
