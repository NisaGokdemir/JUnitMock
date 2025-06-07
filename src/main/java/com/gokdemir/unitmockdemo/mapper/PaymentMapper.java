package com.gokdemir.unitmockdemo.mapper;

import java.util.List;

import com.gokdemir.unitmockdemo.dto.DtoPayment;
import com.gokdemir.unitmockdemo.dto.DtoPaymentIU;
import com.gokdemir.unitmockdemo.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PaymentMethodMapper.class)
public interface PaymentMapper {

    DtoPayment entityToDtoPayment(Payment payment);

    Payment dtoPaymentIUToEntity(DtoPaymentIU dtoPaymentIU);

    List<DtoPayment> entitiesToDtoPayments(List<Payment> payments);

    void updatePaymentFromDto(DtoPaymentIU dtoPaymentIU, @MappingTarget Payment payment);
}
