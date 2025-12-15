import java.util.ArrayList;

public class Showdown {
    private ArrayList<Player> players;
    private Deck deck;

    public Showdown() {
        players = new ArrayList<Player>();
        deck = new Deck();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        for (Player player : players) {
            player.nameSelf();
        }

        deck.shuffle();

        for (int i = 0; i < 13; i++) {
            for (Player player : players) {
                Card card = deck.draw();
                player.getHand().addCard(card);
            }
        }

        for (int round = 0; round < 13; round++) {
            System.out.println("\n第 " + (round + 1) + " 回合：");

            ArrayList<Card> playedCards = new ArrayList<Card>();
            for (Player player : players) {
                Card card = player.takeTurn();
                playedCards.add(card);
            }

            System.out.println("\n各玩家出的牌：");
            for (int i = 0; i < players.size(); i++) {
                System.out.println(players.get(i).getName() + " 打出：" + playedCards.get(i));
            }

            Player winner = players.get(0);
            Card winningCard = playedCards.get(0);

            for (int i = 1; i < players.size(); i++) {
                if (playedCards.get(i).compare(winningCard) > 0) {
                    winner = players.get(i);
                    winningCard = playedCards.get(i);
                }
            }

            winner.gainPoint();
            System.out.println("本回合贏家：" + winner.getName());
        }

        Player finalWinner = players.get(0);
        for (Player player : players) {
            if (player.getPoint() > finalWinner.getPoint()) {
                finalWinner = player;
            }
        }

        System.out.println("\n遊戲結束！");
        System.out.println("最終勝利者：" + finalWinner.getName());
        System.out.println("積分：" + finalWinner.getPoint());
    }
}
