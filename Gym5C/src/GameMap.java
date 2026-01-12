import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public Hero getHero() {
        return new Hero();
    }

    public List<Monster> getMonsters() {
        return new ArrayList<Monster>();
    }

    public void printMapMsg() {
        System.out.println("Game Map is displayed.");
    }
}
