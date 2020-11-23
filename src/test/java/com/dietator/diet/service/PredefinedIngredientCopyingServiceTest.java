package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.domain.MealCategory;
import com.dietator.diet.domain.PreparationDifficulty;
import com.dietator.diet.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredefinedIngredientCopyingServiceTest {

    @InjectMocks
    private PredefinedIngredientCopyingService ingredientCopyingService;

    @Mock
    private IngredientRepository ingredientRepository;

    private Ingredient ingredientOne, ingredientTwo, ingredientThree, ingredientFour;
    private Set<Ingredient> ingredientsOneTwoSet, ingredientsThreeFourSet, fourIngredientsSet;

    @BeforeEach
    public void init() {
        ingredientOne = new Ingredient(1L, "potato", 340, 180, false, false, true, null);
        ingredientTwo = new Ingredient(2L, "cucumber", 150, 100, true, false, true, null);
        ingredientThree = new Ingredient(3L, "carrot", 98, 70, true, false, false, 1L);
        ingredientFour = new Ingredient(4L, "sugar", 315, 14, false, false, false, 1L);
        ingredientsOneTwoSet = Stream.of(ingredientOne, ingredientTwo).collect(Collectors.toSet());
        ingredientsThreeFourSet = Stream.of(ingredientThree, ingredientFour).collect(Collectors.toSet());
        fourIngredientsSet = Stream.of(ingredientOne, ingredientTwo, ingredientThree, ingredientFour).collect(Collectors.toSet());
    }

    @Test
    public void shouldCopyPredefinedIngredients() {
        //given
        when(ingredientRepository.save(any(Ingredient.class)))
                .thenReturn(new Ingredient(ingredientOne))
                .thenReturn(new Ingredient(ingredientTwo));
        //when
        Set<Ingredient> ingredients = ingredientCopyingService.copyPreDefinedIngredients(ingredientsOneTwoSet, new HashSet<>());
        Ingredient copiedIngredient = ingredients
                .stream()
                .filter(ingredient -> ingredient.getDesignation().equals("potato"))
                .findFirst()
                .get();
        //then
        assertNotEquals(ingredientOne, copiedIngredient);
    }

    @Test
    public void shouldCopyAllPredefinedIngredients() {
        //given
        when(ingredientRepository.save(any(Ingredient.class)))
                .thenReturn(new Ingredient(ingredientOne))
                .thenReturn(new Ingredient(ingredientTwo));
        //when
        Set<Ingredient> ingredients = ingredientCopyingService.copyPreDefinedIngredients(ingredientsOneTwoSet, new HashSet<>());
        Set<Ingredient> copiedIngredients = ingredients
                .stream()
                .filter(ingredient -> !ingredient.isPreDefined())
                .collect(Collectors.toSet());
        //then
        assertEquals(2, copiedIngredients.size());
    }

    @Test
    public void shouldCopyPredefinedIngredientsAndPassFurtherNotPredefinedIngredients() {
        //given
        when(ingredientRepository.save(any(Ingredient.class)))
                .thenReturn(new Ingredient(ingredientOne))
                .thenReturn(new Ingredient(ingredientTwo))
                .thenReturn(ingredientThree)
                .thenReturn(ingredientFour);
        //when
        Set<Ingredient> ingredients = ingredientCopyingService.copyPreDefinedIngredients(fourIngredientsSet, new HashSet<>());
        Set<Ingredient> copiedIngredients = ingredients
                .stream()
                .filter(ingredient -> !ingredient.isPreDefined())
                .collect(Collectors.toSet());
        //then
        assertEquals(4, copiedIngredients.size());
    }
}