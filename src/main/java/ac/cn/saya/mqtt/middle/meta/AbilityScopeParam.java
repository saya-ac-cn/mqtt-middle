package ac.cn.saya.mqtt.middle.meta;

import java.util.Map;

/**
 * @Title: AbilityScopeParam
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 8/7/21 20:29
 * @Description: 物模型属性字段范围
 */
public class AbilityScopeParam {

    private String beginThreshold;

    private String endThreshold;

    private Map<Integer,String> status;

    public AbilityScopeParam() {
    }

    public AbilityScopeParam(String beginThreshold, String endThreshold) {
        this.beginThreshold = beginThreshold;
        this.endThreshold = endThreshold;
    }

    public AbilityScopeParam(Map<Integer, String> status) {
        this.status = status;
    }

    public String getBeginThreshold() {
        return beginThreshold;
    }

    public void setBeginThreshold(String beginThreshold) {
        this.beginThreshold = beginThreshold;
    }

    public String getEndThreshold() {
        return endThreshold;
    }

    public void setEndThreshold(String endThreshold) {
        this.endThreshold = endThreshold;
    }

    public Map<Integer, String> getStatus() {
        return status;
    }

    public void setStatus(Map<Integer, String> status) {
        this.status = status;
    }
}
