package ac.cn.saya.mqtt.middle.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Title: ClientParam
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/8/2 15:38
 * @Description: 告警规则元数据键值对象
 */
@AllArgsConstructor
@Setter
@Getter
public class RuleParam {

    /**
     * 设备终端id
     */
    private Integer clientId;

    /**
     * 告警规则id
     */
    private Integer ruleId;

    @Override
    public int hashCode() {
        return (null != clientId && 0 != clientId && null != ruleId && 0 != ruleId) ? (clientId.hashCode()+ruleId.hashCode()) : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (null == obj || getClass() != obj.getClass()){
            return false;
        }
        RuleParam _this = (RuleParam) obj;
        if ( null == ruleId || null == _this.getRuleId() || null == clientId || null == _this.getClientId()|| 0 == clientId || 0 == _this.getClientId() || 0 == ruleId || 0 == _this.getRuleId()){
            return false;
        }
        if ((ruleId.compareTo(_this.getRuleId()) == 0) && (clientId.compareTo( _this.getClientId())==0)){
            return true;
        }
        return false;
    }
}
