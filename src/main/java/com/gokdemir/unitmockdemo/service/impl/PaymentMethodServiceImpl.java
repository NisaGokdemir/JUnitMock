package com.gokdemir.unitmockdemo.service.impl;

import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodDomainException;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodErrorCode;
import com.gokdemir.unitmockdemo.mapper.PaymentMethodMapper;
import com.gokdemir.unitmockdemo.model.PaymentMethod;
import com.gokdemir.unitmockdemo.repository.PaymentMethodRepository;
import com.gokdemir.unitmockdemo.service.IPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements IPaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodMapper paymentMethodMapper;

    private PaymentMethod getPaymentMethodById(Long paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND));
    }

    private PaymentMethod createPaymentMethod(DtoPaymentMethodIU dtoPaymentMethodIU) {
        return paymentMethodMapper.dtoPaymentIUToEntity(dtoPaymentMethodIU);
    }

    @Override
    public DtoPaymentMethod savePaymentMethod(DtoPaymentMethodIU dtoPaymentMethodIU) {
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(createPaymentMethod(dtoPaymentMethodIU));
        DtoPaymentMethod result = paymentMethodMapper.entityToDtoPayment(savedPaymentMethod);
        if( result == null) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_SAVE_FAILED);
        }
        return result;
    }

    @Override
    public DtoPaymentMethod updatePaymentMethod(Long paymentMethodId, DtoPaymentMethodIU dtoPaymentMethodIU) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
        paymentMethodMapper.DtoPaymentMethodIUToEntity(dtoPaymentMethodIU, paymentMethod);
        if (paymentMethod.getName() == null || paymentMethod.getName().isBlank()
                || paymentMethod.getDescription() == null || paymentMethod.getDescription().isBlank()) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.entityToDtoPayment(updatedPaymentMethod);
    }

    @Override
    public Long deletePaymentMethod(Long paymentMethodId) {
        paymentMethodRepository.delete(getPaymentMethodById(paymentMethodId));
        return paymentMethodId;
    }

    @Override
    public DtoPaymentMethod findPaymentMethodById(Long paymentMethodId) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
        return paymentMethodMapper.entityToDtoPayment(paymentMethod);
    }

    @Override
    public List<DtoPaymentMethod> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        if (paymentMethods.isEmpty()) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }

        List<DtoPaymentMethod> result = paymentMethodMapper.entitiesToDtoPaymentMethods(paymentMethods);
        if (result == null || result.isEmpty()) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }

        return result;
    }

    @Override
    public Page<DtoPaymentMethod> getPageableResponse(Pageable pageable) {
        Page<PaymentMethod>  paymentMethods = paymentMethodRepository.findAll(pageable);
        if (paymentMethods.isEmpty()) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }
        if (pageable.getPageSize() < 0 || pageable.getPageNumber() < 0) {
            throw new PaymentMethodDomainException(PaymentMethodErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }
        return paymentMethods.map(paymentMethodMapper::entityToDtoPayment);
    }
}
