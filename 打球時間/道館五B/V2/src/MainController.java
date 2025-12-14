import java.util.Stack;

public class MainController {
    private KeyBoard keyBoard;
    private Stack<Command> commandHistory = new Stack<>();
    private Stack<Command> undoHistory = new Stack<>();

    public MainController(KeyBoard keyBoard) {
        this.keyBoard = keyBoard;
    }

    public void setCommand(String keyValue, Command command) {
        keyBoard.bindingButton(keyValue, command);
    }

    public void pressButton(String keyValue) {
        Command executedCommand = keyBoard.executeButtonCommand(keyValue);
        
        commandHistory.push(executedCommand);
        undoHistory.clear();
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command executedCommand = commandHistory.pop();
            executedCommand.undo();
            
            undoHistory.push(executedCommand);
        }
    }

    public void redo() {
        if (!undoHistory.isEmpty()) {
            Command executedUndoCommand = undoHistory.pop();
            executedUndoCommand.execute();
        
            commandHistory.push(executedUndoCommand);
        }
    }
}
