package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, QuerydslPredicateExecutor<Ingredient> {
    @Query("select i from Ingredient i where i.createdUserId = ?1")
    List<Ingredient> findAcessable(Long createdUserId);
}