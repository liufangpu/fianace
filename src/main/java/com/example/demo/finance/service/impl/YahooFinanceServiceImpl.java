package com.example.demo.finance.service.impl;

import com.example.demo.finance.model.YahooResponse;
import com.example.demo.finance.service.YahooFinanceServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Slf4j
public class YahooFinanceServiceImpl implements YahooFinanceServiceI {

    private final RestTemplate restTemplate;

    @Autowired
    public YahooFinanceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal fetchCurrentPrice(String symbol) {
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol;
        try {
            // 调用Yahoo Finance API
            YahooResponse response = restTemplate.getForObject(url, YahooResponse.class);
            log.info("Yahoo Finance Response : {}", response);
            // 解析响应以获取当前价格
            BigDecimal price = extractPriceFromResponse(response);
            return price;
        } catch (Exception e) {
            // 处理错误情况
            log.error(e.getMessage(),e);
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal extractPriceFromResponse(YahooResponse response) {

        assert response.getChart().getResult().size()==1;

        YahooResponse.Result result = response.getChart().getResult().get(0);
        double regularMarketPrice = result.getMeta().getRegularMarketPrice();

        return new BigDecimal(regularMarketPrice);
    }

}
