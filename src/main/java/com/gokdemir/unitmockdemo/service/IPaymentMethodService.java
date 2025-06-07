package com.gokdemir.unitmockdemo.service;

import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaymentMethodService {

    public DtoPaymentMethod savePaymentMethod(DtoPaymentMethodIU dtoPaymentMethodIU);

    public DtoPaymentMethod updatePaymentMethod(Long paymentMethodId,DtoPaymentMethodIU dtoPaymentMethodIU);

    public Long deletePaymentMethod(Long paymentMethodId);

    public DtoPaymentMethod findPaymentMethodById(Long paymentMethodId);

    public List<DtoPaymentMethod> getAllPaymentMethods();

    public Page<DtoPaymentMethod> getPageableResponse(Pageable pageable);

}
