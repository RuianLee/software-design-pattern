import java.util.Random;

/**
 * 地圖類別 - 只負責地圖座標管理和顯示
 */
public class GameMap {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    private MapObject[][] map = new MapObject[WIDTH][HEIGHT];

    /**
     * 檢查指定座標四周是否有 Hero
     */
    public String checkHeroAround(Crood crood) {
        int x = crood.getX();
        int y = crood.getY();

        if (isWithinBounds(new Crood(x, y - 1)) && isHero(getMapObjectAt(new Crood(x, y - 1)))) return "up";
        if (isWithinBounds(new Crood(x, y + 1)) && isHero(getMapObjectAt(new Crood(x, y + 1)))) return "down";
        if (isWithinBounds(new Crood(x + 1, y)) && isHero(getMapObjectAt(new Crood(x + 1, y)))) return "right";
        if (isWithinBounds(new Crood(x - 1, y)) && isHero(getMapObjectAt(new Crood(x - 1, y)))) return "left";
        return null;
    }

    /**
     * 印出地圖狀態
     */
    public void printMapMsg() {
        System.out.println("Map status:");
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                MapObject obj = getMapObjectAt(new Crood(x, y));
                if (obj != null) {
                    System.out.print(obj.getSymbol() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }


    /**
     * 取得隨機空位置
     */
    public Crood getEmptyPosition() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);

        return isPositionOccupied(new Crood(x, y)) ? getEmptyPosition() : new Crood(x, y);
    }

    /**
     * 取得指定座標的寶物狀態
     */
    public State getTreasureStateAt(Crood crood) {
        MapObject obj = getMapObjectAt(crood);
        if (obj instanceof Treasure) {
            return ((Treasure) obj).getState();
        }
        return null;
    }

    /**
     * 移動角色到新座標
     */
    public void moveToNewCrood(Role role, Crood newCrood) {
        setMapObjectAt(role.getCrood(), null);  // 清除舊位置
        setMapObjectAt(newCrood, role);         // 放到新位置
    }

    /**
     * 更新地圖上的位置（不修改角色座標）
     */
    public void updatePosition(Crood oldCrood, Crood newCrood, MapObject obj) {
        map[oldCrood.getX()][oldCrood.getY()] = null;  // 清除舊位置
        map[newCrood.getX()][newCrood.getY()] = obj;   // 放到新位置
    }

    /**
     * 從地圖移除物件
     */
    public void removeFromMap(MapObject mapObject) {
        setMapObjectAt(mapObject.getCrood(), null);
    }

    /**
     * 設定指定座標的地圖物件
     */
    public void setMapObjectAt(Crood crood, MapObject mapObject) {
        if (mapObject != null) {
            mapObject.setCrood(crood);
            map[crood.getX()][crood.getY()] = mapObject;
        } else {
            map[crood.getX()][crood.getY()] = null;
        }
    }

    public void generateMapObject(MapObject mapObject){
        mapObject.setCrood(getEmptyPosition());
        map[mapObject.getCrood().getX()][mapObject.getCrood().getY()] = mapObject;
    }

    // 查詢方法
    public boolean isWithinBounds(Crood crood) {
        return crood.getX() >= 0 && crood.getX() < WIDTH && crood.getY() >= 0 && crood.getY() < HEIGHT;
    }

    public MapObject getMapObjectAt(Crood crood) {
        return map[crood.getX()][crood.getY()];
    }

    public boolean isValidTouch(Crood crood) {
        return getMapObjectAt(crood) instanceof Treasure || getMapObjectAt(crood) == null;
    }

    private boolean isPositionOccupied(Crood crood) {
        return getMapObjectAt(crood) != null;
    }

    private boolean isHero(MapObject mapObject) {
        return mapObject instanceof Hero;
    }
}
