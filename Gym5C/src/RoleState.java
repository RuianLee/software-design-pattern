/**
 * 角色狀態類別 - 管理當前狀態、剩餘回合數和狀態轉換
 */
public class RoleState {
    private State currentState;
    private int rounds;
    private int orderlessType; // 1: 只能上下, 2: 只能左右

    public RoleState(State initialState) {
        this.currentState = initialState;
        this.rounds = initialState.getInitialRounds();
    }

    /**
     * 倒數回合
     */
    public void countDown() {
        rounds--;
    }

    /**
     * 檢查狀態是否已過期
     * @return 是否過期
     */
    public boolean isExpired() {
        return rounds <= 0;
    }

    /**
     * 更新到新狀態
     * @param newState 新狀態
     */
    public void updateState(State newState) {
        this.currentState = newState;
        this.rounds = newState.getInitialRounds();
    }

    /**
     * 取得當前狀態
     * @return 當前狀態
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * 取得下一個狀態
     * @return 下一個狀態（如果沒有則返回 NORMAL）
     */
    public State getNextState() {
        State next = currentState.getNextState();
        return next != null ? next : State.NORMAL;
    }

    /**
     * 取得剩餘回合數
     * @return 剩餘回合數
     */
    public int getRemainingRounds() {
        return rounds;
    }

    public boolean canMove(String direction) {
        if (currentState == State.ORDERLESS) {
            return checkOrderlessMove(direction);
        }
        return true;  // 其他狀態不限制
    }

    /**
     * 回合開始時決定混亂狀態的移動限制
     * 1: 只能上下, 2: 只能左右
     */
    public void rollOrderlessType() {
        orderlessType = (int)(Math.random() * 2) + 1;
        if (orderlessType == 1) {
            System.out.println("Orderless: This turn you can only move UP/DOWN!");
        } else {
            System.out.println("Orderless: This turn you can only move LEFT/RIGHT!");
        }
        
    }

    /**
     * 檢核是否能在混亂狀態下移動
     * @param direction
     * @return 是否能移動
     */
    private boolean checkOrderlessMove(String direction){
        if(orderlessType == 1 && ("left".equals(direction) || "right".equals(direction))){
            System.out.println("Orderless state: horizontal move not allowed this turn!");
            return false;
        }
        else if(orderlessType == 2 && ("up".equals(direction) || "down".equals(direction))){
            System.out.println("Orderless state: vertical move not allowed this turn!");
            return false;
        }

        return true;
    }

    public boolean isInvincible() {
        return currentState == State.INVINCIBLE;
    }

    public boolean shouldInterruptOnDamage() {
        return currentState == State.STOCKPILE || 
            currentState == State.ACCELERATED;
    }
}
