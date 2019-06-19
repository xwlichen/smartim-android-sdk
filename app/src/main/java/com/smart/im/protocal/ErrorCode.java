package com.smart.im.protocal;

/**
 * @date : 2019-06-19 11:58
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface ErrorCode {

    /**
     * 未登陆错误
     */
    int ERROR_LOGIN_NO=1;

    /**
     * 登陆验证失败
     */
    int ERROR_LOGIN_FAILURE=2;

    /**
     * 发送消息失败
     */
    int ERROR_SEND_DATA_FAILURE=3;


    /**
     * 非法协议
     */
    int ERROR_INVAILD_PROTOCAL=4;

    /**
     * 没有网络
     */
    int ERROR_NO_NETWORK=5;


    /**
     *
     */
    int ERROR_UNKNOW=6;

}
