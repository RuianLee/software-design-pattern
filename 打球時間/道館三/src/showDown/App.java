package showDown;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Player> players = new ArrayList<>();

        // 建立 1 位人類玩家和 3 位 AI
        players.add(new HumanPlayer());
        players.add(new AI());
        players.add(new AI());
        players.add(new AI());
        
        Showdown game = new Showdown(players);
        game.start();
    }
}
