package com.gokdemir.unitmockdemo.controller.impl;


import com.gokdemir.unitmockdemo.controller.IRestPaymentMethodController;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import com.gokdemir.unitmockdemo.service.IPaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gokdemir.unitmockdemo.config.RestApis.*;

import java.util.List;

@RequestMapping(PAYMENT_METHOD)
@RestController
@RequiredArgsConstructor
public class RestPaymentMethodControllerImpl implements IRestPaymentMethodController {

    private final IPaymentMethodService paymentMethodService;

    @PostMapping(CREATE_PAYMENT_METHOD)
    @Override
    public ResponseEntity<DtoPaymentMethod> savePaymentMethod(@Valid @RequestBody DtoPaymentMethodIU dtoPaymentMethodIU) {
        return ResponseEntity.ok(paymentMethodService.savePaymentMethod(dtoPaymentMethodIU));
    }

    @PutMapping(UPDATE_PAYMENT_METHOD)
    @Override
    public ResponseEntity<DtoPaymentMethod> updatePaymentMethod(@PathVariable(name = "id") Long paymentMethodId,@Valid @RequestBody DtoPaymentMethodIU dtoPaymentMethodIU) {
        return ResponseEntity.ok(paymentMethodService.updatePaymentMethod(paymentMethodId, dtoPaymentMethodIU));
    }

    @DeleteMapping(DELETE_PAYMENT_METHOD)
    @Override
    public ResponseEntity<Long> deletePaymentMethod(@PathVariable(name = "id") Long paymentMethodId) {
        return ResponseEntity.ok(paymentMethodService.deletePaymentMethod(paymentMethodId));
    }

    @GetMapping(LIST_PAYMENT_METHOD)
    @Override
    public ResponseEntity<DtoPaymentMethod> findPaymentMethodById(@PathVariable(name = "id") Long paymentMethodId) {
        return ResponseEntity.ok(paymentMethodService.findPaymentMethodById(paymentMethodId));
    }

    @GetMapping(LIST_PAYMENT_METHOD_ALL)
    @Override
    public ResponseEntity<List<DtoPaymentMethod>> getAllPaymentMethods() {
        return ResponseEntity.ok(paymentMethodService.getAllPaymentMethods());
    }

    @GetMapping(LIST_PAYMENT_METHOD_PAGEABLE)
    @Override
    public ResponseEntity<Page<DtoPaymentMethod>> getPageableResponse(Pageable pageable) {
        return ResponseEntity.ok(paymentMethodService.getPageableResponse(pageable));
    }
}
