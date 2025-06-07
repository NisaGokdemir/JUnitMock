package com.gokdemir.unitmockdemo.service;

import com.gokdemir.unitmockdemo.dto.DtoPayment;
import com.gokdemir.unitmockdemo.dto.DtoPaymentIU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaymentService {

    public DtoPayment savePayment(DtoPaymentIU dtoPaymentIU);

    public DtoPayment updatePayment(Long paymentId, DtoPaymentIU dtoPaymentIU);

    public Long deletePayment(Long paymentId);

    public DtoPayment findPaymentById(Long paymentId);

    public List<DtoPayment> getAllPayments();

    public Page<DtoPayment> getPageableResponse(Pageable pageable);
}
