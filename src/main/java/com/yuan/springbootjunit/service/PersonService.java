package com.yuan.springbootjunit.service;

import com.yuan.springbootjunit.entity.Person;

public interface PersonService {

    Person getPerson(int id);

    boolean update(Person person);
}
