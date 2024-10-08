package fyordo.lifeagragator.food.dish;

import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.dish.request.DishCreateRequest;
import fyordo.lifeagragator.food.dish.request.DishFilter;
import fyordo.lifeagragator.food.dish.request.DishUpdateRequest;
import fyordo.lifeagragator.food.ingredient.Ingredient;
import fyordo.lifeagragator.food.ingredient.IngredientService;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.tag.Tag;
import fyordo.lifeagragator.food.tag.TagService;
import fyordo.lifeagragator.food.tag.request.TagCreateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private TagService tagService;

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testCreateDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            Dish expected = new Dish(data);

            Dish result = dishService.createDish(data);

            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, dishService.getDishes(new DishFilter()).size());
            dishService.deleteDishById(result.getId());
        }
    }

    @Test
    public void testDeleteDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );

            Dish result = dishService.createDish(data);
            dishService.deleteDishById(result.getId());

            assertEquals(0, dishService.getDishes(new DishFilter()).size());
        }
    }

    @Test
    public void testUpdateDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );

            Dish result = dishService.createDish(data);
            DishUpdateRequest updateData = new DishUpdateRequest(
                    result.getId(), "TEST_TITLE_2", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );

            result = dishService.updateDish(updateData);
            assertEquals(updateData.getDescription(), result.getDescription());
            assertEquals(updateData.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, dishService.getDishes(new DishFilter()).size());
            dishService.deleteDishById(result.getId());
        }
    }

    @Test
    public void testGetDishById() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            var dish = dishService.createDish(data);

            var result = dishService.getDishById(dish.getId());
            assertEquals(data.getDescription(), result.getDescription());
            assertEquals(data.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            dishService.deleteDishById(dish.getId());
        }
    }

    @Test
    public void testGetDishs() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishFilter dishFilter = new DishFilter(Map.of());
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            var dish1 = dishService.createDish(data);
            var dish2 = dishService.createDish(data);
            var dish3 = dishService.createDish(data);

            var result = dishService.getDishes(dishFilter);
            assertEquals(3, result.size());
            dishService.deleteDishById(dish1.getId());
            dishService.deleteDishById(dish2.getId());
            dishService.deleteDishById(dish3.getId());
        }
    }

    @Test
    public void testGetDishsWithFilter() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishFilter dishFilter = new DishFilter(Map.of());
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE_1", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            var dish1 = dishService.createDish(data);
            data = new DishCreateRequest(
                    "TEST_TITLE_2", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            var dish2 = dishService.createDish(data);
            data = new DishCreateRequest(
                    "TEST_TITLE_3", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            var dish3 = dishService.createDish(data);

            dishFilter.setSearch("1");
            var result = dishService.getDishes(dishFilter);
            assertEquals(1, result.size());

            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );
            Tag resultTag = tagService.createTag(dataTag);
            dishService.addTagToDish(resultTag.getId(), dish1.getId());
            dishService.addTagToDish(resultTag.getId(), dish2.getId());

            dishFilter.setSearch(null);
            dishFilter.setTagIds(List.of(resultTag.getId()));
            result = dishService.getDishes(dishFilter);
            assertEquals(2, result.size());

            dishService.deleteDishById(dish1.getId());
            dishService.deleteDishById(dish2.getId());
            dishService.deleteDishById(dish3.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }

    @Test
    public void testAddTagToDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Dish resultDish = dishService.createDish(data);
            Tag resultTag = tagService.createTag(dataTag);

            resultDish = dishService.addTagToDish(resultTag.getId(), resultDish.getId());

            assertEquals(1, resultDish.getTags().size());
            dishService.deleteDishById(resultDish.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }

    @Test
    public void testRemoveTagFromDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Dish resultDish = dishService.createDish(data);
            Tag resultTag = tagService.createTag(dataTag);

            resultDish = dishService.addTagToDish(resultTag.getId(), resultDish.getId());
            assertEquals(1, resultDish.getTags().size());

            resultDish = dishService.removeTagFromDish(resultTag.getId(), resultDish.getId());
            assertEquals(0, resultDish.getTags().size());

            dishService.deleteDishById(resultDish.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }

    @Test
    public void testAddIngredientToDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            IngredientCreateRequest dataIngredient = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );

            Dish resultDish = dishService.createDish(data);
            Ingredient resultIngredient = ingredientService.createIngredient(dataIngredient);

            resultDish = dishService.addIngredientToDish(
                    resultIngredient.getId(),
                    resultDish.getId(),
                    "test_desc"
            );
            assertEquals(1, resultDish.getDishIngredients().size());
            assertEquals("test_desc", resultDish.getDishIngredients().stream().findFirst().get().getDescription());

            dishService.deleteDishById(resultDish.getId());
            ingredientService.deleteIngredientById(resultIngredient.getId());
        }
    }

    @Test
    public void testRemoveIngredientFromDish() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            DishCreateRequest data = new DishCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION", "TEST_RECEIPT"
            );
            IngredientCreateRequest dataIngredient = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );

            Dish resultDish = dishService.createDish(data);
            Ingredient resultIngredient = ingredientService.createIngredient(dataIngredient);

            dishService.addIngredientToDish(
                    resultIngredient.getId(),
                    resultDish.getId(),
                    "test_desc"
            );

            resultDish = dishService.getDishById(resultDish.getId());
            assertEquals(1, dishService.getDishById(resultDish.getId()).getDishIngredients().size());
            assertEquals("test_desc", resultDish.getDishIngredients().stream().findFirst().get().getDescription());

            dishService.removeIngredientFromDish(resultIngredient.getId(), resultDish.getId());
            assertEquals(0, dishService.getDishById(resultDish.getId()).getTags().size());

            dishService.deleteDishById(resultDish.getId());
            ingredientService.deleteIngredientById(resultIngredient.getId());
        }
    }
}
