public class Game {
    private GameMap map;

    public Game(GameMap map) {
        this.map = map;
    }
    
    public void start() {
        while (isGameOver()) {
            map.printMapMsg();
            map.getHero().command();
            map.getMonsters().forEach(Monster::autoAction);
        }

        System.out.println("All monsters have been defeated! You win!");
    }

    private boolean isGameOver() {
        return map.getMonsters().isEmpty();
    }
}
