
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

    public boolean isConnected() {
        return isConnected;
    }
}
