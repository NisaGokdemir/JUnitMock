package com.gokdemir.unitmockdemo.controller.impl;

import com.gokdemir.unitmockdemo.controller.IRestPaymentController;
import com.gokdemir.unitmockdemo.dto.DtoPayment;
import com.gokdemir.unitmockdemo.dto.DtoPaymentIU;
import com.gokdemir.unitmockdemo.service.IPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gokdemir.unitmockdemo.config.RestApis.*;

import java.util.List;

@RequestMapping( PAYMENT)
@RestController
@RequiredArgsConstructor
public class RestPaymentControllerImpl implements IRestPaymentController {

    private final IPaymentService paymentService;

    @PostMapping(CREATE_PAYMENT)
    @Override
    public ResponseEntity<DtoPayment> savePayment(@Valid @RequestBody DtoPaymentIU dtoPaymentIU) {
        return ResponseEntity.ok(paymentService.savePayment(dtoPaymentIU));
    }

    @PutMapping(UPDATE_PAYMENT)
    @Override
    public ResponseEntity<DtoPayment> updatePayment(@PathVariable(name = "id") Long paymentId, @Valid @RequestBody DtoPaymentIU dtoPaymentIU) {
        return ResponseEntity.ok(paymentService.updatePayment(paymentId, dtoPaymentIU));
    }

    @DeleteMapping(DELETE_PAYMENT)
    @Override
    public ResponseEntity<Long> deletePayment(@PathVariable(name = "id") Long paymentId) {
        return ResponseEntity.ok(paymentService.deletePayment(paymentId));
    }

    @GetMapping(LIST_PAYMENT)
    @Override
    public ResponseEntity<DtoPayment> findPaymentById(@PathVariable(name = "id") Long paymentId) {
        return ResponseEntity.ok(paymentService.findPaymentById(paymentId));
    }

    @GetMapping(LIST_PAYMENT_ALL)
    @Override
    public ResponseEntity<List<DtoPayment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping(LIST_PAYMENT_PAGEABLE)
    @Override
    public ResponseEntity<Page<DtoPayment>> getPageableResponse(Pageable pageable) {
        Page<DtoPayment> page = paymentService.getPageableResponse(pageable);
        return ResponseEntity.ok(page);
    }


}
