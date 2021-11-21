package com.yuki.experiment.common.interceptor;

import com.yuki.experiment.common.result.IErrorCode;

public enum TokenResultCode implements IErrorCode {
    TOKEN_ALGORITHM_MISMATCH(401,"token算法不匹配"),
    TOKEN_IS_NULL(401,"token为空"),
    TOKEN_SIGNATURE_INVALIDATE(401,"token签名无效"),
    TOKEN_INVALIDATE(401,"token无效"),
    TOKEN_EXPIRED(401,"token过期");


    private final long code;

    private final String message;

    TokenResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
