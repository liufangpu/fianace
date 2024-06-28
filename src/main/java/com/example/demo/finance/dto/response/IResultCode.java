package com.example.demo.finance.dto.response;

/**
 * 结果状态码接口
 */
public interface IResultCode {

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getMessage();

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();
}
