package showDown;
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

    public int selectCard(Hand hand) {
        displayHand(hand);
        System.out.print("Select a card to play (1-" + hand.size() + "): ");
        int choice = scanner.nextInt();
        while (choice < 1 || choice > hand.size()) {
            System.out.print("Invalid choice. Please select (1-" + hand.size() + "): ");
            choice = scanner.nextInt();
        }
        return choice - 1; // 轉換為 index
    }

    public String inputName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }
}
