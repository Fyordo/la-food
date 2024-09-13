package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.base.exceptions.ModelNotFoundException;
import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
import fyordo.lifeagragator.food.tag.Tag;
import fyordo.lifeagragator.food.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final TagService tagService;

    public List<Ingredient> getIngredients(){
        return ingredientRepository.findAcessable(WorkspaceUtils.getUserId());
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
