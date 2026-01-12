import java.util.HashMap;
import java.util.Map;

public class KeyBoard {
    private Map<String, Button> oldButtons;
    private Map<String, Button> buttons = new HashMap<>();

    public void bindingButton(String keyValue, Command command) {
        buttons.put(keyValue, new Button(keyValue, command));
    }

    public Command executeButtonCommand(String keyValue) {
        if(!buttons.containsKey(keyValue)){
            System.out.println("尚未綁定指令");
            return null;
        }

        Button target = buttons.get(keyValue);
        target.execute();

        return target.getCommand();
    }

    public void resetAllCommand(){
        oldButtons = new HashMap<>(buttons);
        buttons = new HashMap<>();
        System.out.println("將綁定都消除");
    }

    public void undoReset() {
        if (oldButtons != null) {
            buttons = new HashMap<>(oldButtons);
            oldButtons = null;
            System.out.println("恢復到上次綁定");

            return;
        } 

        System.out.println("沒有上次綁定");
    }
}
