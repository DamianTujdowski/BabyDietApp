package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PredefinedIngredientCopierTest {

    @InjectMocks
    private PredefinedIngredientCopier copier;
    @Mock
    private IngredientRepository ingredientRepository;

    private Set<Ingredient> ingredientsFromClient, ingredientsFromDb;

    @BeforeEach
    public void init() {
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false, 5L);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false, 5L);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false, 5L);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false, 5L);
        ingredientsFromClient = Stream.of(carrot, sugar).collect(Collectors.toSet());
        ingredientsFromDb = Stream.of(potato, cucumber).collect(Collectors.toSet());
    }

    @Test
    public void shouldNotReturnNullPointerException_whenClientIngredientsAreNull() {
     //given

     //when
        Set<Ingredient> ingredientsJoinedFromClientAndDb = copier.copyPreDefinedIngredients(null, ingredientsFromDb);
     //then
        assertEquals(0, ingredientsJoinedFromClientAndDb.size());
    }

    @Test
    public void shouldNotReturnNullPointerException_whenDbIngredientsAreNull() {
     //given

     //when
        Set<Ingredient> ingredientsJoinedFromClientAndDb = copier.copyPreDefinedIngredients(ingredientsFromClient, null);
     //then
        assertEquals(2, ingredientsJoinedFromClientAndDb.size());
    }
}