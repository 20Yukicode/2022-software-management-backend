package com.campus.love.common.exception;

import com.campus.love.common.api.IErrorCode;

public class ApiException extends RuntimeException{

    private IErrorCode error;

    public IErrorCode getError(){
        return this.error;
    }

    public ApiException(String message){
        super(message);
    }

    public ApiException(IErrorCode code){
        super(code.getMessage());
        this.error=code;
    }


}
