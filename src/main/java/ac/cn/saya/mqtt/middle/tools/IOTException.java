package ac.cn.saya.mqtt.middle.tools;
/**
 * @描述 自定义的异常处理
 * @参数
 * @返回值  
 * @创建人  saya.ac.cn-刘能凯
 * @创建时间  2020/11/08
 * @修改人和其它信息
 */
public class IOTException extends RuntimeException {

    private int code;

    public IOTException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public IOTException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
