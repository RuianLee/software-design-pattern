import java.util.*;

// 單純繼承 CardPattern，不覆寫任何方法
public class SingleCard extends CardPattern {
    public SingleCard(List<Card> cards) {
        super(cards, "單張");
    }
}
