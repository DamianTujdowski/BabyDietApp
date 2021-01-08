package com.dietator.diet.controller;

import com.dietator.diet.domain.User;
import com.dietator.diet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    private User userWithWrongEmail;

    @BeforeEach
    public void init() {
        userWithWrongEmail = new User(1, "franko123", "lala1515", "franek@lolo@", new HashSet<>());
    }

    @Test
    public void shouldReturnStatus400_whenTryingToSaveUserWithWrongEmail() throws Exception {
        //given

        //when

        //then
        mvc.perform(post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(userWithWrongEmail)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_whenTryingToSaveUserWithWrongEmail() throws Exception {
        //given

        //when
        MvcResult result = mvc.perform(post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(userWithWrongEmail)))
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();
        String expectedResponse = "email: incorrect address";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

}