package com.gokdemir.unitmockdemo.service.impl;

import com.gokdemir.unitmockdemo.dto.DtoPayment;
import com.gokdemir.unitmockdemo.dto.DtoPaymentIU;
import com.gokdemir.unitmockdemo.exception.payment.PaymentDomainException;
import com.gokdemir.unitmockdemo.exception.payment.PaymentErrorCode;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodDomainException;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodErrorCode;
import com.gokdemir.unitmockdemo.mapper.PaymentMapper;
import com.gokdemir.unitmockdemo.model.Payment;
import com.gokdemir.unitmockdemo.model.PaymentMethod;
import com.gokdemir.unitmockdemo.repository.PaymentMethodRepository;
import com.gokdemir.unitmockdemo.repository.PaymentRepository;
import com.gokdemir.unitmockdemo.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMapper paymentMapper;

    private Payment createPayment(DtoPaymentIU dtoPaymentIU) {
        return paymentMapper.dtoPaymentIUToEntity(dtoPaymentIU);
    }

    private PaymentMethod findPaymentMethodById(Long paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND));
                //.orElseThrow(() -> new BaseDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND));
    }

    private Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentDomainException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }


    @Override
    public DtoPayment savePayment(DtoPaymentIU dtoPaymentIU) {
        Payment payment = createPayment(dtoPaymentIU);
        payment.setPaymentMethod(findPaymentMethodById(dtoPaymentIU.getPaymentMethodId()));
        paymentRepository.save(payment);
        DtoPayment result = paymentMapper.entityToDtoPayment(payment);
        if (result == null) {
            throw new PaymentDomainException(PaymentErrorCode.PAYMENT_SAVE_FAILED);
        }
        return result;
    }

    @Override
    public DtoPayment updatePayment(Long paymentId, DtoPaymentIU dtoPaymentIU) {
        Payment payment = getPaymentById(paymentId);
        paymentMapper.updatePaymentFromDto(dtoPaymentIU, payment);
        if (payment.getAmount() == null || payment.getAmount() <= 0 || payment.getPaymentMethod().getId() == null) {
            throw new PaymentDomainException(PaymentErrorCode.PAYMENT_AMOUNT_INVALID);
        }
        payment.setPaymentMethod(findPaymentMethodById(dtoPaymentIU.getPaymentMethodId()));
        paymentRepository.save(payment);
        return paymentMapper.entityToDtoPayment(payment);
    }

    @Override
    public Long deletePayment(Long paymentId) {
        paymentRepository.delete(getPaymentById(paymentId));
        return paymentId;
    }

    @Override
    public DtoPayment findPaymentById(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        return paymentMapper.entityToDtoPayment(payment);
    }

    @Override
    public List<DtoPayment> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) {
            throw new PaymentDomainException(PaymentErrorCode.PAYMENT_NOT_FOUND);
        }
        List<DtoPayment> result = paymentMapper.entitiesToDtoPayments(payments);
        if (result == null || result.isEmpty()) {
            throw new PaymentDomainException(PaymentErrorCode.PAYMENT_NOT_FOUND);
        }
        return result;
    }

    @Override
    public Page<DtoPayment> getPageableResponse(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAllPageable(pageable);
        if (payments.isEmpty()) {
            throw new PaymentDomainException(PaymentErrorCode.PAYMENT_NOT_FOUND);
        }
        return payments.map(paymentMapper::entityToDtoPayment);
    }
}
