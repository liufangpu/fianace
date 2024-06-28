package com.example.demo.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.finance.dto.AssetDTO;
import com.example.demo.finance.exception.ServiceException;
import com.example.demo.finance.mapper.AssetMapper;
import com.example.demo.finance.mapper.PersonMapper;
import com.example.demo.finance.model.Asset;
import com.example.demo.finance.model.Person;
import com.example.demo.finance.service.AssetServiceI;
import com.example.demo.finance.support.converter.AssetConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AssetServiceImpl implements AssetServiceI {


    private final AssetMapper assetMapper;

    private final PersonMapper personMapper;

    public AssetServiceImpl(AssetMapper assetMapper, PersonMapper personMapper) {
        this.assetMapper = assetMapper;
        this.personMapper = personMapper;
    }

    @Override
    public List<AssetDTO> getByAccount(String account) {
        Person person = personMapper.selectOne(new QueryWrapper<Person>().lambda().eq(Person::getAccount, account));

        if (Objects.isNull(person)) {
            throw new ServiceException("找不到用户");
        }
        List<Asset> assets = assetMapper.selectList(new QueryWrapper<Asset>().lambda().eq(Asset::getPersonId, person.getId()));
        if (CollectionUtil.isEmpty(assets)){
            return Collections.emptyList();
        }
        return assets.stream().map(AssetConverter::toDTO).toList();
    }



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
