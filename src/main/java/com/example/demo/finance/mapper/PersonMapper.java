package com.example.demo.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.finance.model.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    // 可以添加自定义方法
}
