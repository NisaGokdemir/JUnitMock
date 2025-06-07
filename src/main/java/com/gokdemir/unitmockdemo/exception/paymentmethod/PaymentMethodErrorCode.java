package com.gokdemir.unitmockdemo.exception.paymentmethod;

import com.gokdemir.unitmockdemo.exception.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PaymentMethodErrorCode implements BaseErrorCode{
    PAYMENT_METHOD_NOT_FOUND("PAYMENT_METHOD-404", "Payment method not found", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    PaymentMethodErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}
