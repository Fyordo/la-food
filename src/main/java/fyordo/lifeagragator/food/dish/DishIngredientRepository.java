package fyordo.lifeagragator.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DishIngredientRepository extends JpaRepository<DishIngredient, Long> {
    @Query("select d from DishIngredient d where d.dish.id = :dishId and d.ingredient.id = :ingredientId")
    Iterable<DishIngredient> findByDishIdAndIngredientId(@Param("dishId") Long dishId, @Param("ingredientId") Long ingredientId);
}