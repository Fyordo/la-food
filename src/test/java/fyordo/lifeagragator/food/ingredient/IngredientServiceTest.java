package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientFilter;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
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
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private TagService tagService;

    @Test
    public void testCreateIngredient() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );
            Ingredient expected = new Ingredient(data);

            Ingredient result = ingredientService.createIngredient(data);

            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, ingredientService.getIngredients(new IngredientFilter()).size());
            ingredientService.deleteIngredientById(result.getId());
        }
    }

    @Test
    public void testDeleteIngredient() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );

            Ingredient result = ingredientService.createIngredient(data);
            ingredientService.deleteIngredientById(result.getId());

            assertEquals(0, ingredientService.getIngredients(new IngredientFilter()).size());
        }
    }

    @Test
    public void testUpdateIngredient() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );

            Ingredient result = ingredientService.createIngredient(data);
            IngredientUpdateRequest updateData = new IngredientUpdateRequest(
                    result.getId(), "TEST_TITLE_2", "TEST_DESCRIPTION"
            );

            result = ingredientService.updateIngredient(updateData);
            assertEquals(updateData.getDescription(), result.getDescription());
            assertEquals(updateData.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, ingredientService.getIngredients(new IngredientFilter()).size());
            ingredientService.deleteIngredientById(result.getId());
        }
    }

    @Test
    public void testGetIngredientById() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );
            var ingredient = ingredientService.createIngredient(data);

            var result = ingredientService.getIngredientById(ingredient.getId());
            assertEquals(data.getDescription(), result.getDescription());
            assertEquals(data.getTitle(), result.getTitle());
            assertEquals(1L, result.getCreatedUserId());
            ingredientService.deleteIngredientById(ingredient.getId());
        }
    }

    @Test
    public void testGetIngredients() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientFilter ingredientFilter = new IngredientFilter(Map.of());
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );
            var ingredient1 = ingredientService.createIngredient(data);
            var ingredient2 = ingredientService.createIngredient(data);
            var ingredient3 = ingredientService.createIngredient(data);

            var result = ingredientService.getIngredients(ingredientFilter);
            assertEquals(3, result.size());
            ingredientService.deleteIngredientById(ingredient1.getId());
            ingredientService.deleteIngredientById(ingredient2.getId());
            ingredientService.deleteIngredientById(ingredient3.getId());
        }
    }

    @Test
    public void testGetIngredientsWithFilter() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientFilter ingredientFilter = new IngredientFilter(Map.of());

            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE_1", "TEST_DESCRIPTION"
            );
            var ingredient1 = ingredientService.createIngredient(data);
            data = new IngredientCreateRequest(
                    "TEST_TITLE_2", "TEST_DESCRIPTION"
            );
            var ingredient2 = ingredientService.createIngredient(data);
            data = new IngredientCreateRequest(
                    "TEST_TITLE_3", "TEST_DESCRIPTION"
            );
            var ingredient3 = ingredientService.createIngredient(data);

            ingredientFilter.setSearch("1");
            var result = ingredientService.getIngredients(ingredientFilter);
            assertEquals(1, result.size());


            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );
            Tag resultTag = tagService.createTag(dataTag);
            ingredientFilter.setSearch(null);
            ingredientService.addTagToIngredient(resultTag.getId(), ingredient1.getId());
            ingredientService.addTagToIngredient(resultTag.getId(), ingredient2.getId());

            ingredientFilter.setTagIds(List.of(resultTag.getId()));
            result = ingredientService.getIngredients(ingredientFilter);

            assertEquals(2, result.size());
            ingredientService.deleteIngredientById(ingredient1.getId());
            ingredientService.deleteIngredientById(ingredient2.getId());
            ingredientService.deleteIngredientById(ingredient3.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }

    @Test
    public void testAddTagToIngredient() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );
            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Ingredient resultIngredient = ingredientService.createIngredient(data);
            Tag resultTag = tagService.createTag(dataTag);

            resultIngredient = ingredientService.addTagToIngredient(resultTag.getId(), resultIngredient.getId());

            assertEquals(1, resultIngredient.getTags().size());
            ingredientService.deleteIngredientById(resultIngredient.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }

    @Test
    public void testRemoveTagFromIngredient() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            IngredientCreateRequest data = new IngredientCreateRequest(
                    "TEST_TITLE", "TEST_DESCRIPTION"
            );
            TagCreateRequest dataTag = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Ingredient resultIngredient = ingredientService.createIngredient(data);
            Tag resultTag = tagService.createTag(dataTag);

            resultIngredient = ingredientService.addTagToIngredient(resultTag.getId(), resultIngredient.getId());
            assertEquals(1, resultIngredient.getTags().size());

            resultIngredient = ingredientService.removeTagFromIngredient(resultTag.getId(), resultIngredient.getId());
            assertEquals(0, resultIngredient.getTags().size());

            ingredientService.deleteIngredientById(resultIngredient.getId());
            tagService.deleteTagById(resultTag.getId());
        }
    }
}
