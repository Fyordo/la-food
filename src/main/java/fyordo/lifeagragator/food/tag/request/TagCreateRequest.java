package fyordo.lifeagragator.food.tag.request;

import fyordo.lifeagragator.food.tag.Tag;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequest {
    @NonNull
    private String title;

    @NonNull
    private String color;

    @NonNull
    private String textColor;
}
