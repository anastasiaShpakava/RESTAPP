package com.leverX.blog.controller;

import com.leverX.blog.security.JwtFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private ArticleController articleController;
    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) //Чтобы использовать Spring Security
                .apply(springSecurity())
                .build();
    }

    @Test
    public void test() throws Exception {
        this.mockMvc.perform(get("/articles")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    @WithAnonymousUser
    public void getUserArticles() throws Exception {
        mockMvc.perform(get("/{login}/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("Test")
    public void getAuthUserArticles() throws Exception {
        mockMvc.perform(get("/{login}/articles"))
                .andExpect(status().isOk());
    }
}
