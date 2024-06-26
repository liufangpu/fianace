package com.example.demo.finance.service;

import java.math.BigDecimal;

public interface YahooFinanceServiceI {

    BigDecimal fetchCurrentPrice(String symbol);
}
