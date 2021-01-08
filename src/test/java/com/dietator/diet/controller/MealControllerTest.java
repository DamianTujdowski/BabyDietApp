package com.dietator.diet.controller;

import com.dietator.diet.domain.*;
import com.dietator.diet.service.MealService;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MealController.class)
class MealControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MealService service;

    private Meal mealWithEmptyDesignation;

    @BeforeEach
    public void init() {
        mealWithEmptyDesignation = new Meal(5L, "", 0, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1L);
    }

    @Test
    public void shouldReturnStatus400_whenTryingToSaveMealWithEmptyDescription() throws Exception {
        //given

        //when

        //then
        mvc.perform(post("/meals")
                .contentType("application/json")
                .content(mapper.writeValueAsString(mealWithEmptyDesignation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_whenTryingToSaveIngredientWithEmptyStringSetAsDesignation() throws Exception {
        //given

        //when
        MvcResult result = mvc.perform(post("/meals")
                .contentType("application/json")
                .content(mapper.writeValueAsString(mealWithEmptyDesignation)))
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