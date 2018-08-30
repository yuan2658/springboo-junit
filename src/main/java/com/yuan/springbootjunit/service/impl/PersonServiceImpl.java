/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:18
 * @Description:
 */
package com.yuan.springbootjunit.service.impl;

import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {


    @Override
    public Person getPerson(int id) {
        Person person = new Person(id,"zhangsan");
        return person;
    }

    @Override
    public boolean update(Person person) {
       if(person.getId()==1 && person.getName().equals("zhangsan")){
           return true;
       }
        return false;
    }
}
