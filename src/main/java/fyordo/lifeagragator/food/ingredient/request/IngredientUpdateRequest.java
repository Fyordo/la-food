package fyordo.lifeagragator.food.ingredient.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUpdateRequest extends IngredientCreateRequest {
    @NonNull
    private Long id;
}
