# 重構差異清單

## 1. 新增 `Direction` 類別 (enum)

| 項目 | 說明 |
|------|------|
| 檔案 | `src/Direction.java` (新增) |
| 功能 | 封裝方向邏輯（UP, DOWN, LEFT, RIGHT） |
| 屬性 | `symbol` (↑↓←→), `dx`, `dy` |
| 方法 | `move(Crood)`, `fromString()`, `random()`, `isVertical()`, `isHorizontal()` |

---

## 2. `Crood` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| move 方法 | `move(String direction)` 用 if-else 判斷 | 新增 `move(Direction direction)` 方法，舊方法委託給新方法 |

---

## 3. `Role` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| touch 方法 | 無，邏輯散在 `move()` 中 | 新增 `touch(Crood)` 方法處理碰撞 |
| die 方法 | 無，直接在 `getDamage()` 中呼叫 `map.deleteRole()` | 新增 `die()` 方法，子類別可覆寫 |
| isValidDirection | 用 `Arrays.asList()` 判斷 | 改用 `Direction.fromString()` |
| import | `import java.util.Arrays;` | 移除 |

---

## 4. `Hero` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| 方向屬性 | 用 `char symbol` 存方向 | 新增 `Direction direction` 屬性 |
| constructor | `SYMBOLS[(int)(Math.random() * 4)]` | `Direction.random()` |
| updateSymbol | 用 if-else 設定 char | 改名 `updateDirection()`，使用 `Direction.fromString()` |
| getDirectionFromSymbol | 用 if-else 從 char 轉 String | 移除，直接用 `direction` 屬性 |
| attackLine | 呼叫 `getGameMap().procAttack()` | 攻擊邏輯內嵌在 Hero 中（從 GameMap 移來） |

---

## 5. `Monster` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| die 方法 | 無 | 新增 `die()` 覆寫父類別，印出死亡訊息 |

---

## 6. `GameMap` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| hero 屬性 | 有 | 移除，移到 Game |
| monsters 屬性 | 有 | 移除，移到 Game |
| buildHero() | 有 | 移除，移到 Game |
| buildMonsters() | 有 | 移除，移到 Game |
| rolesPlay() | 有 | 移除，移到 Game |
| deleteRole() | 有 | 改名為 `removeFromMap()`，只負責從地圖移除 |
| procAttack() | 有 | 移除，移到 Hero（攻擊是角色行為，不是地圖功能） |
| isObstacle() | 有 | 移除（不再需要） |
| isMonster() | 有 | 移除（不再需要） |

---

## 7. `Game` 類別

| 項目 | 舊 | 新 |
|------|-----|-----|
| hero 屬性 | 無 | 新增，從 GameMap 移來 |
| monsters 屬性 | 無 | 新增，從 GameMap 移來 |
| buildHero() | 無 | 新增，從 GameMap 移來 |
| buildMonsters() | 無 | 新增，從 GameMap 移來 |
| rolesPlay() | 無 | 新增，從 GameMap 移來 |
| removeMonster() | 無 | 新增，供 Monster.die() 呼叫 |
| constructor | `Game(GameMap map)` | `Game()` 自己建立 GameMap |

---

## 8. `Role` 類別（第二次重構）

| 項目 | 舊 | 新 |
|------|-----|-----|
| 依賴 | `GameMap map` | `Game game` |
| getGameMap() | 回傳 `map` | 回傳 `game.getMap()` |
| getGame() | 無 | 新增 |

---

## 主要改善

1. **類型安全** - 用 `Direction` enum 取代 String，避免拼寫錯誤
2. **職責分離** - `touch()` 方法明確處理碰撞，`die()` 方法明確處理死亡
3. **可擴展性** - Monster 可以覆寫 `die()` 加入特殊死亡邏輯
4. **減少重複** - 方向相關邏輯集中在 `Direction` 類別
5. **單一職責原則 (SRP)** - `GameMap` 只負責地圖座標管理，`Game` 負責角色生命週期和遊戲邏輯

---

## 什麼是「承擔太多責任」？

這是 **單一職責原則 (Single Responsibility Principle, SRP)** 的概念：一個類別應該只有一個「改變的理由」。

### 重構前 GameMap 的問題

| 責任類型 | 方法 | 改變的理由 |
|----------|------|------------|
| 地圖顯示 | `printMapMsg()` | 顯示格式要改 |
| 座標管理 | `getMapObjectAt()`, `setMapObjectAt()` | 地圖結構要改 |
| 角色生命週期 | `buildHero()`, `buildMonsters()`, `deleteRole()` | 角色建立規則要改 |
| 回合執行 | `rolesPlay()` | 遊戲流程要改 |
| 攻擊處理 | `procAttack()` | 戰鬥規則要改 |

**問題**：當戰鬥規則要改時，需要修改 GameMap；當遊戲流程要改時，也需要修改 GameMap。這讓 GameMap 變成一個「萬能類別」，難以維護。

### 重構後的職責分離

| 類別 | 職責 | 改變的理由 |
|------|------|------------|
| **GameMap** | 地圖座標管理、顯示 | 地圖結構或顯示格式要改 |
| **Game** | 角色管理、遊戲流程、勝負判斷 | 遊戲規則要改 |
| **Hero** | Hero 的行為（移動、攻擊） | Hero 行為要改 |
| **Monster** | Monster 的行為（移動、攻擊、死亡） | Monster 行為要改 |
| **Role** | 角色共用行為（狀態、傷害、碰撞） | 角色共用行為要改 |
