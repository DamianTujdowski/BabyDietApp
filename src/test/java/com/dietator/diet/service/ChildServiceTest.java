package com.dietator.diet.service;

import com.dietator.diet.domain.*;
import com.dietator.diet.repository.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChildServiceTest {

    @InjectMocks
    private ChildService childService;
    @Mock
    private PredefinedMealCopyingService mealCopyingService;
    @Mock
    private PredefinedIngredientCopyingService ingredientCopyingService;
    @Mock
    private ChildRepository childRepository;

    private Child noMealsNoIngredientsChild, twoMealsTwoIngredientsChild, secondTwoMealsTwoIngredientsChild, twoMealsChildFromClient, fourMealsChildFromClient;


    @BeforeEach
    public void init() {
        Ingredient ingredientOne = new Ingredient(1L, "potato", 340, 180, false, false, false);
        Ingredient ingredientTwo = new Ingredient(2L, "cucumber", 150, 100, true, false, false);
        Ingredient ingredientThree = new Ingredient(3L, "carrot", 98, 70, true, false, false);
        Ingredient ingredientFour = new Ingredient(4L, "sugar", 315, 14, false, false, false);
        Set<Ingredient> ingredientsOneTwoSet = Stream.of(ingredientOne, ingredientTwo).collect(Collectors.toSet());
        Set<Ingredient> ingredientsThreeFourSet = Stream.of(ingredientThree, ingredientFour).collect(Collectors.toSet());
        Set<Ingredient> fourIngredientsSet = Stream.of(ingredientOne, ingredientTwo, ingredientThree, ingredientFour).collect(Collectors.toSet());
        Meal mealOne = new Meal(1, "meal one", 420, "chop chop amd put in pot",
                10, new HashSet<>(), new HashSet<>(), MealCategory.BREAKFAST, PreparationDifficulty.EASY, false);
        Meal mealTwo = new Meal(2, "meal two", 420, "chop chop very fast",
                15, new HashSet<>(), new HashSet<>(), MealCategory.SUPPER, PreparationDifficulty.EASY, false);
        Meal mealThree = new Meal(3, "meal three", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        Meal mealFour = new Meal(4, "meal four", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        Set<Meal> mealOneTwoSet = Stream.of(mealOne, mealTwo).collect(Collectors.toSet());
        Set<Meal> mealThreeFourSet = Stream.of(mealThree, mealFour).collect(Collectors.toSet());
        Set<Meal> fourMealsSet = Stream.of(mealOne, mealTwo, mealThree, mealFour).collect(Collectors.toSet());
        noMealsNoIngredientsChild = new Child(1, "Zosia", LocalDate.of(2019, 6, 26), new HashSet<>(), new HashSet<>());
        twoMealsTwoIngredientsChild = new Child(1, "Zosia", LocalDate.of(2019, 6, 26), mealOneTwoSet, ingredientsOneTwoSet);
        secondTwoMealsTwoIngredientsChild = new Child(1, "Zosia", LocalDate.of(2019, 6, 26), mealThreeFourSet, ingredientsThreeFourSet);
        twoMealsChildFromClient = new Child(1, "Zosia", LocalDate.of(2019, 6, 26), mealOneTwoSet, ingredientsOneTwoSet);
        fourMealsChildFromClient = new Child(1, "Zosia", LocalDate.of(2019, 6, 26), fourMealsSet, fourIngredientsSet);
    }

    @Test
    public void whenEditingChildWithNoMeals_shouldAddAllMeals() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(noMealsNoIngredientsChild));
        when(mealCopyingService.copyPreDefinedMeals(anySet(), anySet()))
                .thenReturn(twoMealsChildFromClient.getConsumedMeals());
        //when
        Child editedChild = childService.editChild(twoMealsChildFromClient);
        //then
        assertEquals(2, editedChild.getConsumedMeals().size());
    }

    @Test
    public void whenEditingChildWhichHasMealsAlready_shouldAddOnlyNewMeals() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(mealCopyingService.copyPreDefinedMeals(anySet(), anySet()))
                .thenReturn(fourMealsChildFromClient.getConsumedMeals());
        //when
        Child editedChild = childService.editChild(fourMealsChildFromClient);
        //then
        assertEquals(4, editedChild.getConsumedMeals().size());
    }

    @Test
    public void whenEditingChild_shouldAddAllMeals_ifClientAndDatabaseChildHaveNoCommonMeals() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(mealCopyingService.copyPreDefinedMeals(anySet(), anySet()))
                .thenReturn(secondTwoMealsTwoIngredientsChild.getConsumedMeals());
        //when
        Child editedChild = childService.editChild(secondTwoMealsTwoIngredientsChild);
        //then
        assertEquals(4, editedChild.getConsumedMeals().size());
    }

    @Test
    public void whenEditingChild_shouldAddNoMeals_ifClientObjectHasNoNewMeals() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(mealCopyingService.copyPreDefinedMeals(anySet(), anySet()))
                .thenReturn(twoMealsChildFromClient.getConsumedMeals());
        //when
        Child editedChild = childService.editChild(twoMealsChildFromClient);
        //then
        assertEquals(2, editedChild.getConsumedMeals().size());
    }

    @Test
    public void whenEditingChildWithNoIngredients_shouldAddAllIngredients() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(noMealsNoIngredientsChild));
        when(ingredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(twoMealsChildFromClient.getFavouriteAndDislikedIngredients());
        //when
        Child editedChild = childService.editChild(twoMealsChildFromClient);
        //then
        assertEquals(2, editedChild.getFavouriteAndDislikedIngredients().size());
    }

    @Test
    public void whenEditingChildWhichHasIngredientsAlready_shouldAddOnlyNewIngredients() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(ingredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(fourMealsChildFromClient.getFavouriteAndDislikedIngredients());
        //when
        Child editedChild = childService.editChild(fourMealsChildFromClient);
        //then
        assertEquals(4, editedChild.getFavouriteAndDislikedIngredients().size());
    }

    @Test
    public void whenEditingChild_shouldAddAllIngredients_ifClientAndDatabaseChildHaveNoCommonIngredients() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(ingredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(secondTwoMealsTwoIngredientsChild.getFavouriteAndDislikedIngredients());
        //when
        Child editedChild = childService.editChild(secondTwoMealsTwoIngredientsChild);
        //then
        assertEquals(4, editedChild.getFavouriteAndDislikedIngredients().size());
    }

    @Test
    public void whenEditingChild_shouldAddNoIngredients_ifClientObjectHasNoNewIngredients() {
        //given
        when(childRepository.findById(1L)).thenReturn(Optional.of(twoMealsTwoIngredientsChild));
        when(ingredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(twoMealsChildFromClient.getFavouriteAndDislikedIngredients());
        //when
        Child editedChild = childService.editChild(twoMealsChildFromClient);
        //then
        assertEquals(2, editedChild.getFavouriteAndDislikedIngredients().size());
    }

}