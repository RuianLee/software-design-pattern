// 這裡定義撲克牌的點數，從 A 到 2
public enum Rank {
    // 括號內的數字會傳給下面的建構子 (constructor)
    A(14),      // A 是最大的牌，值為 14
    K(13),      // K 的值為 13
    Q(12),      // Q 的值為 12
    J(11),      // J 的值為 11
    TEN(10),    // 10 的值為 10
    NINE(9),    // 9 的值為 9
    EIGHT(8),   // 8 的值為 8
    SEVEN(7),   // 7 的值為 7
    SIX(6),     // 6 的值為 6
    FIVE(5),    // 5 的值為 5
    FOUR(4),    // 4 的值為 4
    THREE(3),   // 3 的值為 3
    TWO(2);     // 2 是最小的牌，值為 2

    // 每個 Rank 都有一個整數值屬性
    private int value;

    // 建構子：當建立 enum 時，會把上面括號內的數字存到 value
    Rank(int value) {
        this.value = value;
    }

    // getter 方法：讓外部可以取得這個 Rank 的數值
    public int getValue() {
        return value;
    }
}
