package ac.cn.saya.mqtt.middle.tools;

import ac.cn.saya.mqtt.middle.enums.SymbolEnum;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Title: SymbolCompareUtil
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 4/11/21 21:13
 * @Description: 运算符重载比较
 */

public class SymbolCompareUtil {

    /**
     * 根据symbol执行相应的比较
     * @param op 运算符
     * @param ruleValue1 规则参数1
     * @param ruleValue2 规则参数2
     * @param realValue 实际形参
     * @return
     */
    public static final boolean compare(SymbolEnum op,String ruleValue1,String ruleValue2,String realValue){
        try {
            if (Objects.isNull(op) || StringUtils.isEmpty(realValue)){
                throw new NullPointerException("无效参数");
            }
            // 第一参数为空，或者是范围比较时第二参数为空
            if (StringUtils.isEmpty(ruleValue1) || ((SymbolEnum.RANGE == op && StringUtils.isEmpty(ruleValue2)))){
                throw new IllegalArgumentException("无效比较参数");
            }

            BigDecimal collectionValue = new BigDecimal(realValue);
            BigDecimal value1 = new BigDecimal(ruleValue1);
            switch (op){
                case EQ:
                    return collectionValue.compareTo(value1) == 0;
                case NEQ:
                    return collectionValue.compareTo(value1) != 0;
                case GT:
                    return collectionValue.compareTo(value1) == 1;
                case LT:
                    return collectionValue.compareTo(value1) == -1;
                case GTE:
                    return collectionValue.compareTo(value1) > -1;
                case LTE:
                    return collectionValue.compareTo(value1) < 1;
                case RANGE:{
                    BigDecimal value2 = new BigDecimal(ruleValue2);
                    if (collectionValue.compareTo(value1) > -1 && collectionValue.compareTo(value2) < 1){
                        return true;
                    }else {
                        return false;
                    }
                }
                default:
                    return false;
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取运算符失败", e,SymbolCompareUtil.class);
            return false;
        }
    }

}
