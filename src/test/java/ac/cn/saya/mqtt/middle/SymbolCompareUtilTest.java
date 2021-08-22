package ac.cn.saya.mqtt.middle;

import ac.cn.saya.mqtt.middle.tools.SymbolCompareUtil;
import org.junit.jupiter.api.Test;

/**
 * @Title: SymbolCompareUtilTest
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 4/11/21 21:41
 * @Description:
 */

public class SymbolCompareUtilTest {

    @Test
    public void testEQ(){
        System.out.println("result:"+ SymbolCompareUtil.compare("EQ","20.0","","20.0"));
    }

    @Test
    public void testNEQ(){
        System.out.println("result:"+ SymbolCompareUtil.compare("NEQ","20.0","","19.99999"));
    }

    @Test
    public void testGT(){
        System.out.println("result:"+ SymbolCompareUtil.compare("GT","20","","20.1"));
    }

    @Test
    public void testLT(){
        System.out.println("result:"+ SymbolCompareUtil.compare("LT","20","","19.999999"));
    }

    @Test
    public void testGTE(){
        System.out.println("result:"+ SymbolCompareUtil.compare("GTE","20","","19.0"));
    }

    @Test
    public void testLTE(){
        System.out.println("result:"+ SymbolCompareUtil.compare("LTE","20","","19"));
    }

    @Test
    public void testRANGE(){
        System.out.println("result:"+ SymbolCompareUtil.compare("RANGE","20","30","30"));
    }

}
