public abstract class Role extends MapObject {
    protected int HP;
    protected String state;
    protected StateRoundInfo stateRoundInfo;
    protected int actionCount;

    public Role(char symbol, Coords coords, int HP) {
        super(symbol, coords);
        this.HP = HP;
        this.state = "Normal State";
        this.stateRoundInfo = null;
        this.actionCount = 1;
    }

    /**
     * 處理指令並執行動作
     * @param command 指令（例如："上"、"下"、"左"、"右"、"攻擊"）
     * @param map 遊戲地圖（僅在執行動作時使用，不儲存）
     * @return 是否成功執行（用於判斷是否需要重試）
     */
    public abstract boolean handleCommand(String command, Map map);

    public boolean move(int xCoordinate, int yCoordinate) {
        this.coords = new Coords(xCoordinate, yCoordinate);
        return true;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getState() {
        return state;
    }

    public void updateState(String newState) {
        this.state = newState;

        // 根據狀態設定動作次數，預設為 1
        this.actionCount = 1;
        if (newState.equals("Accelerated")) {
            this.actionCount = 2;
        }

        if (newState.equals("Invincible")) {
            this.stateRoundInfo = new StateRoundInfo(2, "Invincible", "Normal State");
        } else if (newState.equals("Poisoned")) {
            this.stateRoundInfo = new StateRoundInfo(3, "Poisoned", "Normal State");
        } else if (newState.equals("Accelerated")) {
            this.stateRoundInfo = new StateRoundInfo(3, "Accelerated", "Normal State");
        } else if (newState.equals("Healing")) {
            this.stateRoundInfo = new StateRoundInfo(5, "Healing", "Normal State");
        } else if (newState.equals("Orderless")) {
            this.stateRoundInfo = new StateRoundInfo(3, "Orderless", "Normal State");
        } else if (newState.equals("Stockpile")) {
            this.stateRoundInfo = new StateRoundInfo(2, "Stockpile", "Erupting");
        } else if (newState.equals("Erupting")) {
            this.stateRoundInfo = new StateRoundInfo(3, "Erupting", "Teleport");
        } else if (newState.equals("Teleport")) {
            this.stateRoundInfo = new StateRoundInfo(1, "Teleport", "Normal State");
        } else {
            this.stateRoundInfo = null;
        }
    }

    public int getActionCount() {
        return actionCount;
    }

    public void onRoundStart() {
        if (this.state.equals("Poisoned")) {
            this.HP -= 15;
            System.out.println("中毒狀態：失去 15 HP，目前 HP：" + this.HP);

            return;
        }
        
        if (this.state.equals("Healing")) {
            int maxHP = (this instanceof Character) ? 300 : 1;
            if (this.HP < maxHP) {
                this.HP += 30;
                if (this.HP > maxHP) {
                    this.HP = maxHP;
                }
                System.out.println("恢復狀態：恢復 30 HP，目前 HP：" + this.HP);

                // 滿血後立刻恢復正常狀態
                if (this.HP >= maxHP) {
                    System.out.println("已滿血，恢復正常狀態！");
                    updateState("Normal State");
                }
            }
        } else if (this.state.equals("Orderless") && this instanceof Character) {
            // 混亂狀態：隨機設定只能上下或左右移動
            java.util.Random random = new java.util.Random();
            String restriction = random.nextBoolean() ? "vertical" : "horizontal";
            ((Character) this).setOrderlessRestriction(restriction);
            if (restriction.equals("vertical")) {
                System.out.println("混亂狀態：本回合只能進行上下移動！");
            } else {
                System.out.println("混亂狀態：本回合只能進行左右移動！");
            }
        }
    }

    public boolean decreaseStateRound() {
        boolean needsTeleport = false;
        if (stateRoundInfo != null) {
            stateRoundInfo.decreaseRound();
            if (stateRoundInfo.isExpired()) {
                // 瞬身狀態過期時需要傳送
                if (state.equals("Teleport")) {
                    needsTeleport = true;
                }
                String nextState = stateRoundInfo.getNextState();
                updateState(nextState);
            }
        }
        return needsTeleport;
    }

    public void getDamage(int damage) {
        if (!state.equals("Invincible")) {
            this.HP -= damage;

            // 加速或蓄力狀態受攻擊後立刻恢復正常
            if (state.equals("Accelerated") || state.equals("Stockpile")) {
                System.out.println(state + " 狀態因受攻擊而結束，恢復正常狀態！");
                updateState("Normal State");
            }
        }
    }

    public void touchTreasure(Treasure treasure) {
        String treasureType = treasure.getTreasureType();
        System.out.println("獲得寶物：" + treasureType);

        if (treasureType.equals("Super Star")) {
            updateState("Invincible");
        } else if (treasureType.equals("Poison")) {
            updateState("Poisoned");
        } else if (treasureType.equals("Accelerating Potion")) {
            updateState("Accelerated");
        } else if (treasureType.equals("Healing Potion")) {
            updateState("Healing");
        } else if (treasureType.equals("Devil Fruit")) {
            updateState("Orderless");
        } else if (treasureType.equals("King's Rock")) {
            updateState("Stockpile");
        } else if (treasureType.equals("Dokodemo Door")) {
            updateState("Teleport");
        }
    }
}
