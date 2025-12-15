package gameFramework;

import java.util.ArrayList;
import java.util.Collections;

public class Deck<T extends Card> {
    private ArrayList<T> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public T draw() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public int size() {
        return cards.size();
    }
}
