package com.gokdemir.unitmockdemo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoPaymentMethodIU {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
