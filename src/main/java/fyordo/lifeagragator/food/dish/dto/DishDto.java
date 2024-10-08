package fyordo.lifeagragator.food.dish.dto;

import fyordo.lifeagragator.food.dish.Dish;
import fyordo.lifeagragator.food.dish.DishIngredient;
import fyordo.lifeagragator.food.ingredient.Ingredient;
import fyordo.lifeagragator.food.ingredient.dto.IngredientDto;
import fyordo.lifeagragator.food.ingredient.dto.IngredientShortDto;
import fyordo.lifeagragator.food.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishDto extends DishShortDto {
    private Set<TagDto> tags;
    private Set<IngredientShortDto> ingredients;

    public DishDto(Dish dish){
        super(dish);
        tags = dish.getTags()
                .stream()
                .map(TagDto::new)
                .collect(Collectors.toSet());
        ingredients = dish.getDishIngredients()
                .stream()
                .map(DishIngredient::getIngredient)
                .map(IngredientShortDto::new)
                .collect(Collectors.toSet());
    }
}
