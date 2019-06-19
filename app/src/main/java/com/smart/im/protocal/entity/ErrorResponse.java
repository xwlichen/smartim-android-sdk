package com.smart.im.protocal.entity;

/**
 * @date : 2019-06-19 14:27
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ErrorResponse {

    private int code;

    private String message;


    public ErrorResponse(){

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
