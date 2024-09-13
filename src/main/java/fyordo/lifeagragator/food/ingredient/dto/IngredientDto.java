package fyordo.lifeagragator.food.ingredient.dto;

import fyordo.lifeagragator.food.ingredient.Ingredient;
import fyordo.lifeagragator.food.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto extends IngredientShortDto{
    private Set<TagDto> tags;

    public IngredientDto(Ingredient ingredient){
        super(ingredient);
        tags = ingredient.getTags()
                .stream()
                .map(TagDto::new)
                .collect(Collectors.toSet());
    }
}
