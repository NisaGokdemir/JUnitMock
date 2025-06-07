package com.gokdemir.unitmockdemo.exception.payment;

import com.gokdemir.unitmockdemo.exception.base.BaseDomainException;

public class PaymentDomainException extends BaseDomainException {
    public PaymentDomainException(PaymentErrorCode errorCode) {
        super(errorCode);
    }
}

