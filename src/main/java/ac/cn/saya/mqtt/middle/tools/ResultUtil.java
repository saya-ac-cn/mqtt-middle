package ac.cn.saya.mqtt.middle.tools;

/**
 * 统一返回的外包装类
 */
public class ResultUtil {

    public ResultUtil() {
    }

    /**
     * 用于查询，添加，修改等方法返回值
     *
     * @param data 执行成功后返回的数据
     * @return 包装后的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>().code(0)
        .msg("成功")
        //返回执行成功后的模型
        .data(data);
    }

    /**
     * 用于修改、删除等方法返回的值，只返回操作的结果
     *
     * @return 包装后的数据
     */
    public static <T> Result<T> success() {
        return new Result<T>().code(0).msg("成功");
    }

    /**
     * 用于特殊场景下的返回值（eg：查询学生注册状态）
     *
     * @param code 状态码
     * @param msg 响应消息
     * @param data 执行成功后返回的数据
     * @return 包装后的数据
     */
    public static <T> Result<T> success(int code, String msg, T data) {
        return new Result<T>().code(code).msg(msg).data(data);
    }

    /**
     * 用于错误，异常等方法返回值
     *
     * @param code 状态码
     * @param msg 响应消息
     * @return 包装后的数据
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>().code(code).msg(msg);
    }

    /**
     * 用于错误，异常等方法返回值
     *
     * @param value 异常
     * @return 包装后的数据
     */
    public static <T> Result<T> error(ResultEnum value) {
        return new Result<T>().code(value.getCode()).msg(value.getMsg());
    }

}