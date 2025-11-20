import java.util.*;

public class Game {
    private Player[] players;
    private CardPattern topPlay;
    private int topPlayerIndex;
    private int currentPlayerIndex;
    private boolean isFirstRound;
    private boolean isFirstPlayInRound;
    private Scanner scanner;

    public Game() {
        players = new Player[4];
    }

    public void startGame(Scanner scanner) {
        this.scanner = scanner;
        initializeGame();
        runGameLoop();
        announceWinner();
    }

    private void initializeGame() {
        Deck deck = readDeck();
        readPlayerNames();
        dealCards(deck);
        currentPlayerIndex = findPlayerWithClub3();

        // 處理初始遊戲狀態
        topPlayerIndex = currentPlayerIndex;
        topPlay = null;
        isFirstRound = true;
    }

    // 處理牌堆的方法 -------------------------------------------------------------------------------------------------
    private Deck readDeck() {
        String deckLine = scanner.nextLine();
        return parseDeck(deckLine);
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

    private void dealCards(Deck deck) {
        int playerIndex = 0;
        while (!deck.isEmpty()) {
            Card card = deck.deal();
            players[playerIndex].addCard(card);
            playerIndex = (playerIndex + 1) % 4;
        }
    }

    // 每個玩家都輸入名稱 ----------------------------------------------------------------------------------------------
    private void readPlayerNames() {
        for (int i = 0; i < 4; i++) {
            String name = scanner.nextLine();
            players[i] = new Player(name);
        }
    }

    // 找到首回合梅花三玩家 --------------------------------------------------------------------------------------------
    private int findPlayerWithClub3() {
        Card club3 = new Card(Suit.CLUB, Rank.THREE);
        for(int i = 0; i < 4; i++){
            for (Card card : players[i].getHandCards()) {
                if (card.equals(club3)) {
                    return i;
                }
            }
        }

        return 0;
    }

    // 開始遊戲流程 ---------------------------------------------------------------------------------------------------
    private void runGameLoop() {
        while (!isGameOver()) {
            playRound();
        }
    }

    private boolean isGameOver() {
        return getCurrentPlayer().hasFinished();
    }

    private Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    // 一個輪裡面回合處理 ---------------------------------------------------------------------------------------------
    private void playRound() {
        startNewRound();
        int passCount = 0;

        while (passCount < 3 && !isGameOver()) {
            showCurrentPlayerTurn();
            passCount = handlePlayerAction(passCount);

            if (!isGameOver()) {
                moveToNextPlayer();
            }
        }

        if (!isGameOver()) {
            currentPlayerIndex = topPlayerIndex;
        }
    }

    private void startNewRound() {
        System.out.println("新的回合開始了。");
        topPlay = null;
        topPlayerIndex = currentPlayerIndex;
        isFirstPlayInRound = true;
    }

    private void showCurrentPlayerTurn() {
        System.out.println("輪到" + getCurrentPlayer().getName() + "了");
        getCurrentPlayer().printHandCards();
    }

    private int handlePlayerAction(int passCount) {
        while (true) {
            String action = scanner.nextLine();

            // 選擇喊 pass ----------------------------------------------------------
            if (isPassAction(action)) {
                if (isFirstPlayInRound) {
                    showCannotPassMessage();
                } else {
                    executePass();
                    return passCount + 1;
                }
            } 
            // 選擇出牌 -------------------------------------------------------------
            else {
                if (tryPlayCards(action)) {
                    return 0;
                }
            }
        }
    }

    private boolean isPassAction(String action) {
        return action.equals("-1");
    }

    private void executePass() {
        System.out.println("玩家 " + getCurrentPlayer().getName() + " PASS.");
    }

    private void showCannotPassMessage() {
        System.out.println("你不能在新的回合中喊 PASS");
    }

    private boolean tryPlayCards(String action) {
        // 處理選擇出牌的牌型 ------------------------------------------------------------
        int[] indices = parseIndices(action);
        List<Card> selectedCards = getSelectedCards(indices);

        if (selectedCards == null) {
            showInvalidPatternMessage();
            return false;
        }

        CardPattern pattern = CardPattern.createPattern(selectedCards);

        if (!isValidPattern(pattern)) {
            showInvalidPatternMessage();
            return false;
        }

        if (!meetsFirstRoundRequirement(pattern)) {
            showInvalidPatternMessage();
            return false;
        }

        if (!beatsTopPlay(pattern)) {
            showInvalidPatternMessage();
            return false;
        }

        executePlay(indices, pattern);
        return true;
    }

    /**
     *  輸入的 "0 2 4" 變成程式可以用的 [0, 2, 4]
     */
    private int[] parseIndices(String action) {
        String[] parts = action.trim().split(" ");
        int[] indices = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indices[i] = Integer.parseInt(parts[i]);
        }
        return indices;
    }

    private List<Card> getSelectedCards(int[] indices) {
        List<Card> selectedCards = new ArrayList<>();

        for (int index : indices) {
            if (index < 0 || index >= getCurrentPlayer().getHandCards().size()) {
                return null;
            }
            selectedCards.add(getCurrentPlayer().getHandCards().get(index));
        }

        return selectedCards;
    }

    private boolean isValidPattern(CardPattern pattern) {
        return pattern != null;
    }

    private boolean meetsFirstRoundRequirement(CardPattern pattern) {
        if (!isFirstRound || !isFirstPlayInRound) {
            return true;
        }

        for (Card card : pattern.getCards()) {
            if (card.getSuit() == Suit.CLUB && card.getRank() == Rank.THREE) {
                return true;
            }
        }
        return false;
    }

    private boolean beatsTopPlay(CardPattern pattern) {
        if (topPlay == null) {
            return true;
        }
        return pattern.isGreaterThan(topPlay);
    }

    private void executePlay(int[] indices, CardPattern pattern) {
        getCurrentPlayer().playCards(indices);
        topPlay = pattern;
        topPlayerIndex = currentPlayerIndex;

        System.out.println("玩家 " + getCurrentPlayer().getName() + " 打出了 " +
                          pattern.getPatternName() + " " + pattern.getCardsString());

        isFirstPlayInRound = false;
        isFirstRound = false;
    }

    private void showInvalidPatternMessage() {
        System.out.println("此牌型不合法，請再嘗試一次。");
    }

    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    private void announceWinner() {
        System.out.println("遊戲結束，遊戲的勝利者為 " + getCurrentPlayer().getName());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        game.startGame(scanner);
        scanner.close();
    }
}
