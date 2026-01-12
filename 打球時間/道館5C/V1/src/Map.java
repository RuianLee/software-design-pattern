import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private MapObject[][] map;
    private int width = 10;
    private int height = 10;
    private Character character;
    private List<Monster> monsters;

    public Map() {
        map = new MapObject[height][width];
        this.character = new Character(width, height);
        this.monsters = new ArrayList<>();

        // 隨機生成 5 個寶物
        for (int i = 0; i < 5; i++) {
            Treasure treasure = new Treasure(width, height);
            int x = treasure.getCoords().getXCoord();
            int y = treasure.getCoords().getYCoord();
            if (map[y][x] == null && !(x == character.getCoords().getXCoord() && y == character.getCoords().getYCoord())) {
                map[y][x] = treasure;
            } else {
                i--;
            }
        }

        // 隨機生成 3 個怪物
        for (int i = 0; i < 3; i++) {
            Monster monster = new Monster(width, height);
            int x = monster.getCoords().getXCoord();
            int y = monster.getCoords().getYCoord();
            if (map[y][x] == null && !(x == character.getCoords().getXCoord() && y == character.getCoords().getYCoord())) {
                map[y][x] = monster;
                monsters.add(monster);
            } else {
                i--;
            }
        }

        // 隨機生成障礙物
        for (int i = 0; i < 5; i++) {
            Obstacle obstacle = new Obstacle(width, height);
            int x = obstacle.getCoords().getXCoord();
            int y = obstacle.getCoords().getYCoord();
            if (map[y][x] == null && !(x == character.getCoords().getXCoord() && y == character.getCoords().getYCoord())) {
                map[y][x] = obstacle;
            } else {
                i--;
            }
        }
    }

    public void show() {
        System.out.println("\n=== 地圖 ===");
        int charX = character.getCoords().getXCoord();
        int charY = character.getCoords().getYCoord();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == charX && y == charY) {
                    System.out.print(character.getSymbol() + " ");
                } else if (map[y][x] != null) {
                    System.out.print(map[y][x].showMapObject() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void deleteMapObject(int xCoordinate, int yCoordinate) {
        if (xCoordinate >= 0 && xCoordinate < width && yCoordinate >= 0 && yCoordinate < height) {
            map[yCoordinate][xCoordinate] = null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MapObject getMapObject(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return map[y][x];
        }
        return null;
    }

    public boolean handleMoveCommand(Role role, String command) {
        int currentX = role.coords.getXCoord();
        int currentY = role.coords.getYCoord();
        int newX = currentX;
        int newY = currentY;

        if (command.equals("上")) {
            newY = currentY - 1;
        } else if (command.equals("下")) {
            newY = currentY + 1;
        } else if (command.equals("左")) {
            newX = currentX - 1;
        } else if (command.equals("右")) {
            newX = currentX + 1;
        }

        // 檢查邊界
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            System.out.println("超出邊界！");
            if (role instanceof Character) {
                ((Character) role).setDirection(getDirectionFromCommand(command));
            }
            return false;
        }

        // 檢查目標位置的物件
        MapObject targetObject = map[newY][newX];

        if (targetObject == null) {
            // 空地，可以移動
            if (role instanceof Character) {
                ((Character) role).handleCommand(newX, newY, command);
            } else {
                role.move(newX, newY);
            }
            return true;
        } else if (targetObject instanceof Obstacle) {
            // 觸碰到障礙物，留在原地
            System.out.println("觸碰到障礙物，無法移動！");
            if (role instanceof Character) {
                ((Character) role).setDirection(getDirectionFromCommand(command));
            }
            return false;
        } else if (targetObject instanceof Role) {
            // 觸碰到其他角色，留在原地
            System.out.println("觸碰到其他角色，無法移動！");
            if (role instanceof Character) {
                ((Character) role).setDirection(getDirectionFromCommand(command));
            }
            return false;
        } else if (targetObject instanceof Treasure) {
            // 觸碰到寶物，吃掉寶物並移動
            System.out.println("觸碰到寶物！");
            Treasure treasure = (Treasure) targetObject;
            role.touchTreasure(treasure);
            deleteMapObject(newX, newY);
            if (role instanceof Character) {
                ((Character) role).handleCommand(newX, newY, command);
            } else {
                role.move(newX, newY);
            }
            return true;
        }

        return false;
    }

    private Direction getDirectionFromCommand(String command) {
        if (command.equals("上")) return Direction.UP;
        if (command.equals("下")) return Direction.DOWN;
        if (command.equals("左")) return Direction.LEFT;
        if (command.equals("右")) return Direction.RIGHT;
        return Direction.UP;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void spawnRandomObjects() {
        Random random = new Random();

        // 30% 機率生成一個寶物
        if (random.nextDouble() < 0.3) {
            Treasure treasure = new Treasure(width, height);
            int x = treasure.getCoords().getXCoord();
            int y = treasure.getCoords().getYCoord();
            if (map[y][x] == null) {
                map[y][x] = treasure;
                System.out.println("新的寶物出現在 (" + x + ", " + y + ")");
            }
        }

        // 20% 機率生成一個怪物
        if (random.nextDouble() < 0.2) {
            Monster monster = new Monster(width, height);
            int x = monster.getCoords().getXCoord();
            int y = monster.getCoords().getYCoord();
            if (map[y][x] == null && !(x == character.getCoords().getXCoord() && y == character.getCoords().getYCoord())) {
                map[y][x] = monster;
                monsters.add(monster);
                System.out.println("新的怪物出現在 (" + x + ", " + y + ")");
            }
        }
    }

    public void teleportRole(Role role) {
        System.out.println("瞬身狀態：" + (role instanceof Character ? "主角" : "怪物") + " 被隨機傳送！");

        // 找到所有空地
        List<int[]> emptySpaces = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == null) {
                    emptySpaces.add(new int[]{x, y});
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            Random random = new Random();
            int[] newPos = emptySpaces.get(random.nextInt(emptySpaces.size()));
            role.move(newPos[0], newPos[1]);
            System.out.println("傳送到 (" + newPos[0] + ", " + newPos[1] + ")");
        } else {
            System.out.println("沒有空地可以傳送！");
        }
    }

    public void handleAttack(Character character) {
        System.out.println("主角發動攻擊！");

        // 爆發狀態：全場攻擊
        if (character.getState().equals("Erupting")) {
            System.out.println("爆發狀態：全場攻擊！");
            List<Monster> monstersToRemove = new ArrayList<>(monsters);
            for (Monster monster : monstersToRemove) {
                System.out.println("擊殺怪物於 (" + monster.getCoords().getXCoord() + ", " + monster.getCoords().getYCoord() + ")");
                monster.getDamage(50);
                if (monster.getHP() <= 0) {
                    int x = monster.getCoords().getXCoord();
                    int y = monster.getCoords().getYCoord();
                    map[y][x] = null;
                    monsters.remove(monster);
                }
            }
            return;
        }

        Direction direction = character.getDirection();
        int charX = character.getCoords().getXCoord();
        int charY = character.getCoords().getYCoord();

        // 根據方向找到所有可攻擊的怪物
        List<Monster> targetsToRemove = new ArrayList<>();

        if (direction == Direction.UP) {
            for (int y = charY - 1; y >= 0; y--) {
                if (map[y][charX] instanceof Obstacle) break;
                if (map[y][charX] instanceof Monster) {
                    Monster monster = (Monster) map[y][charX];
                    System.out.println("擊殺怪物於 (" + charX + ", " + y + ")");
                    targetsToRemove.add(monster);
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int y = charY + 1; y < height; y++) {
                if (map[y][charX] instanceof Obstacle) break;
                if (map[y][charX] instanceof Monster) {
                    Monster monster = (Monster) map[y][charX];
                    System.out.println("擊殺怪物於 (" + charX + ", " + y + ")");
                    targetsToRemove.add(monster);
                }
            }
        } else if (direction == Direction.LEFT) {
            for (int x = charX - 1; x >= 0; x--) {
                if (map[charY][x] instanceof Obstacle) break;
                if (map[charY][x] instanceof Monster) {
                    Monster monster = (Monster) map[charY][x];
                    System.out.println("擊殺怪物於 (" + x + ", " + charY + ")");
                    targetsToRemove.add(monster);
                }
            }
        } else if (direction == Direction.RIGHT) {
            for (int x = charX + 1; x < width; x++) {
                if (map[charY][x] instanceof Obstacle) break;
                if (map[charY][x] instanceof Monster) {
                    Monster monster = (Monster) map[charY][x];
                    System.out.println("擊殺怪物於 (" + x + ", " + charY + ")");
                    targetsToRemove.add(monster);
                }
            }
        }

        // 移除被擊殺的怪物
        for (Monster monster : targetsToRemove) {
            int x = monster.getCoords().getXCoord();
            int y = monster.getCoords().getYCoord();
            map[y][x] = null;
            monsters.remove(monster);
        }

        if (targetsToRemove.isEmpty()) {
            System.out.println("沒有擊中任何怪物！");
        }
    }
}
