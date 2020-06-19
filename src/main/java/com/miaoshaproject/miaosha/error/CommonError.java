package com.miaoshaproject.miaosha.error;

public interface CommonError {

    int getErrorCode();
    String getErrorMessage();
    CommonError setErrorMessage(String errorMsg);
}
