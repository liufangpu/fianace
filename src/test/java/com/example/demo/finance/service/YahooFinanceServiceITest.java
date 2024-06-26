package com.example.demo.finance.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class YahooFinanceServiceITest {

    @Autowired
    private YahooFinanceServiceI yahooFinanceService;
    @Test
    void fetchCurrentPrice() {
        BigDecimal bigDecimal = yahooFinanceService.fetchCurrentPrice("2880.TW");
        System.out.println(bigDecimal);
    }
}