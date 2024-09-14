package fyordo.lifeagragator.food.ingredient;

import com.querydsl.core.types.dsl.BooleanExpression;
import fyordo.lifeagragator.food.base.exceptions.ModelNotFoundException;
import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientFilter;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
import fyordo.lifeagragator.food.tag.Tag;
import fyordo.lifeagragator.food.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final TagService tagService;

    public List<Ingredient> getIngredients(IngredientFilter ingredientFilter){
        return (List<Ingredient>) ingredientRepository
                .findAll(generateFilter(ingredientFilter))
                ;
    }

    private BooleanExpression generateFilter(IngredientFilter ingredientFilter) {
        fyordo.lifeagragator.food.ingredient.QIngredient qIngredient = fyordo.lifeagragator.food.ingredient.QIngredient.ingredient;
        BooleanExpression result = qIngredient.isNotNull();

        if (ingredientFilter.getOnlyMy()){
            result = result.and(qIngredient.createdUserId.eq(WorkspaceUtils.getUserId()));
        }

        if (ingredientFilter.getSearch() != null){
            result = result.and(qIngredient.title.containsIgnoreCase(ingredientFilter.getSearch()));
        }

        if (ingredientFilter.getTagIds() != null && !ingredientFilter.getTagIds().isEmpty()){
            BooleanExpression tagsQueryExpression = qIngredient.isNull();
            for (Long tagId : ingredientFilter.getTagIds()){
                tagsQueryExpression = tagsQueryExpression.or(qIngredient.tags.contains(
                        tagService.getTagById(tagId)
                ));
            }

            result = result.and(tagsQueryExpression);
        }

        return result;
    }

    public Ingredient getIngredientById(Long id){
        return ingredientRepository
                .findById(id)
                .filter((Ingredient ingredient) -> Objects.equals(ingredient.getCreatedUserId(), WorkspaceUtils.getUserId()))
                .orElseThrow(ModelNotFoundException::new);
    }

    public Ingredient createIngredient(IngredientCreateRequest data){
        Ingredient ingredient = new Ingredient(data);
        ingredient.setCreatedUserId(WorkspaceUtils.getUserId());
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(IngredientUpdateRequest data){
        getIngredientById(data.getId());

        Ingredient ingredient = new Ingredient(data);
        ingredient.setCreatedUserId(WorkspaceUtils.getUserId());
        return ingredientRepository.save(ingredient);
    }

    public Ingredient addTagToIngredient(Long tagId, Long ingredientId){
        Ingredient ingredient = getIngredientById(ingredientId);
        ingredient.getTags().add(
                tagService.getTagById(tagId)
        );

        return ingredientRepository.save(ingredient);
    }

    public Ingredient removeTagFromIngredient(Long tagId, Long ingredientId){
        Ingredient ingredient = getIngredientById(ingredientId);
        ingredient.getTags().removeIf((Tag tag) -> Objects.equals(tag.getId(), tagId));

        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredientById(Long id){
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }
}
