package com.ppbkaf.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private int id;
    @NotNull
    private AlcoholDTO alcohol;
    @NotNull
    private ShopDTO shop;
    @Positive
    private double price;
    @NotNull
    private RateDTO rate;
}
