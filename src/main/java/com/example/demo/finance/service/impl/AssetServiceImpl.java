package com.example.demo.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.finance.mapper.AssetMapper;
import com.example.demo.finance.model.Asset;
import com.example.demo.finance.service.AssetServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetServiceI {

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public List<String> getAllSymbols() {
        //todo 根据资产情况合理的设置缓存，避免一次性加载大量的数据
        return assetMapper.selectAllSymbols();
    }



    @Override
    @CacheEvict(value = "AssetPrice", key = "#symbol")
    public void updateAssetPrice(String symbol, BigDecimal newPrice) {
        assetMapper.update(new UpdateWrapper<Asset>().lambda()
                .set(Asset::getLastPrice, newPrice)
                .eq(Asset::getSymbol,symbol));
    }

    @Override
    @Cacheable(value = "AssetPrice", key = "#symbol")
    public BigDecimal getAssetPrice(String symbol) {
        Asset asset = assetMapper.selectOne(new QueryWrapper<Asset>().lambda().eq(Asset::getSymbol, symbol).last("limit 1"));
        return asset.getLastPrice();
    }
}
