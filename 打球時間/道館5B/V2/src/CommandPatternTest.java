public class CommandPatternTest {
    public static void main(String[] args) {
        System.out.println("=== 命令模式自動化測試 ===\n");

        // 測試 1: 基本命令執行
        System.out.println("測試 1: 基本命令執行");
        testBasicCommands();
        System.out.println();

        // 測試 2: 撤銷功能
        System.out.println("測試 2: 撤銷功能");
        testUndo();
        System.out.println();

        // 測試 3: 重做功能
        System.out.println("測試 3: 重做功能");
        testRedo();
        System.out.println();

        // 測試 4: 多個命令的撤銷和重做
        System.out.println("測試 4: 多個命令的撤銷和重做");
        testMultipleUndoRedo();
        System.out.println();

        // 測試 5: 鍵盤重置功能
        System.out.println("測試 5: 鍵盤重置功能");
        testKeyReset();
        System.out.println();

        System.out.println("=== 所有測試完成 ===");
    }

    private static void testBasicCommands() {
        Tank tank = new Tank();
        Telecom telecom = new Telecom();
        KeyBoard keyBoard = new KeyBoard();
        MainController controller = new MainController(keyBoard);

        // 設定命令
        controller.setCommand("0", new MoveForwardCommand(tank));
        controller.setCommand("1", new MoveBackwardCommand(tank));
        controller.setCommand("2", new ConnectCommand(telecom));
        controller.setCommand("3", new DisconnectCommand(telecom));

        // 執行命令
        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("執行按鈕 1 (坦克後退):");
        controller.pressButton("1");
        
        System.out.println("執行按鈕 2 (電信連接):");
        controller.pressButton("2");
        
        System.out.println("執行按鈕 3 (電信斷開):");
        controller.pressButton("3");
    }

    private static void testUndo() {
        Tank tank = new Tank();
        KeyBoard keyBoard = new KeyBoard();
        MainController controller = new MainController(keyBoard);

        controller.setCommand("0", new MoveForwardCommand(tank));
        controller.setCommand("1", new MoveBackwardCommand(tank));

        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("撤銷上一個命令:");
        controller.undo();
        
        System.out.println("撤銷上一個命令:");
        controller.undo();
    }

    private static void testRedo() {
        Tank tank = new Tank();
        KeyBoard keyBoard = new KeyBoard();
        MainController controller = new MainController(keyBoard);

        controller.setCommand("0", new MoveForwardCommand(tank));

        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("撤銷上一個命令:");
        controller.undo();
        
        System.out.println("重做被撤銷的命令:");
        controller.redo();
    }

    private static void testMultipleUndoRedo() {
        Tank tank = new Tank();
        Telecom telecom = new Telecom();
        KeyBoard keyBoard = new KeyBoard();
        MainController controller = new MainController(keyBoard);

        controller.setCommand("0", new MoveForwardCommand(tank));
        controller.setCommand("1", new MoveBackwardCommand(tank));
        controller.setCommand("2", new ConnectCommand(telecom));
        controller.setCommand("3", new DisconnectCommand(telecom));

        System.out.println("執行多個命令:");
        controller.pressButton("0");
        controller.pressButton("1");
        controller.pressButton("2");
        controller.pressButton("3");

        System.out.println("\n撤銷所有命令:");
        controller.undo();
        controller.undo();
        controller.undo();
        controller.undo();

        System.out.println("\n重做所有命令:");
        controller.redo();
        controller.redo();
        controller.redo();
        controller.redo();
    }

    private static void testKeyReset() {
        Tank tank = new Tank();
        Telecom telecom = new Telecom();
        KeyBoard keyBoard = new KeyBoard();
        MainController controller = new MainController(keyBoard);

        // 設定初始命令
        controller.setCommand("0", new MoveForwardCommand(tank));
        controller.setCommand("1", new MoveBackwardCommand(tank));
        controller.setCommand("2", new ConnectCommand(telecom));
        controller.setCommand("4", new KeyResetCommand(keyBoard));

        System.out.println("執行按鈕 0 (坦克前進):");
        controller.pressButton("0");
        
        System.out.println("執行按鈕 2 (電信連接):");
        controller.pressButton("2");
        
        System.out.println("執行按鈕 4 (鍵盤重置):");
        controller.pressButton("4");
        
        System.out.println("撤銷重置操作:");
        controller.undo();
    }
}
