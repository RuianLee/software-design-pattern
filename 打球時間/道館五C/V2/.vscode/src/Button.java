
public class Button {
    private String value;
    private Command command;

    public Button(String value , Command command){
        this.value = value;
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void execute() {
        command.execute();;
    }
}
