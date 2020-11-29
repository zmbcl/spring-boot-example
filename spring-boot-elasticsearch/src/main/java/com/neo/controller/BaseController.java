package com.neo.controller;

import com.neo.response.ResponseResult;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 12:25 下午 2020/11/29
 */
public class BaseController {
    protected ResponseResult success() {
        return new ResponseResult();
    }

    protected<T> ResponseResult success(T t){
        return new ResponseResult(t);
    }
}
