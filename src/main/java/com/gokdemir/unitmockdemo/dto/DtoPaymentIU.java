package com.gokdemir.unitmockdemo.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPaymentIU {

    @NotNull
    private Float amount;

    @NotNull
    private Date paymentDate;

    @NotNull
    private Long paymentMethodId;
}
