package fyordo.lifeagragator.food.ingredient.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUpdateRequest extends IngredientCreateRequest {
    @NonNull
    private Long id;

    public IngredientUpdateRequest(@NonNull Long id, @NonNull String title, @NonNull String description) {
        super(title, description);
        this.id = id;
    }
}
