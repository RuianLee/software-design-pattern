public class KeyResetCommand implements Command {
    private KeyBoard keyBoard;

    public KeyResetCommand(KeyBoard keyBoard) {
        this.keyBoard = keyBoard;
    }

    @Override
    public void execute() {
        keyBoard.resetAllCommand();
    }
}

