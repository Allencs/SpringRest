package com.allen.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 统一返回对象
 */
public class CommonResponse<T> {
    @ApiModelProperty(value = "响应状态码；200:操作成功 | 500:操作失败 | 404:参数检验失败 | 401:暂未登录或token已经过期 |" +
            " 600:请求头中的token为空 | 601:远程获取TokenKey异常 | 603:token校验异常 | 429:服务触发流控 | 604:服务触发熔断降级" +
            " | 502:网关服务异常 | 403:没有相关权限")
    private long code;
    @ApiModelProperty(value = "操作成功 | 操作失败")
    private String message;
    @ApiModelProperty(value = "详细结果数据")
    public T data;

    protected CommonResponse() {
    }

    protected CommonResponse(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResponse<T> failed(IErrorCode errorCode) {
        return new CommonResponse<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }


    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResponse<T> failed(String message) {
        return new CommonResponse<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResponse<T> failed(long code, String message) {
        return new CommonResponse<T>(code, message, null);
    }

    public static <T> CommonResponse<T> failed(T data, String message) {
        return new CommonResponse<T>(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResponse<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResponse<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResponse<T> validateFailed(String message) {
        return new CommonResponse<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResponse<T> unauthorized(T data) {
        return new CommonResponse<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResponse<T> forbidden(T data) {
        return new CommonResponse<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
