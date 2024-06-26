package com.example.demo.finance;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.demo.finance.mapper.PersonMapper;
import com.example.demo.finance.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisPlusTest
class MybatisPlusSampleTest {

    @Autowired
    private PersonMapper sampleMapper;

    @Test
    void testInsert() {
        Person person = new Person();
        person.setAccount("testAccount");
        person.setName("testName");
        sampleMapper.insert(person);
        assertThat(person.getId()).isNotNull();
    }
}