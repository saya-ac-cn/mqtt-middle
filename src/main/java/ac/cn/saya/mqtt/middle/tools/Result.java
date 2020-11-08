package ac.cn.saya.mqtt.middle.tools;

import java.io.Serializable;

/**
 * 统一返回的子内容
 * @param <T>
 * @author saya
 * @date 2016/10/31
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8753496894647207237L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 成功时返回 null，失败时返回具体错误消息
     */
    private String msg;

    /**
     * 成功时具体返回值，失败时为 null
     */
    private T data;

    public int getCode() {
        return code;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

}
