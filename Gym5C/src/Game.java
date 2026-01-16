import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameMap map;
    private Hero hero;
    private List<Monster> monsters = new ArrayList<>();

    public Game() {
        this.map = new GameMap();
        addHero();
        addMonsters(2);
        addTreasures(1);
        addObstacles(3);
    }

    public void start() {
        while(!isGameOver()){
            map.printMapMsg();
            rolesPlay();
        }

        map.printMapMsg();
        System.out.println("All monsters have been defeated! You win!");
    }

    /**
     * 所有角色執行回合
     */
    private void rolesPlay() {
        hero.command();
        new ArrayList<>(monsters).forEach(Monster::autoAction);
    }

    /**
     * 檢查遊戲是否結束
     */
    private boolean isGameOver() {
        return monsters.isEmpty();
    }

    /**
     * 移除怪物（怪物死亡時呼叫）
     */
    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public Hero getHero() {
        return hero;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public GameMap getMap() {
        return map;
    }

    private void addHero() {
        this.hero = new Hero(this,  Direction.random());
        map.generateMapObject(hero);
    }

    private void addMonsters(int count) {
        for (int i = 0; i < count; i++) {
            Monster monster = new Monster(this);
            monsters.add(monster);
            map.generateMapObject(monster);
        }
    }

    private void addTreasures(int count) {
        for (int i = 0; i < count; i++) {
            map.generateMapObject(new Treasure());
        }
    }

    private void addObstacles(int count) {
        for (int i = 0; i < count; i++) {
           map.generateMapObject(new Obstacle());
        }
    }
}
