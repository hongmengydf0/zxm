package zxm.common.result;

/**
 * 响应结果生成工具(单例)
 */
public enum ResultFactory {
    INSTANCE;
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    /**
     * 成功，无返回数据
     *
     * @return
     */
    public Result success() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 成功,返回数据
     *
     * @param data
     * @return
     */
    public Result success(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 失败
     *
     * @param msg
     * @return
     */
    public Result error(String msg) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMsg(msg);
    }

    /**
     * 自定义,无数据
     * @param resultCode
     * @param msg
     * @return
     */
    public Result result(ResultCode resultCode, String msg) {
        return new Result()
                .setCode(resultCode)
                .setMsg(msg);
    }

    /**
     * 自定义,无数据
     * @param code
     * @param msg
     * @return
     */
    public Result result(int code, String msg) {
        return new Result()
                .setCode(code)
                .setMsg(msg);
    }

    /**
     * 自定义,有数据
     * @param resultCode
     * @param msg
     * @param data
     * @return
     */
    public Result result(ResultCode resultCode, String msg, Object data) {
        return new Result()
                .setCode(resultCode)
                .setMsg(msg)
                .setData(data);
    }

    /**
     * 自定义,有数据
     * @param resultCode
     * @param msg
     * @param data
     * @return
     */
    public Result result(int resultCode, String msg, Object data) {
        return new Result()
                .setCode(resultCode)
                .setMsg(msg)
                .setData(data);
    }

}