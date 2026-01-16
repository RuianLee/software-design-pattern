public class Role extends MapObject{
    private int hp;
    private int maxHp;
    private int times = 1;
    private RoleState roleState;
    private Game game;

    private static final int POISON_DAMAGE = 15;
    private static final int HEAL_AMOUNT = 30;

    public Role(Game game, char symbol, int hp) {
        super(symbol);
        this.hp = hp;
        this.maxHp = hp;
        this.game = game;
        this.roleState = new RoleState(State.NORMAL);
    }

    /**
     * 執行當前狀態的每回合效果
     */
    public void executeStateEffect(){
        State currentState = roleState.getCurrentState();

        // 混亂狀態：回合開始時決定這回合的移動限制
        if (currentState == State.ORDERLESS) {
            roleState.rollOrderlessType();
        }
        else if(currentState.equals(State.POISONED)){
            setHp(getHp() - POISON_DAMAGE);
            System.out.println(getClass().getSimpleName() + " loses 15 HP due to poison. Current HP: " + getHp());
        }
        else if(currentState.equals(State.HEALING)){
            int healAmount = Math.min(HEAL_AMOUNT, maxHp - getHp());
            setHp(getHp() + healAmount);

            System.out.println(getClass().getSimpleName() + " heals " + healAmount + " HP. Current HP: " + getHp());

            if(getHp() >= maxHp){
                System.out.println(getClass().getSimpleName() + " is fully healed! Returning to normal state.");
                updateState(State.NORMAL);
            }
        }

        roleState.countDown();
        if(roleState.isExpired()){
            updateState(roleState.getNextState());
        }
    }

    public void getDamage(int damage){
        if(roleState.isInvincible()){
            System.out.println(getClass().getSimpleName() + " is invincible!");
            return;
        }

        setHp(getHp() - damage);

        if(getHp() <= 0){
            die();
            return;
        }

        if(roleState.shouldInterruptOnDamage()){
            System.out.println(getClass().getSimpleName() + "'s state was interrupted!");
            updateState(State.NORMAL);
            return;
        }

        System.out.println("Hero gains invincibility after being attacked!");
        updateState(State.INVINCIBLE);
    }

    /**
     * 角色死亡 - 子類別可覆寫
     */
    protected void die() {
        getGameMap().removeFromMap(this);
    }

    public boolean isValidMove(String direction){
        Crood newCrood = getCrood().move(direction);
        return roleState.canMove(direction)
            && getGameMap().isWithinBounds(newCrood)
            && getGameMap().isValidTouch(newCrood);
    }

    /**
     * 移動到指定方向
     * @param direction 移動方向
     */
    public void move(String direction){
        Crood oldCrood = getCrood();
        Crood newCrood = oldCrood.move(direction);
        touch(newCrood);
        setCrood(newCrood);  // 先更新自己的座標
        getGameMap().updatePosition(oldCrood, newCrood, this);  // 再更新地圖
    }

    public String performMove(java.util.Scanner cin) {
        String[] directions = {"up", "down", "left", "right"};

        while(true) {
            String direction;

            if(cin != null) {
                System.out.println("Enter direction (up, down, left, right):");
                direction = cin.nextLine();
            } else {
                int randomIndex = (int) (Math.random() * 4);
                direction = directions[randomIndex];
                System.out.println(getClass().getSimpleName() + " attempts to move " + direction + "...");
            }

            if(!isValidDirection(direction)) {
                System.out.println("不該出現這樣的輸入");
                continue;
            }

            if(!isValidMove(direction)) {
                if(cin != null) {
                    System.out.println("違規移動");
                } else {
                    System.out.println(getClass().getSimpleName() + " cannot move " + direction + ", trying another direction.");
                }
                continue;
            }

            move(direction);
            if(cin == null) {
                System.out.println(getClass().getSimpleName() + " successfully moved " + direction + "!");
            }
            return direction;
        }
    }

    /**
     * 碰觸指定座標，處理碰撞效果（如寶物）
     * @param crood 目標座標
     */
    public void touch(Crood crood){
        State treasureState = getGameMap().getTreasureStateAt(crood);
        if(treasureState != null){
            updateState(treasureState);
        }
    }

    public boolean isValidDirection(String direction) {
        if(Direction.fromString(direction) == null){
            System.out.println("Invalid direction! Please enter up, down, left, or right.");
            return false;
        }

        return true;
    }

    public boolean canAttack(){
        return roleState.getCurrentState() != State.ORDERLESS;
    }

    /**
     * 更新到新狀態，並執行進入新狀態時的效果
     * @param newState 新狀態
     */
    public void updateState(State newState){
        // 執行進入新狀態時的效果
        switch (newState) {
            case NORMAL:
                setTimes(1);
                break;

            case ACCELERATED:
                setTimes(2);
                break;

            case TELEPORT:
                teleport();
                break;

            default:
                // 其他狀態進入時沒有特殊效果
                break;
        }

        // 更新狀態
        System.out.println(getClass().getSimpleName() + " found a treasure and gained " + newState + " state!");
        roleState.updateState(newState);
    }

    /**
     * 隨機傳送到地圖上的空地
     */
    private void teleport() {
        Crood oldPosition = getCrood();
        Crood newPosition = getGameMap().getEmptyPosition();
        setCrood(newPosition);
        getGameMap().updatePosition(oldPosition, newPosition, this);
    }

    public Game getGame() {
        return this.game;
    }

    public GameMap getGameMap() {
        return this.game.getMap();
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public State getState(){
        return roleState.getCurrentState();
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
