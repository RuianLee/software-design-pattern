
public class Role {
    private int hp;
    private int times;
    private String direction;

    public void move(String direction) {
        this.direction = direction;
        System.out.println("Moving " + direction);
    }

    public void attack(String direction) {
        this.direction = direction;
        System.out.println("Attacking " + direction);
    }

    public int getTimes() {
        return times;
    }
}
