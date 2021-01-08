package com.dietator.diet.controller;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.service.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IngredientService service;

    private Ingredient emptyDesignationIngredient, nullMealIdIngredient;

    @BeforeEach
    public void init() {
        emptyDesignationIngredient = new Ingredient(0, "", 340, 180, false, false, false, 5L);
        nullMealIdIngredient = new Ingredient(0, "broccoli", 340, 180, false, false, false, null);
    }

    @Test
    public void shouldReturnStatus400_whenTryingToSaveIngredientWithNoMealId() throws Exception {
        //given

        //when

        //then
        mvc.perform(post("/ingredients")
                .contentType("application/json")
                .content(mapper.writeValueAsString(emptyDesignationIngredient)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_whenTryingToSaveIngredientWithEmptyStringSetAsDesignation() throws Exception {
        //given

        //when
        MvcResult result = mvc.perform(post("/ingredients")
                .contentType("application/json")
                .content(mapper.writeValueAsString(emptyDesignationIngredient)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();
        String expectedResponse = "designation: designation must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

}