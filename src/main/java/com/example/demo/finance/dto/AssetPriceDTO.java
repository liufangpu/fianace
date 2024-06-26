package com.example.demo.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class AssetPriceDTO {

    private String symbol;

    public BigDecimal price;
}
