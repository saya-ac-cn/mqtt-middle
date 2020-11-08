package ac.cn.saya.mqtt.middle.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @Title: ClientParam
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/8/2 15:38
 * @Description: 设备元数据键值对象
 */
@AllArgsConstructor
@Setter
@Getter
public class ClientParam {

    private String code;

    private Integer clientId;


    @Override
    public int hashCode() {
        return (!StringUtils.isEmpty(code) && null != clientId && 0 != clientId) ? (code.hashCode()+clientId.hashCode()) : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (null == obj || getClass() != obj.getClass()){
            return false;
        }
        ClientParam _this = (ClientParam) obj;
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(_this.getCode()) || null == clientId || null == _this.getCode()|| 0 == clientId || 0 == _this.getClientId()){
            return false;
        }
        if (code.equals(_this.getCode()) && clientId.equals(_this.getClientId())){
            return true;
        }
        return false;
    }
}
