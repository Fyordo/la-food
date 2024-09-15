package fyordo.lifeagragator.food.dish;

import fyordo.lifeagragator.food.base.reponse.ListResponse;
import fyordo.lifeagragator.food.dish.dto.DishDto;
import fyordo.lifeagragator.food.dish.request.DishAndIngredientRequest;
import fyordo.lifeagragator.food.dish.request.DishCreateRequest;
import fyordo.lifeagragator.food.dish.request.DishFilter;
import fyordo.lifeagragator.food.dish.request.DishUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @GetMapping("")
    public ResponseEntity<ListResponse<DishDto>> getAllDishes(@RequestParam Map<String, String> filter, @RequestParam(required = false) List<Long> tagIds){
        DishFilter dishFilter = new DishFilter(filter);
        dishFilter.setTagIds(tagIds);

        List<DishDto> result = dishService.getDishes(dishFilter).stream().map(DishDto::new).toList();
        return ResponseEntity.ok(new ListResponse<>(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable Long id){
        return ResponseEntity.ok(new DishDto(dishService.getDishById(id)));
    }

    @PostMapping()
    public ResponseEntity<DishDto> createDish(@RequestBody DishCreateRequest request){
        return ResponseEntity.status(201).body(
                new DishDto(dishService.createDish(request))
        );
    }

    @PutMapping()
    public ResponseEntity<DishDto> updateDish(@RequestBody DishUpdateRequest request){
        return ResponseEntity.status(201).body(
                new DishDto(dishService.updateDish(request))
        );
    }

    @PutMapping("/{dishId}/add-tag/{tagId}")
    public ResponseEntity<DishDto> addTagToDish(@PathVariable Long dishId, @PathVariable Long tagId){
        return ResponseEntity.ok(
                new DishDto(dishService.addTagToDish(dishId, tagId))
        );
    }

    @PutMapping("/{dishId}/remove-tag/{tagId}")
    public ResponseEntity<DishDto> removeTagFromDish(@PathVariable Long dishId, @PathVariable Long tagId){
        return ResponseEntity.ok(
                new DishDto(dishService.removeTagFromDish(dishId, tagId))
        );
    }

    @PutMapping("/add-ingredient")
    public ResponseEntity<DishDto> addIngredientToDish(@RequestBody DishAndIngredientRequest data){
        return ResponseEntity.ok(
                new DishDto(dishService.addIngredientToDish(data.getIngredientId(), data.getDishId(), data.getDescription()))
        );
    }

    @PutMapping("/remove-ingredient")
    public ResponseEntity<?> removeIngredientFromDish(@RequestBody DishAndIngredientRequest data){
        dishService.removeIngredientFromDish(data.getIngredientId(), data.getDishId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id){
        dishService.deleteDishById(id);
        return ResponseEntity.noContent().build();
    }
}
