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
        return paymentMethodMapper.entityToDtoPayment(savedPaymentMethod);
    }

    @Override
    public DtoPaymentMethod updatePaymentMethod(Long paymentMethodId, DtoPaymentMethodIU dtoPaymentMethodIU) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
        paymentMethodMapper.DtoPaymentMethodIUToEntity(dtoPaymentMethodIU, paymentMethod);
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
        return paymentMethodMapper.entitiesToDtoPaymentMethods(paymentMethods);
    }

    @Override
    public Page<DtoPaymentMethod> getPageableResponse(Pageable pageable) {
        Page<PaymentMethod>  paymentMethods = paymentMethodRepository.findAll(pageable);
        return paymentMethods.map(paymentMethodMapper::entityToDtoPayment);
    }
}
