import java.util.List;
import java.util.Scanner;

/**
 * 遊戲主控制類別，處理遊戲迴圈和使用者輸入
 */
public class Game {
    private Map map;
    private Scanner scanner;

    public Game() {
        this.map = new Map();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            // 隨機生成新的寶物和怪物
            map.spawnRandomObjects();    
 
            // 取得該回合行動的角色
            Character character = map.getCharacter();
            List<Monster> monsters = map.getMonsters();
 
            // 回合開始時的狀態效果
            character.onRoundStart();
            monsters.forEach(Monster::onRoundStart);

            // 顯示地圖和主角狀態
            map.show();
            System.out.println("HP: " + character.getHP() + " | 狀態: " + character.getState());

            // 主角回合
            for (int i = 0; i < character.getActionCount(); i++) {
                System.out.println("\n--- 第 " + (i + 1) + " 次動作 ---");

                String command;
                if (character.getState().equals("Orderless")) {
                    // 混亂狀態只能移動
                    System.out.print("混亂狀態：只能移動！請輸入移動方向 (上/下/左/右/q): ");
                    command = scanner.nextLine();
                } else {
                    // 正常狀態可以選擇動作
                    System.out.print("請選擇動作 (1.移動 / 2.攻擊 / q.離開): ");
                    String action = scanner.nextLine();

                    if (action.equals("q")) {
                        command = "q";
                    } else if (action.equals("1")) {
                        System.out.print("請輸入移動方向 (上/下/左/右): ");
                        command = scanner.nextLine();
                    } else if (action.equals("2")) {
                        command = "攻擊";
                    } else {
                        System.out.println("無效的選項！");
                        i--; // 重新輸入
                        continue;
                    }
                }

                if (command.equals("q")) {
                    scanner.close();
                    System.out.println("遊戲結束");
                    return;
                }

                // 讓 Character 自己處理指令
                boolean success = character.handleCommand(command, map);
                if (!success) {
                    i--; // 失敗則重新輸入
                }
            }

            // Monster 回合
            System.out.println("\n=== 怪物回合 ===");
            for (Monster monster : monsters) {
                monster.executeTurn(character, map);
            }

            // 回合結束處理
            if (character.decreaseStateRound()) {
                map.teleportRole(character);
            }
            for (Monster monster : map.getMonsters()) {
                if (monster.decreaseStateRound()) {
                    map.teleportRole(monster);
                }
            }

            // 檢查主角是否死亡
            if (character.getHP() <= 0) {
                System.out.println("\n主角死亡！遊戲結束！");
                break;
            }

            // 檢查是否所有怪物都被消滅
            if (map.getMonsters().isEmpty()) {
                System.out.println("\n所有怪物都被消滅了！你獲得勝利！");
                break;
            }
        }

        scanner.close();
        System.out.println("遊戲結束");
    }
}
