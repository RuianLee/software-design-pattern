package showDown;
import java.util.ArrayList;

public class Showdown {
    private ArrayList<Player> players;
    private Deck deck;

    public Showdown(ArrayList<Player> players) {
        this.players = players;
        this.deck = new Deck();
    }

    public void start() {
        // 每位玩家取名
        for (Player player : players) {
            player.nameSelf();
        }

        // 洗牌
        deck.shuffle();

        // 發牌給每位玩家，每人 <13> 張
        for (int i = 0; i < 13; i++) {
            for (Player player : players) {
                Card card = deck.draw();
                player.getHand().addCard(card);
            }
        }
        
        // <開始遊戲: 不用做任何事>

        // <遊戲開始: 遊戲進行 13 回合結束> 
        for (int round = 1; round <= 13; round++) {
            System.out.println("\n=== Round " + round + " ===");

            // 玩家 round --------------------------------------------------------
            ArrayList<Card> cardsPlayed = new ArrayList<>();
            for (Player player : players) {
                Card card = player.takeTurn();
                cardsPlayed.add(card);
            }

            // 顯示所有玩家出的牌
            for (int i = 0; i < players.size(); i++) {
                System.out.println(players.get(i).getName() + " plays: " + cardsPlayed.get(i));
            }

            // 遊戲 round --------------------------------------------------------
            // 找出最大的牌
            Player winner = findWinner(cardsPlayed);
            winner.gainPoint();
            System.out.println(winner.getName() + " wins this round!");
        }

        // 遊戲結束，找出最終贏家
        System.out.println("\n=== Game Over ===");
        Player finalWinner = findFinalWinner();
        System.out.println("\nFinal Winner: " + finalWinner.getName() +
                           " with " + finalWinner.getPoint() + " points!");

        // 顯示所有玩家分數
        System.out.println("\nFinal Scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getPoint() + " points");
        }
    }

    private Player findWinner(ArrayList<Card> cardsPlayed) {
        int maxIndex = 0;
        for (int i = 1; i < cardsPlayed.size(); i++) {
            if (cardsPlayed.get(i).isGreaterThan(cardsPlayed.get(maxIndex))) {
                maxIndex = i;
            }
        }
        return players.get(maxIndex);
    }

    private Player findFinalWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getPoint() > winner.getPoint()) {
                winner = player;
            }
        }
        return winner;
    }
}
