package com.example.demo.finance.service;

import com.example.demo.finance.dto.AssetDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AssetServiceI {

    List<String> getAllSymbols();

    BigDecimal getAssetPrice(String symbol);

    void updateAssetPrice(String s, BigDecimal bigDecimal);

    List<AssetDTO> getByAccount(String account);

}
