package fyordo.lifeagragator.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("select d from Dish d where d.createdUserId = ?1")
    List<Dish> findAcessable(Long createdUserId);
}