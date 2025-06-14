package com.gokdemir.unitmockdemo.exception.payment;

import com.gokdemir.unitmockdemo.exception.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PaymentErrorCode implements BaseErrorCode {
    PAYMENT_NOT_FOUND("PAYMENT-404", "Payment not found", HttpStatus.NOT_FOUND),
    PAYMENT_SAVE_FAILED("PAYMENT_SAVE_FAILED","Payment save fail" ,HttpStatus.BAD_REQUEST),
    PAYMENT_AMOUNT_INVALID("PAYMENT_AMOUNT_INVALID","Payment amount invalid" ,HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    PaymentErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}

