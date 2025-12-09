public class App {
    public static void main(String[] args) throws Exception {
        // Create receivers
        Tank tank = new Tank();
        Telecom telecom = new Telecom();
        KeyBoard keyBoard = new KeyBoard();

        // Create invoker
        MainController mainController = new MainController(keyBoard);

        // Test commands
        System.out.println("=== Testing Tank Commands ===");
        mainController.setCommand("1", new MoveForwardCommand(tank));
        mainController.setCommand("2", new MoveBackwardCommand(tank));
        mainController.setCommand("3", new ConnectCommand(telecom));
        mainController.setCommand("4", new DisconnectCommand(telecom));
        mainController.setCommand("5", new KeyResetCommand(keyBoard));

        mainController.pressButton("1");
        mainController.pressButton("2");
        mainController.pressButton("3");
        mainController.pressButton("4");
        mainController.pressButton("5");
        mainController.pressButton("1");

        mainController.setCommand("1", new MoveForwardCommand(tank));
        mainController.pressButton("1");
        mainController.pressButton("1");
    }
}
