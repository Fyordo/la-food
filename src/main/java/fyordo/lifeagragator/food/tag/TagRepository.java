package fyordo.lifeagragator.food.tag;

import fyordo.lifeagragator.food.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.createdUserId = ?1")
    List<Tag> findAcessable(Long createdUserId);
}