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
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false, 5L);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false, 5L);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false, 5L);
        Ingredient carrotPredefined = new Ingredient(3L, "carrotPredefined", 98, 70, true, false, true, null);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false, 5L);
        Ingredient sugarPredefined = new Ingredient(4L, "sugarPredefined", 315, 14, false, false, true, null);
        Set<Ingredient> potatoCucumber = Stream.of(potato, cucumber).collect(Collectors.toSet());
        Set<Ingredient> carrotSugar = Stream.of(carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredientsWithTwoPredefined = Stream.of(potato, cucumber, carrotPredefined, sugarPredefined).collect(Collectors.toSet());
        ConsumptionTime morning = new ConsumptionTime(1, LocalDateTime.of(2020, Month.APRIL, 13, 10, 40));
        ConsumptionTime afternoon = new ConsumptionTime(2, LocalDateTime.of(2020, Month.APRIL, 13, 15, 18));
        ConsumptionTime evening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 19, 5));
        ConsumptionTime lateEvening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 21, 15));
        Set<ConsumptionTime> twoTimesBeforeAfternoon = Stream.of(morning, afternoon).collect(Collectors.toSet());
        Set<ConsumptionTime> twoTimesAfterAfternoon = Stream.of(evening, lateEvening).collect(Collectors.toSet());
        Set<ConsumptionTime> fourTimes = Stream.of(morning, afternoon, evening, lateEvening).collect(Collectors.toSet());
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