package fyordo.lifeagragator.food.dish.dto;

import fyordo.lifeagragator.food.dish.Dish;
import fyordo.lifeagragator.food.ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishShortDto {
    @NonNull
    protected Long id;
    @NonNull
    protected String title;
    protected String description;
    protected String receipt;

    public DishShortDto(Dish dish){
        id = dish.getId();
        title = dish.getTitle();
        description = dish.getDescription();
        receipt = dish.getReceipt();
    }
}
