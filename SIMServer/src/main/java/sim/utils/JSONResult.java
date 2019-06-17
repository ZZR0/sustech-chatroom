package sim.utils;

/**
 * This class is to return json result
 *
 * @Description: Custom response data structure              This class is for portal, ios, Android, WeChat Mall              After accepting such data, the portal needs to use the method of this class to convert to the data type format (class, or list).              Other self-handling 				200：success 				500：error in msg segment 				501：Bean validation error, no matter how many errors are returned as a map 				502：The interceptor intercepted the user token error 				555：exception
 */
public class JSONResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    /**
     * Build json result.
     *
     * @param status the status
     * @param msg    the msg
     * @param data   the data
     * @return the json result
     */
    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }

    /**
     * Ok json result.
     *
     * @param data the data
     * @return the json result
     */
    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    /**
     * Ok json result.
     *
     * @return the json result
     */
    public static JSONResult ok() {
        return new JSONResult(null);
    }

    /**
     * Error msg json result.
     *
     * @param msg the msg
     * @return the json result
     */
    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    /**
     * Error map json result.
     *
     * @param data the data
     * @return the json result
     */
    public static JSONResult errorMap(Object data) {
        return new JSONResult(501, "error", data);
    }

    /**
     * Error token msg json result.
     *
     * @param msg the msg
     * @return the json result
     */
    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(502, msg, null);
    }

    /**
     * Error exception json result.
     *
     * @param msg the msg
     * @return the json result
     */
    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    /**
     * Instantiates a new Json result.
     *
     * @param status the status
     * @param msg    the msg
     * @param data   the data
     */
    public JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * Instantiates a new Json result.
     *
     * @param data the data
     */
    public JSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    /**
     * Is ok boolean.
     *
     * @return the boolean
     */
    public Boolean isOK() {
        return this.status == 200;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
