package com.example.demo.finance.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("person")
public class Person {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("account")
    private String account;

    @TableField("name")
    private String name;



}
