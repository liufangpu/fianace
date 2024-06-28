package com.example.demo.finance.schedule;

import cn.hutool.json.JSONUtil;
import com.example.demo.finance.dto.AssetPriceDTO;
import com.example.demo.finance.service.AssetServiceI;
import com.example.demo.finance.service.YahooFinanceServiceI;
import com.example.demo.finance.websocket.AssetWebSocketHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
public class AssetTask {


    @Autowired
    private YahooFinanceServiceI yahooFinanceService;

    @Resource
    private AssetWebSocketHandler assetWebSocketHandler;



    @Autowired
    private AssetServiceI assetService;

    @Scheduled(initialDelay = 30000, fixedRate = 10000)  // 每10s检查一次
    public void updateAssetPrices() {
        //检查数据里面所有的symbol,然后去yahoo查询，查询完更新数据库和缓存，
        List<String> allSymbols = assetService.getAllSymbols();
        allSymbols.forEach(s -> {
            BigDecimal latestPrice = yahooFinanceService.fetchCurrentPrice(s);
            BigDecimal assetPrice = assetService.getAssetPrice(s);
            if (latestPrice.compareTo(assetPrice) != 0) {
                log.info("获取缓存中{}最新价格：{}",s,assetPrice);
                assetService.updateAssetPrice(s, latestPrice);
                log.info("拉取yahoo实时价格更新了{}的价格为：{}",s,latestPrice);
                assetWebSocketHandler.broadcast(new AssetPriceDTO(s,latestPrice));
            }
        });
    }

}
