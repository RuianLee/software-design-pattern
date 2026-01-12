
public class Telecom {
    private boolean isConnected = false;

    public void connect() {
        isConnected = true;
        System.out.println("Telecom connected.");
    }

    public void disconnect() {
        isConnected = false;
        System.out.println("Telecom disconnected.");
    }

    public void undoConnect() {
        isConnected = false;
        System.out.println("Undo connect. Telecom disconnected.");
    }

    public void undoDisconnect() {
        isConnected = true;
        System.out.println("Undo disconnect. Telecom connected.");
    }

    public boolean isConnected() {
        return isConnected;
    }
}
