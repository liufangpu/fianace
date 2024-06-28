package com.example.demo.finance.exception;

import com.example.demo.finance.dto.response.IResultCode;
import com.example.demo.finance.dto.response.ResultCode;
import lombok.Getter;

public class ServiceException extends RuntimeException {


    @Getter
    private final IResultCode resultCode;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }
}