package com.ppbkaf.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlcoholDTO {
    private int id;
    @NotBlank
    private String name;
    @NotNull
    private BrandDTO brand;
    @NotNull
    private KindDTO kind;
    @PositiveOrZero
    private double alcoholicStrength;
    @Positive
    private int volume;
}
