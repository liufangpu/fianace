package com.example.demo.finance.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AssetDTO {


    private Long id;


    private String symbol;



    private String currency;

    private Integer amount;

    private BigDecimal lastPrice;
}
