package fyordo.lifeagragator.food.ingredient.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCreateRequest {
    @NonNull
    private String title;

    @NonNull
    private String description;
}
