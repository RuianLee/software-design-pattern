package uno;


import java.util.ArrayList;

public class Uno {
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> table;

    public Uno(ArrayList<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.table = new ArrayList<>();
    }

    public void start() {
        // 每位玩家取名
        for (Player player : players) {
            player.nameSelf();
        }

        // 洗牌 ------------------------------------------------------------------
        deck.shuffle();

        // 抽牌階段：每位玩家抽 <5> 張牌 -----------------------------------------
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                Card card = deck.draw();
                player.getHand().addCard(card);
            }
        }

        
        // <開始遊戲: 先翻出第一張牌到檯面上>
        Card topCard = deck.draw();
        table.add(topCard);
        System.out.println("\nStarting card on table: " + topCard);

        // <遊戲開始: 輪流出牌直到有玩家出完手牌> --------------------------------
        int currentPlayerIndex = 0;
        Player winner = null;

        while (winner == null) {
            Player currentPlayer = players.get(currentPlayerIndex);

            // 玩家 round --------------------------------------------------------
            Card playedCard = currentPlayer.takeTurn(table.get(table.size() - 1));

            // 如果玩家出牌
            if(playedCard == null){
                Card drawnCard = deck.draw();
                if (drawnCard != null) {
                    currentPlayer.getHand().addCard(drawnCard);
                    System.out.println(currentPlayer.getName() + " drew a card");
                }
            } else {
                table.add(playedCard);
            }

            // 如果牌堆空了，把檯面上除了最新的牌以外的牌放回牌堆
            if (deck.isEmpty() && table.size() > 1) {
                System.out.println("\nDeck is empty! Reshuffling table cards...");
                Card topCardToKeep = table.remove(table.size() - 1);
                for (Card card : table) {
                    deck.addCard(card);
                }
                table.clear();
                table.add(topCardToKeep);
                deck.shuffle();
            }

            // 遊戲 round --------------------------------------------------------
            // 檢查是否有玩家獲勝
            if (currentPlayer.hasWon()) {
                winner = currentPlayer;
            }

            // 下一位玩家
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        // 遊戲結束找最贏的玩家 -----------------------------------------------------
        System.out.println("\n=== Game Over ===");
        System.out.println("Winner: " + winner.getName() + "!");
    }
}
