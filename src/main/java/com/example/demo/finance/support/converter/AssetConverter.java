package com.example.demo.finance.support.converter;

import com.example.demo.finance.dto.AssetDTO;
import com.example.demo.finance.model.Asset;

import java.util.Objects;

public class AssetConverter {


    public static AssetDTO toDTO(Asset asset) {
        if (Objects.isNull(asset)){
            return null;
        }
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(asset.getId());
        assetDTO.setSymbol(asset.getSymbol());
        assetDTO.setCurrency(asset.getCurrency());
        assetDTO.setAmount(asset.getAmount());
        assetDTO.setLastPrice(asset.getLastPrice());
        return assetDTO;
    }

}
