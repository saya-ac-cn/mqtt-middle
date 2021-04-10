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

    /**
     * 网关编码
     */
    private String uuid;

    /**
     * 设备编号
     */
    private Integer clientId;


    @Override
    public int hashCode() {
        return (!StringUtils.isEmpty(uuid) && null != clientId && 0 != clientId) ? (uuid.hashCode()+clientId.hashCode()) : 0;
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
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(_this.getUuid()) || null == clientId || null == _this.getUuid()|| 0 == clientId || 0 == _this.getClientId()){
            return false;
        }
        if (uuid.equals(_this.getUuid()) && clientId.equals(_this.getClientId())){
            return true;
        }
        return false;
    }
}
