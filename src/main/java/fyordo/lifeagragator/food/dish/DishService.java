package fyordo.lifeagragator.food.dish;

import com.querydsl.core.types.dsl.BooleanExpression;
import fyordo.lifeagragator.food.base.exceptions.ModelNotFoundException;
import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.dish.request.DishCreateRequest;
import fyordo.lifeagragator.food.dish.request.DishFilter;
import fyordo.lifeagragator.food.dish.request.DishUpdateRequest;
import fyordo.lifeagragator.food.tag.Tag;
import fyordo.lifeagragator.food.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final TagService tagService;

    public List<Dish> getDishes(DishFilter dishFilter){
        return (List<Dish>) dishRepository
                .findAll(generateFilter(dishFilter))
                ;
    }

    private BooleanExpression generateFilter(DishFilter dishFilter) {
        fyordo.lifeagragator.food.dish.QDish qDish = fyordo.lifeagragator.food.dish.QDish.dish;
        BooleanExpression result = qDish.isNotNull();

        if (dishFilter.getOnlyMy()){
            result = result.and(qDish.createdUserId.eq(WorkspaceUtils.getUserId()));
        }

        if (dishFilter.getSearch() != null){
            result = result.and(qDish.title.containsIgnoreCase(dishFilter.getSearch()));
        }

        if (dishFilter.getTagIds() != null && !dishFilter.getTagIds().isEmpty()){
            BooleanExpression tagsQueryExpression = qDish.isNull();
            for (Long tagId : dishFilter.getTagIds()){
                tagsQueryExpression = tagsQueryExpression.or(qDish.tags.contains(
                        tagService.getTagById(tagId)
                ));
            }

            result = result.and(tagsQueryExpression);
        }

        return result;
    }

    public Dish getDishById(Long id){
        return dishRepository
                .findById(id)
                .filter((Dish dish) -> Objects.equals(dish.getCreatedUserId(), WorkspaceUtils.getUserId()))
                .orElseThrow(ModelNotFoundException::new);
    }

    public Dish createDish(DishCreateRequest data){
        Dish dish = new Dish(data);
        dish.setCreatedUserId(WorkspaceUtils.getUserId());
        return dishRepository.save(dish);
    }

    public Dish updateDish(DishUpdateRequest data){
        getDishById(data.getId());

        Dish dish = new Dish(data);
        dish.setCreatedUserId(WorkspaceUtils.getUserId());
        return dishRepository.save(dish);
    }

    public Dish addTagToDish(Long tagId, Long dishId){
        Dish dish = getDishById(dishId);
        dish.getTags().add(
                tagService.getTagById(tagId)
        );

        return dishRepository.save(dish);
    }

    public Dish removeTagFromDish(Long tagId, Long dishId){
        Dish dish = getDishById(dishId);
        dish.getTags().removeIf((Tag tag) -> Objects.equals(tag.getId(), tagId));

        return dishRepository.save(dish);
    }

    public void deleteDishById(Long id){
        Dish dish = getDishById(id);
        dishRepository.delete(dish);
    }
}
