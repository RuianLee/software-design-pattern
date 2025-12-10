import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
 // 創建接收者物件
 Tank tank = new Tank();
 Telecom telecom = new Telecom();
 KeyBoard keyBoard = new KeyBoard();

 // 創建控制器
 MainController controller = new MainController(keyBoard);

 // 設定命令到按鈕
 // button: 0 - 坦克前進
 controller.setCommand("0", new MoveForwardCommand(tank));
 // button: 1 - 坦克後退
 controller.setCommand("1", new MoveBackwardCommand(tank));
 // button: 2 - 電信連接
 controller.setCommand("2", new ConnectCommand(telecom));
 // button: 3 - 電信斷開
 controller.setCommand("3", new DisconnectCommand(telecom));
 // button: 4 - 鍵盤重置
 controller.setCommand("4", new KeyResetCommand(keyBoard));
 // button: 5 - 坦克前進（重複命令用於測試）
 controller.setCommand("5", new MoveForwardCommand(tank));

 // 顯示使用說明
 System.out.println("=== 命令模式互動測試 ===");
 System.out.println("按鈕功能：");
 System.out.println("  0 - 坦克前進");
 System.out.println("  1 - 坦克後退");
 System.out.println("  2 - 電信連接");
 System.out.println("  3 - 電信斷開");
 System.out.println("  4 - 鍵盤重置");
 System.out.println("  5 - 坦克前進");
 System.out.println("  -1 - 撤銷 (Undo)");
 System.out.println("  -2 - 重做 (Redo)");
 System.out.println("輸入 'q' 或 'quit' 退出程式");
 System.out.println("================================\n");

 // 創建掃描器接收使用者輸入
 Scanner scanner = new Scanner(System.in);

 // 互動循環
 while (true) {
     System.out.print("點擊按鈕 (0~5) 或 撤銷(-1) 或 重做(-2): ");
     
     String input = scanner.nextLine().trim();
     
     // 檢查是否要退出
     if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
         System.out.println("程式結束。");
         break;
     }

     try {
         int button = Integer.parseInt(input);

         if (button == -1) {
             // 撤銷操作
             controller.undo();
         } else if (button == -2) {
             // 重做操作
             controller.redo();
         } else if (button >= 0 && button <= 5) {
             // 執行按鈕命令
             controller.pressButton(String.valueOf(button));
         } else {
             System.out.println("無效的輸入！請輸入 0~5、-1 (撤銷) 或 -2 (重做)");
         }
     } catch (NumberFormatException e) {
         System.out.println("無效的輸入！請輸入數字、'q' 或 'quit'");
     }
     
     System.out.println(); // 空行分隔
 }

 scanner.close();
}
}
