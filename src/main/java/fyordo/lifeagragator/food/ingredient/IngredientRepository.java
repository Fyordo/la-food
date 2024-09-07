package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("select i from Ingredient i where i.createdUserId = ?1")
    List<Ingredient> findAcessable(Long createdUserId);
}