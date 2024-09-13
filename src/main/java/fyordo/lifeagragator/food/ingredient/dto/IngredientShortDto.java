package fyordo.lifeagragator.food.ingredient.dto;

import fyordo.lifeagragator.food.ingredient.Ingredient;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientShortDto {
    @NonNull
    protected Long id;
    @NonNull
    protected String title;
    protected String description;

    public IngredientShortDto(Ingredient ingredient){
        id = ingredient.getId();
        title = ingredient.getTitle();
        description = ingredient.getDescription();
    }
}
