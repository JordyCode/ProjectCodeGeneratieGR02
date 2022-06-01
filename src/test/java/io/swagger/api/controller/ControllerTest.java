package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.jwt.JwtTokenProvider;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public abstract class ControllerTest {

    protected ObjectMapper mapper;
    protected User user1;
    protected User user2;
    protected List<User> users;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Before
    public void prepare() {
        user1 = new User("Gillian", "Fowler", "test1@mail.nl");
        user1.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));

        user2 = new User("Michael", "Mayers", "test2@mail.nl");
        user2.setRoles(Arrays.asList(Role.ROLE_USER));

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        mapper = new ObjectMapper();
    }

    protected String getEmployeeToken() {
        return jwtTokenProvider.createToken(user1.getUsername(),user1.getRoles());
    }

    protected String getUserToken() {
        return jwtTokenProvider.createToken(user2.getUsername(),user2.getRoles());
    }


}
