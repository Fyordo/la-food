package fyordo.lifeagragator.food.dish.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCreateRequest {
    @NonNull
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String receipt;
}
