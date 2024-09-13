package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.base.exceptions.ModelNotFoundException;
import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

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

    public void deleteIngredientById(Long id){
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }
}
