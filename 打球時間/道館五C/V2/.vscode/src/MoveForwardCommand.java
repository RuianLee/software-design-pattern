
public class MoveForwardCommand implements Command {
    private Tank tank;

    public MoveForwardCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.moveForward();
    }

    @Override
    public void undo() {
        tank.moveBackward();
    }
}
