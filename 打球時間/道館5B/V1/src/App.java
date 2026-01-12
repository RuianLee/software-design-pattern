public class App {
    public static void main(String[] args) throws Exception {
        MainController mainController = new MainController(new Tank(), new Telecom(), new KeyBoard());
        mainController.pressButton('f');
    }
}
