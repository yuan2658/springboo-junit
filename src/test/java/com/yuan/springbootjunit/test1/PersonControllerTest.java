/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:28
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import com.yuan.springbootjunit.controller.PersonController;
import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PersonControllerTest extends BaseJunitTest{

    ///**
    // * Service 单元测试
    // */
    //@Autowired
    //PersonService personService;
    //@Test
    //public void test(){
    //    Person person = personService.getPerson(10);
    //    assertEquals(15,person.getId());
    //}


    /**
     * 验证行为是否发生
     */
    @Test
    public void verify_behaviour(){
        //模拟创建一个List对象
        List mock = mock(List.class);
        //使用mock的对象
        mock.add(1);
        mock.clear();
        //验证add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }



    /**
     * 模拟期望结果
     */
    @Test
    public void when_thenReturn(){
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        assertEquals("hello world world",result);
    }



    private MockMvc mockMvc;


    @Mock
    PersonService personService;

    @InjectMocks
    PersonController personController;

    @Before
    public void setUp() throws Exception {
        // 初始化Mock
        MockitoAnnotations.initMocks(this);
        // 构造appcontext
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    /**
     * Controller 测试
     * MockMvc可以对controller中的一次调用进行模拟，perform就是一次请求，MockMvcRequestBuilders进行url的请求，
     * andExcept方法为对Controller类、调用方法、视图和model的预期设置，andDo进行这次请求的执行，最后andReturn返回。
     * Mockito通过方法when()、thenReturn()等方法可以对方法进行打桩，
     * 让后续方法按照自己的数据桩来返回，达到了隔离依赖的效果。
     */
    @Test
    public void getPersonTest() throws Exception {

        when(personService.getPerson(1)).thenReturn(new Person(1,"zhangsan"));

        // 1. controller mvc test
         mockMvc.perform(get("/person/getPerson/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("zhangsan"));

       // verify(personService).getPerson(1);

         //2.service stub test
        Person stub = new Person(1,"zhangsan");
        Mockito.when(personService.getPerson(3)).thenReturn(stub);
        Assert.assertEquals(stub, personService.getPerson(3));
        Mockito.verify(personService).getPerson(3);
    }




}
