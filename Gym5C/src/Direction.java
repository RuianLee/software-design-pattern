/**
 * 方向類別 - 封裝方向相關邏輯
 */
public enum Direction {
    UP('↑', 0, -1),
    DOWN('↓', 0, 1),
    LEFT('←', -1, 0),
    RIGHT('→', 1, 0);

    private final char symbol;
    private final int dx;
    private final int dy;

    Direction(char symbol, int dx, int dy) {
        this.symbol = symbol;
        this.dx = dx;
        this.dy = dy;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    /**
     * 根據方向計算新座標
     */
    public Crood move(Crood crood) {
        return new Crood(crood.getX() + dx, crood.getY() + dy);
    }

    /**
     * 從字串轉換為 Direction
     */
    public static Direction fromString(String direction) {
        switch (direction.toLowerCase()) {
            case "up": return UP;
            case "down": return DOWN;
            case "left": return LEFT;
            case "right": return RIGHT;
            default: return null;
        }
    }

    /**
     * 隨機取得一個方向
     */
    public static Direction random() {
        Direction[] values = Direction.values();
        return values[(int)(Math.random() * values.length)];
    }

    /**
     * 檢查是否為垂直方向
     */
    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    /**
     * 檢查是否為水平方向
     */
    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
}
