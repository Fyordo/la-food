package fyordo.lifeagragator.food.dish.request;

import jakarta.annotation.Nullable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishUpdateRequest extends DishCreateRequest {
    @NonNull
    private Long id;

    public DishUpdateRequest(@NonNull Long id, @NonNull String title, @Nullable String description, @Nullable String receipt) {
        super(title, description, receipt);
        this.id = id;
    }
}
