import java.util.Scanner;

public abstract class Player {
    protected int point;
    protected String name;
    protected Hand hand;

    public Player() {
        this.point = 0;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public Hand getHand() {
        return hand;
    }

    public void nameSelf() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入玩家名稱: ");
        this.name = scanner.nextLine();
        System.out.println("玩家名稱設定為: " + name);
    }

    public void gainPoint() {
        point++;
    }

    public abstract Card takeTurn();
}
