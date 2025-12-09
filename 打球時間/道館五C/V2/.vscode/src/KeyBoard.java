import java.util.HashMap;
import java.util.Map;

public class KeyBoard {
    private Map<String, Button> buttons = new HashMap<>();

    public void bindingButton(String keyValue, Command command) {
        buttons.put(keyValue, new Button(keyValue, command));
    }

    public void executeButtonCommand(String keyValue) {
        if(!buttons.containsKey(keyValue)){
            System.out.println("尚未綁定指令");
            return;
        }

        buttons.get(keyValue).execute();
    }

    public void resetAllCommand(){
        this.buttons = new HashMap<>();
        System.out.println("將綁定都消除");
    }
}
