/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:28
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import com.yuan.springbootjunit.SpringbootJunitApplication;
import com.yuan.springbootjunit.controller.PersonController;
import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.impl.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = SpringbootJunitApplication.class)
@WebAppConfiguration
public class PersonControllerTest {


    private MockMvc mockMvc;


    @Spy
    PersonServiceImpl personService;

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
        int id = 1;
        mockMvc.perform(get("/person/getPerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("message").value("查询成功"));
    }

    @Test
    public void savePersonTest() throws Exception {
        mockMvc.perform(post("/person/savePerson/").
                contentType(TestUtil.APPLICATION_JSON_UTF8).
        content(TestUtil.convertObjectToJsonBytes(new Person(1,"zhangsan")))
        )
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success",is(true)));
    }

    @Test
    public void deletePersonTest() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/person/deletePerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                ;
    }

    @Test
    public void updatePersonTest() throws Exception {
        mockMvc.perform(
                put("/person/updatePerson")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(new Person(1,"zhangsan"))))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("success", is(true)));
    }





}
