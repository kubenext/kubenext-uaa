package com.github.kubenext.uaa.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kubenext.uaa.service.dto.CreateUserDTO;
import com.github.kubenext.uaa.utils.RandomUtil;
import io.micrometer.core.instrument.util.JsonUtils;
import net.minidev.json.JSONUtil;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author shangjin.li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserResourceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserResourceTest.class);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    public void testCreateUserSuccess() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setAuthorities(new String[]{"ROLE_USER"});
        createUserDTO.setLogin("test"+RandomUtil.generateActivationKey());
        createUserDTO.setAvatar("/test.jpg");
        createUserDTO.setEmail("test"+RandomUtil.generateActivationKey()+"@kubenext.com");
        createUserDTO.setFirstName("test");
        createUserDTO.setLastName("test");
        createUserDTO.setLangKey("zh-CN");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(objectMapper.writeValueAsString(createUserDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        logger.info("Result: {}", result.getResponse().getContentAsString());
    }

}
