/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:14
 * @Description:
 */
package com.yuan.springbootjunit.controller;

import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;



    @RequestMapping("/getPerson/{id:\\d+}")
    public Person getPserson(@PathVariable int id){
        System.out.print("=================================================================================");
        Person person = personService.getPerson(id);
        return person;
    }

    @PostMapping("/update")
    public void updatePerson(Person person){
        personService.update(person);
    }
}
