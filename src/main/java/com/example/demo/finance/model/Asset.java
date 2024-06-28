package com.example.demo.finance.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

@TableName("asset")
@Data
public class Asset {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("symbol")
    private String symbol;

    @TableField("currency")
    private String currency;

    @TableField("amount")
    private Integer amount;

    @TableField("last_price")
    private BigDecimal lastPrice;

    @TableField("person_id")
    private Long personId;

}
