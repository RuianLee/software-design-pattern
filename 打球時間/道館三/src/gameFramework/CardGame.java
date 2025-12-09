package gameFramework;

import java.util.ArrayList;

public abstract class CardGame<P extends Player<C>, C extends Card> {
    private ArrayList<P> players;
    private Deck<C> deck;

    public CardGame(ArrayList<P> players) {
        this.players = players;
        this.deck = new Deck<C>();
    }

    private void nameThemselves(){
        // 每位玩家取名
        for (P player : players) {
            player.nameSelf();
        }
    }

    private void drawHands() {
        int initialHandSize = getInitialHandSize();
        for (int i = 0; i < initialHandSize; i++) {
            for (P player : players) {
                C card = deck.draw();
                player.addHandCard(card);
            }
        }
    }

    protected abstract int getInitialHandSize();

    protected void onGameBegins() {
        // hook
    }

    protected void nextTurn() {
        // hook
    }



   public void start() {
        nameThemselves();
        deck.shuffle();  // 洗牌
        drawHands();
        nextTurn();
        
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

}
