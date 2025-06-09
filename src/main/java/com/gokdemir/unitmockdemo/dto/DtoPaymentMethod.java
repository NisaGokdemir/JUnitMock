package com.gokdemir.unitmockdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoPaymentMethod{

    private Long id;

    private String name;

    private String description;
}
