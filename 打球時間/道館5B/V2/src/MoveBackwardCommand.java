
public class MoveBackwardCommand implements Command {
    private Tank tank;

    public MoveBackwardCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.moveBackward();
    }

    @Override
    public void undo() {
        tank.moveForward();
    }
}
