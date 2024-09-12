package fyordo.lifeagragator.food.tag.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdateRequest extends TagCreateRequest {
    @NonNull
    private Long id;
}
