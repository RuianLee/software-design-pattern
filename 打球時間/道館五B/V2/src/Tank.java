
public class Tank {
    private int position = 0;

    public void moveForward() {
        position++;
        System.out.println("Tank moved forward. Current position: " + position);
    }

    public void moveBackward() {
        position--;
        System.out.println("Tank moved backward. Current position: " + position);
    }

    public int getPosition() {
        return position;
    }
}
