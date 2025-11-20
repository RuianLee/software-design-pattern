import java.util.*;

// 單純繼承 CardPattern，不覆寫任何方法
public class Straight extends CardPattern {
    public Straight(List<Card> cards) {
        super(cards, "順子");
    }
}
