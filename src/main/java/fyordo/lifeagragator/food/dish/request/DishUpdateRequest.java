package fyordo.lifeagragator.food.dish.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishUpdateRequest extends DishCreateRequest {
    @NonNull
    private Long id;
}
