
public class MainController {
    private KeyBoard keyBoard;

    public MainController(KeyBoard keyBoard) {
        this.keyBoard = keyBoard;
    }

    public void setCommand(String keyValue, Command command) {
        keyBoard.bindingButton(keyValue, command);
    }

    public void pressButton(String keyValue) {
        keyBoard.executeButtonCommand(keyValue);
    }
}
