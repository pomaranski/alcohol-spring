package com.ppbkaf.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    @PositiveOrZero
    private int positiveRates;
    @PositiveOrZero
    private int negativeRates;
}
