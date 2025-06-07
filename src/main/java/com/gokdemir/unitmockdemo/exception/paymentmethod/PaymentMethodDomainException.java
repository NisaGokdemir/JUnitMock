package com.gokdemir.unitmockdemo.exception.paymentmethod;

import com.gokdemir.unitmockdemo.exception.base.BaseDomainException;

public class PaymentMethodDomainException extends BaseDomainException {
    public PaymentMethodDomainException(PaymentMethodErrorCode errorCode) {
        super(errorCode);
    }
}
