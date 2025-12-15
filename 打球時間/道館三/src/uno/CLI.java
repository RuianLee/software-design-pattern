package uno;
import java.util.Scanner;
import java.util.ArrayList;

public class CLI {
    private Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayHand(Hand hand) {
        ArrayList<Card> cards = hand.getCards();
        System.out.println("\nYour hand:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ": " + cards.get(i));
        }
    }

    public void displayTopCard(Card topCard) {
        System.out.println("\nTop card on table: " + topCard);
    }

    public int selectCard(Hand hand, Card topCard) {
        displayTopCard(topCard);
        displayHand(hand);

        // 顯示可以出的牌
        ArrayList<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.getCard(i).canPlayOn(topCard)) {
                validMoves.add(i + 1);
            }
        }

        if (validMoves.isEmpty()) {
            System.out.println("No valid cards to play. You must draw a card.");
            return -1; // 表示沒有可出的牌
        }

        System.out.print("Select a card to play (valid choices: " + validMoves + "): ");
        int choice = scanner.nextInt();
        while (!validMoves.contains(choice)) {
            System.out.print("Invalid choice. Please select from " + validMoves + ": ");
            choice = scanner.nextInt();
        }
        return choice - 1; // 轉換為 index
    }

    public String inputName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }
}
