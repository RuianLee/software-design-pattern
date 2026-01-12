import java.util.Random;

public class Monster extends Role {
    public Monster(int mapWidth, int mapHeight) {
        super('M', generateRandomCoords(mapWidth, mapHeight), 1);
    }

    private static Coords generateRandomCoords(int mapWidth, int mapHeight) {
        Random random = new Random();
        int x = random.nextInt(mapWidth);
        int y = random.nextInt(mapHeight);
        return new Coords(x, y);
    }

    public boolean isInAttackRange(Character character) {
        int monsterX = this.coords.getXCoord();
        int monsterY = this.coords.getYCoord();
        int charX = character.getCoords().getXCoord();
        int charY = character.getCoords().getYCoord();

        // 檢查是否在攻擊範圍內（上下左右一格）
        return (Math.abs(monsterX - charX) == 1 && monsterY == charY) ||
               (Math.abs(monsterY - charY) == 1 && monsterX == charX);
    }

    public void attack(Character character) {
        System.out.println("怪物於 (" + coords.getXCoord() + ", " + coords.getYCoord() + ") 攻擊主角！");
        character.getDamage(50);
        System.out.println("主角受到 50 點傷害，目前 HP：" + character.getHP());

        // 如果主角不是加速或蓄力狀態，受到攻擊後獲得無敵狀態
        if (!character.getState().equals("Accelerated") && !character.getState().equals("Stockpile")) {
            character.updateState("Invincible");
            System.out.println("主角獲得無敵狀態！");
        }
    }

    public String decideAction(Character character) {
        // 隨機決定移動方向
        Random random = new Random();
        String[] directions = {"上", "下", "左", "右"};
        return directions[random.nextInt(4)];
    }

    @Override
    public boolean handleCommand(String command, Map map) {
        // Monster 不接受外部指令，直接返回 false
        return false;
    }

    /**
     * Monster 執行自己的回合
     * @param character 主角
     * @param map 遊戲地圖
     */
    public void executeTurn(Character character, Map map) {
        if (this.getHP() <= 0) return;

        if (isInAttackRange(character)) {
            // 在攻擊範圍內，攻擊主角
            attack(character);
        } else {
            // 不在攻擊範圍內，隨機移動
            String direction = decideAction(character);
            System.out.print("怪物於 (" + this.getCoords().getXCoord() + ", " +
                           this.getCoords().getYCoord() + ") 往" + direction + "移動 -> ");
            boolean moved = map.handleMoveCommand(this, direction);
            if (moved) {
                System.out.println("移動到 (" + this.getCoords().getXCoord() + ", " +
                                 this.getCoords().getYCoord() + ")");
            } else {
                System.out.println("無法移動");
            }
        }
    }
}
