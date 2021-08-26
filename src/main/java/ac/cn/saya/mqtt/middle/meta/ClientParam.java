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
     * 设备在网关上的唯一序列号
     */
    private Integer serialNum;

    @Override
    public int hashCode() {
        return (!StringUtils.isEmpty(uuid) && null != serialNum && 0 != serialNum) ? (uuid.hashCode()+serialNum.hashCode()) : 0;
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
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(_this.getUuid()) || null == serialNum || null == _this.getUuid()|| 0 == serialNum || 0 == _this.getSerialNum()){
            return false;
        }
        if (uuid.equals(_this.getUuid()) && serialNum.equals(_this.getSerialNum())){
            return true;
        }
        return false;
    }
}
