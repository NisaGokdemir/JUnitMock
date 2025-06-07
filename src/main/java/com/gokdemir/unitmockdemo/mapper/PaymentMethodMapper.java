package com.gokdemir.unitmockdemo.mapper;

import java.util.List;

import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import com.gokdemir.unitmockdemo.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    DtoPaymentMethod entityToDtoPayment(PaymentMethod payment);

    PaymentMethod dtoPaymentIUToEntity(DtoPaymentMethodIU dtoPaymentMethodIU);

    List<DtoPaymentMethod> entitiesToDtoPaymentMethods(List<PaymentMethod> paymentMethods);

    void DtoPaymentMethodIUToEntity(DtoPaymentMethodIU dtoPaymentMethodIU, @MappingTarget PaymentMethod paymentMethod);
}
