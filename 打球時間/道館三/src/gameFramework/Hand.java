package gameFramework;

import java.util.ArrayList;

public class Hand<T extends Card> {
    private ArrayList<T> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(T card) {
        cards.add(card);
    }

    public T removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.remove(index);
        }
        return null;
    }

    public T getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }

    public int size() {
        return cards.size();
    }

    public ArrayList<T> getCards() {
        return cards;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
