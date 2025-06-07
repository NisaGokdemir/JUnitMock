package com.gokdemir.unitmockdemo.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPayment{

    private Long id;

    private Float amount;

    private Date paymentDate;

    private DtoPaymentMethod paymentMethod;
}
