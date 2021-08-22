package ac.cn.saya.mqtt.middle.entity;

import ac.cn.saya.mqtt.middle.meta.AbilityScopeParam;
import ac.cn.saya.mqtt.middle.tools.JackJsonUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: IotAbilityEntity
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 8/7/21 20:03
 * @Description: 物模型属性
 */

public class IotAbilityEntity extends BaseEntity{


    private static final long serialVersionUID = -4379644254454353199L;

    private Integer id;

    /**
     * 所属品类名（外键）
     */
    private Integer productId;

    /**
     * 属性符号
     */
    private String identifier;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性类型（1：数值类型，2：状态类型）
     */
    private Integer type;

    /**
     * 属性值范围
     */
    private String scope;


    /**
     * 读写标志（1：读，2：写，3：读写）
     */
    private Integer rwFlag;

    public IotAbilityEntity() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public AbilityScopeParam getScope() {
        if (StringUtils.isEmpty(scope)){
            return null;
        }
        // 将阈值数据（json对象）转换成java类型
        Map<String, Object> map = JackJsonUtil.objectToMap(scope);
        if (1 == type){
            // 返回数值类型的范围
            if (map.containsKey("begin") && map.containsKey("end")){
                return new AbilityScopeParam(String.valueOf(map.get("begin")),String.valueOf(map.get("end")));
            }
        }else {
            // 返回状态类型的数据
            Map<Integer, String> status = new HashMap<>(map.size());
            for (Map.Entry<String,Object> item :map.entrySet()) {
                item.getKey();
                status.put(Integer.valueOf(item.getKey()),String.valueOf(item.getValue()));
            }
            return new AbilityScopeParam(status);
        }
        return null;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRwFlag() {
        return rwFlag;
    }

    public void setRwFlag(Integer rwFlag) {
        this.rwFlag = rwFlag;
    }
}
