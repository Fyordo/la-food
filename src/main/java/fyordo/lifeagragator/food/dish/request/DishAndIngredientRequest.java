package fyordo.lifeagragator.food.dish.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishAndIngredientRequest {
    @NonNull
    private Long dishId;
    @NonNull
    private Long ingredientId;
    @Nullable
    private String description;
}
