import java.util.*;

public class Game {
    private Player[] players;
    private CardPattern topPlay;
    private int topPlayerIndex;

    public Game() {
        players = new Player[4];
    }

    public void startGame(Scanner scanner) {
        // Read deck
        String deckLine = scanner.nextLine();
        Deck deck = parseDeck(deckLine);

        // Read player names
        for (int i = 0; i < 4; i++) {
            String name = scanner.nextLine();
            players[i] = new Player(name);
        }

        // Deal cards
        int playerIndex = 0;
        while (!deck.isEmpty()) {
            Card card = deck.deal();
            players[playerIndex].addCard(card);
            playerIndex = (playerIndex + 1) % 4;
        }

        // Find player with C[3]
        int currentPlayer = findPlayerWithClub3();
        topPlayerIndex = currentPlayer;
        topPlay = null;

        boolean isFirstRound = true;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("新的回合開始了。");
            clearTopPlay();
            topPlayerIndex = currentPlayer;

            int passCount = 0;
            boolean isFirstPlayInRound = true;

            while (passCount < 3 && !gameOver) {
                System.out.println("輪到" + players[currentPlayer].getName() + "了");
                players[currentPlayer].printHandCards();

                boolean validAction = false;
                while (!validAction) {
                    String action = scanner.nextLine();

                    if (action.equals("-1")) {
                        // PASS
                        if (isFirstPlayInRound) {
                            System.out.println("你不能在新的回合中喊 PASS");
                        } else {
                            System.out.println("玩家 " + players[currentPlayer].getName() + " PASS.");
                            passCount++;
                            validAction = true;
                        }
                    } else {
                        // Play cards
                        int[] indices = parseIndices(action);
                        List<Card> selectedCards = new ArrayList<>();
                        boolean validIndices = true;

                        for (int index : indices) {
                            if (index < 0 || index >= players[currentPlayer].getHandCards().size()) {
                                validIndices = false;
                                break;
                            }
                            selectedCards.add(players[currentPlayer].getHandCards().get(index));
                        }

                        if (!validIndices) {
                            System.out.println("此牌型不合法，請再嘗試一次。");
                            continue;
                        }

                        CardPattern pattern = CardPattern.createPattern(selectedCards);

                        if (pattern == null) {
                            System.out.println("此牌型不合法，請再嘗試一次。");
                            continue;
                        }

                        // Check if first round requires C[3]
                        if (isFirstRound && isFirstPlayInRound) {
                            boolean hasClub3 = false;
                            for (Card card : pattern.getCards()) {
                                if (card.getSuit() == Suit.CLUB && card.getRank() == Rank.THREE) {
                                    hasClub3 = true;
                                    break;
                                }
                            }
                            if (!hasClub3) {
                                System.out.println("此牌型不合法，請再嘗試一次。");
                                continue;
                            }
                        }

                        // Check if pattern beats top play
                        if (topPlay != null && !pattern.isGreaterThan(topPlay)) {
                            System.out.println("此牌型不合法，請再嘗試一次。");
                            continue;
                        }

                        // Valid play
                        players[currentPlayer].playCards(indices);
                        topPlay = pattern;
                        topPlayerIndex = currentPlayer;
                        passCount = 0;

                        System.out.println("玩家 " + players[currentPlayer].getName() + " 打出了 " + pattern.getPatternName() + " " + pattern.getCardsString());

                        if (players[currentPlayer].hasFinished()) {
                            gameOver = true;
                        }

                        isFirstPlayInRound = false;
                        isFirstRound = false;
                        validAction = true;
                    }
                }

                if (!gameOver) {
                    currentPlayer = (currentPlayer + 1) % 4;
                }
            }

            if (!gameOver) {
                currentPlayer = topPlayerIndex;
            }
        }

        System.out.println("遊戲結束，遊戲的勝利者為 " + players[currentPlayer].getName());
    }

    private Deck parseDeck(String line) {
        Deck deck = new Deck();
        String[] cardStrs = line.split(" ");
        for (String cardStr : cardStrs) {
            Card card = Card.fromString(cardStr);
            deck.addCard(card);
        }
        return deck;
    }

    private int findPlayerWithClub3() {
        Card club3 = new Card(Suit.CLUB, Rank.THREE);
        for (int i = 0; i < 4; i++) {
            for (Card card : players[i].getHandCards()) {
                if (card.equals(club3)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void clearTopPlay() {
        topPlay = null;
    }

    private int[] parseIndices(String action) {
        String[] parts = action.trim().split(" ");
        int[] indices = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indices[i] = Integer.parseInt(parts[i]);
        }
        return indices;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        game.startGame(scanner);
        scanner.close();
    }
}
