package com.example.demo.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.finance.model.Asset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetMapper extends BaseMapper<Asset> {
    // 可以添加自定义方法
    List<String> selectAllSymbols();

}
