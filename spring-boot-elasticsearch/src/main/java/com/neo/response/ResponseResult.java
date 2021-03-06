package com.neo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysql.jdbc.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 请求成功返回码为：0-成功
     */
    public static final Integer SUCCESS_CODE = 0;

    /**
     * 请求失败返回码为：-1失败
     */
    public static final Integer ERR_CODE = -1;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回描述
     */
    private String msg;

    private boolean status = true;

    public ResponseResult() {
        this.code = SUCCESS_CODE;
        this.msg = "REQUEST SUCCESS";
    }

    public static ResponseResult err() {
        return new ResponseResult(ERR_CODE, "REQUEST ERROR");
    }

    public static ResponseResult err(String message) {
        return new ResponseResult(ERR_CODE, message);
    }

    public ResponseResult(boolean status, int code) {
        this();
        this.status = status;
        this.code = code;
    }

    public ResponseResult(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(boolean status, int code, String msg) {
        this();
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, T data) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(boolean status, int code, String msg, T data) {
        this();
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(T data) {
        this();
        this.data = data;
    }

}
