import java.util.Scanner;

public class CLI {
    private Scanner scanner;

    public CLI() {
        scanner = new Scanner(System.in);
    }

    public void showHand(Hand hand) {
        System.out.println("你的手牌：");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.getCard(i));
        }
    }

    public int chooseCard() {
        System.out.print("請選擇要出的牌（輸入編號）：");
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            return choice;
        }
        return 0;
    }

    public void close() {
        scanner.close();
    }
}
