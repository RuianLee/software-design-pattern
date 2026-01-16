
public class Crood {
    private int xCrood;
    private int yCrood;

    public Crood(int x, int y) {
        this.xCrood = x;
        this.yCrood = y;
    }

    public int getX() {
        return xCrood;
    }
    public int getY() {
        return yCrood;
    }

    public Crood move(String direction) {
        Direction dir = Direction.fromString(direction);
        if (dir != null) {
            return move(dir);
        }
        return new Crood(xCrood, yCrood);
    }

    public Crood move(Direction direction) {
        return new Crood(xCrood + direction.getDx(), yCrood + direction.getDy());
    }

    public void moveDown() {
        this.yCrood += 1;
    }

    public void moveLeft() {
        this.xCrood -= 1;
    }

    public void moveRight() {
        this.xCrood += 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Crood crood = (Crood) obj;
        return xCrood == crood.xCrood && yCrood == crood.yCrood;
    }

    @Override
    public int hashCode() {
        return 31 * xCrood + yCrood;
    }
}