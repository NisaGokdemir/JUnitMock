package com.gokdemir.unitmockdemo.controller;

import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRestPaymentMethodController {
    public ResponseEntity<DtoPaymentMethod> savePaymentMethod(DtoPaymentMethodIU dtoPaymentMethodIU);

    public ResponseEntity<DtoPaymentMethod> updatePaymentMethod(Long paymentMethodId,DtoPaymentMethodIU dtoPaymentMethodIU);

    public ResponseEntity<Long> deletePaymentMethod(Long paymentMethodId);

    public ResponseEntity<DtoPaymentMethod> findPaymentMethodById(Long paymentMethodId);

    public ResponseEntity<List<DtoPaymentMethod>> getAllPaymentMethods();

    public ResponseEntity<Page<DtoPaymentMethod>> getPageableResponse(Pageable pageable);
}
