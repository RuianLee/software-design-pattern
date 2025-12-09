
public class ConnectCommand implements Command {
    private Telecom telecom;

    public ConnectCommand(Telecom telecom) {
        this.telecom = telecom;
    }

    @Override
    public void execute() {
        telecom.connect();
    }
}
