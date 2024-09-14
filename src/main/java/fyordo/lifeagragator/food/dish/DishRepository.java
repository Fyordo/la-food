package fyordo.lifeagragator.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long>, QuerydslPredicateExecutor<Dish> {

    @Query("select d from Dish d where d.createdUserId = ?1")
    List<Dish> findAcessable(Long createdUserId);
}