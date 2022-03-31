package com.campus.love.common.core.exception;

import com.campus.love.common.core.api.IErrorCode;

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
