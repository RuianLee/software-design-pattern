import java.util.Scanner;

public class Hero extends Role{
    private Scanner cin = new Scanner(System.in);
    private Direction direction;

    public Hero(Game game, Direction direction) {
        super(game, direction.getSymbol(), 300);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        setSymbol(direction.getSymbol());
    }

    public void command() {
        executeStateEffect();
        printMsg();
        operation();
    }

    private void updateDirection(String directionStr){
        Direction dir = Direction.fromString(directionStr);
        if(dir != null){
            setDirection(dir);
        }
    }

    /**
     * 英雄攻擊：朝著當前面向方向攻擊整條線上的所有怪物，直到遇到障礙物
     */
    private void herroAttack() {
        if(getState() == State.ERUPTING){
            // 爆發狀態：全地圖攻擊
            attackAllMonsters();
        } else {
            // 一般攻擊：攻擊當前面向方向的整條線
            attackLine();
        }
    }

    /**
     * 攻擊整條線上的所有怪物
     */
    private void attackLine(){
        System.out.println("Hero attacks in direction: " + direction);

        Crood nextCrood = getCrood().move(direction);
        GameMap map = getGameMap();

        while (map.isWithinBounds(nextCrood)) {
            MapObject target = map.getMapObjectAt(nextCrood);

            if (target instanceof Obstacle) {
                System.out.println("Attack blocked by obstacle at (" + nextCrood.getX() + ", " + nextCrood.getY() + ")");
                break;
            }

            if (target instanceof Monster) {
                System.out.println("Hero hits Monster at (" + nextCrood.getX() + ", " + nextCrood.getY() + ")!");
                ((Monster) target).getDamage(50);
            }

            nextCrood = nextCrood.move(direction);
        }
    }

    /**
     * 攻擊全地圖的所有怪物（爆發狀態）
     */
    private void attackAllMonsters(){
        System.out.println("Hero is in ERUPTING state! Attacking all monsters on the map!");
        getGame().getMonsters().forEach(monster -> monster.getDamage(50));
    }

    /**
     * 選擇操作
     */
    private void operation() {
        for(int i=0; i < getTimes(); i++){
            if (!canAttack()) {
                System.out.println("Cannot attack in orderless state, moving instead.");
                String direction = performMove(cin);
                updateDirection(direction);

                continue;
            }

            System.out.println("Enter command (1: move, 2: attack):");
            String input = cin.nextLine();

            if("1".equals(input)){
                String direction = performMove(cin);
                updateDirection(direction);
            }
            else if("2".equals(input)){
                herroAttack();
            }

            clearScreen();
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * 印出英雄狀態
     */
    private void printMsg() {
        System.out.println("Hero's hp is" + getHp() + ", " + "Hero's state is " + getState());
    }
}
