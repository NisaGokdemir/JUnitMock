package com.gokdemir.unitmockdemo.controller;

import com.gokdemir.unitmockdemo.dto.DtoPayment;
import com.gokdemir.unitmockdemo.dto.DtoPaymentIU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRestPaymentController {
    public ResponseEntity<DtoPayment> savePayment(DtoPaymentIU dtoPaymentIU);

    public ResponseEntity<DtoPayment> updatePayment(Long paymentId, DtoPaymentIU dtoPaymentIU);

    public ResponseEntity<Long> deletePayment(Long paymentId);

    public ResponseEntity<DtoPayment> findPaymentById(Long paymentId);

    public ResponseEntity<List<DtoPayment>> getAllPayments();

    public ResponseEntity<Page<DtoPayment>> getPageableResponse(Pageable pageable);
}
