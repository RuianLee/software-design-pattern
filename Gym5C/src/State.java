
public enum State {
    NORMAL(1, null),
    INVINCIBLE(2, NORMAL),
    POISONED(3, NORMAL),
    ACCELERATED(3, NORMAL),
    HEALING(5, NORMAL),
    ORDERLESS(3, NORMAL),
    TELEPORT(1, NORMAL),
    ERUPTING(3, TELEPORT),
    STOCKPILE(2, ERUPTING);

    private final int initialRounds;
    private final State nextState;

    State(int initialRounds, State nextState) {
        this.initialRounds = initialRounds;
        this.nextState = nextState;
    }

    /**
     * 取得此狀態的初始回合數
     * @return 初始回合數
     */
    public int getInitialRounds() {
        return initialRounds;
    }

    /**
     * 取得此狀態結束後的下一個狀態
     * @return 下一個狀態
     */
    public State getNextState() {
        return nextState;
    }
}
