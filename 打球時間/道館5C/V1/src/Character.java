import java.util.Random;

public class Character extends Role {
    private Direction direction;
    private String orderlessRestriction; // 混亂狀態的限制：null, "vertical", "horizontal"

    public Character(int mapWidth, int mapHeight) {
        super('↑', generateRandomCoords(mapWidth, mapHeight), 300);
        this.direction = generateRandomDirection();
        this.orderlessRestriction = null;
    }

    private static Coords generateRandomCoords(int mapWidth, int mapHeight) {
        Random random = new Random();
        int x = random.nextInt(mapWidth);
        int y = random.nextInt(mapHeight);
        return new Coords(x, y);
    }

    private static Direction generateRandomDirection() {
        Random random = new Random();
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }

    @Override
    public boolean move(int xCoordinate, int yCoordinate) {
        this.coords = new Coords(xCoordinate, yCoordinate);
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public char getSymbol() {
        if (direction == Direction.UP) {
            return '↑';
        } else if (direction == Direction.DOWN) {
            return '↓';
        } else if (direction == Direction.LEFT) {
            return '←';
        } else {
            return '→';
        }
    }

    public void handleCommand(int newX, int newY, String command) {
        if (command.equals("上")) {
            this.direction = Direction.UP;
        } else if (command.equals("下")) {
            this.direction = Direction.DOWN;
        } else if (command.equals("左")) {
            this.direction = Direction.LEFT;
        } else if (command.equals("右")) {
            this.direction = Direction.RIGHT;
        }

        this.move(newX, newY);
    }

    public void setOrderlessRestriction(String restriction) {
        this.orderlessRestriction = restriction;
    }

    public String getOrderlessRestriction() {
        return orderlessRestriction;
    }

    public boolean isValidOrderlessDirection(String direction) {
        if (orderlessRestriction == null) return true;

        if (orderlessRestriction.equals("vertical")) {
            return direction.equals("上") || direction.equals("下");
        } else if (orderlessRestriction.equals("horizontal")) {
            return direction.equals("左") || direction.equals("右");
        }
        return true;
    }

    @Override
    public boolean handleCommand(String command, Map map) {
        // 混亂狀態只能移動
        if (this.state.equals("Orderless")) {
            // 檢查方向是否符合限制
            if (!isValidOrderlessDirection(command)) {
                System.out.println("該方向不符合混亂狀態的限制！");
                return false; // 重新輸入
            }
            // 執行移動
            return map.handleMoveCommand(this, command);
        }

        // 正常狀態：可以移動或攻擊
        if (command.equals("攻擊")) {
            map.handleAttack(this);
            return true;
        } else if (command.equals("上") || command.equals("下") ||
                   command.equals("左") || command.equals("右")) {
            return map.handleMoveCommand(this, command);
        }

        System.out.println("無效的指令！");
        return false;
    }
}
