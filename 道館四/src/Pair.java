import java.util.*;

// 單純繼承 CardPattern，不覆寫任何方法
public class Pair extends CardPattern {
    public Pair(List<Card> cards) {
        super(cards, "對子");
    }
}
