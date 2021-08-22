package ac.cn.saya.mqtt.middle.enums;

/**
 * @Title: SymbolEnum
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 4/11/21 21:02
 * @Description: 运算符枚举
 */

public enum SymbolEnum {
    EQ("等于"),
    NEQ("不等于"),
    GT("大于"),
    GTE("大于等于"),
    LT("小于"),
    LTE("小于等于"),
    RANGE("范围");

    private final String descript;

    SymbolEnum(String descript) {
        this.descript = descript;
    }
}
