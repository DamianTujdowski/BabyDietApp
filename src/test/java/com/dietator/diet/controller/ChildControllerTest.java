package com.dietator.diet.controller;

import com.dietator.diet.domain.*;
import com.dietator.diet.service.ChildService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChildController.class)
class ChildControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ChildService service;

    private Child childWithToShortName;


    @BeforeEach
    public void init() {
        Ingredient ingredientOne = new Ingredient(1L, "potato", 340, 180, false, false, false, null);
        Ingredient ingredientTwo = new Ingredient(2L, "cucumber", 150, 100, true, false, false, null);
        Ingredient ingredientThree = new Ingredient(3L, "carrot", 98, 70, true, false, false, null);
        Ingredient ingredientFour = new Ingredient(4L, "sugar", 315, 14, false, false, false, null);
        Meal mealOne = new Meal(1, "meal one", 420, "chop chop amd put in pot",
                10, new HashSet<>(), new HashSet<>(), MealCategory.BREAKFAST, PreparationDifficulty.EASY, false, 1L);
        Meal mealTwo = new Meal(2, "meal two", 420, "chop chop very fast",
                15, new HashSet<>(), new HashSet<>(), MealCategory.SUPPER, PreparationDifficulty.EASY, false, 1L);
        Meal mealThree = new Meal(3, "meal three", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1L);
        Meal mealFour = new Meal(4, "meal four", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1L);
        childWithToShortName = new Child(1, "z", LocalDate.of(2019, 6, 26), new HashSet<>(), new HashSet<>());
    }

    @Test
    public void shouldReturnStatus400_whenTryingToSaveMealWithEmptyDescription() throws Exception {
        //given

        //when

        //then
        mvc.perform(post("/children")
                .contentType("application/json")
                .content(mapper.writeValueAsString(childWithToShortName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_whenTryingToSaveIngredientWithEmptyStringSetAsDesignation() throws Exception {
        //given

        //when
        MvcResult result = mvc.perform(post("/children")
                .contentType("application/json")
                .content(mapper.writeValueAsString(childWithToShortName)))
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();
        String expectedResponse = "firstName: must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

}