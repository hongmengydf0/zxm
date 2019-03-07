package zxm.common.result;

import java.util.HashMap;

/**
 * 返回的结果
 */
public class Result extends HashMap<String, Object> {
    public Result setCode(int code) {
        put("code", code);
        return this;
    }

    public Result setCode(ResultCode resultCode) {
        put("code", resultCode.code);
        return this;
    }

    public Double getCode() {
        return (Double) get("code");
    }

    public String getMsg() {
        return (String) get("msg");
    }

    public Result setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public Object getData() {
        return get("data");
    }

    public Result setData(Object data) {
        put("data", data);
        return this;
    }
}