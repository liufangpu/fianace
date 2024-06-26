package com.example.demo.finance.service;

import java.math.BigDecimal;
import java.util.List;

public interface AssetServiceI {

    List<String> getAllSymbols();

    BigDecimal getAssetPrice(String symbol);

    void updateAssetPrice(String s, BigDecimal bigDecimal);
}
