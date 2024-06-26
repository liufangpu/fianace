package com.example.demo.finance.service.impl;

import com.example.demo.finance.mapper.PersonMapper;
import com.example.demo.finance.model.Person;
import com.example.demo.finance.service.PersonServiceI;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonServiceI {
    @Autowired
    private PersonMapper personMapper;

    public List<Person> findAll() {
        return personMapper.selectList(null);
    }

    public Person findById(Long id) {
        return personMapper.selectById(id);
    }

    public void save(Person person) {
        if (person.getId() == null) {
            personMapper.insert(person);
        } else {
            personMapper.updateById(person);
        }
    }

    public void deleteById(Long id) {
        personMapper.deleteById(id);
    }
}
