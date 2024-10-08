package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.base.reponse.ListResponse;
import fyordo.lifeagragator.food.ingredient.dto.IngredientDto;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientFilter;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("")
    public ResponseEntity<ListResponse<IngredientDto>> getAllIngredients(@RequestParam Map<String, String> filter, @RequestParam(required = false) List<Long> tagIds){
        IngredientFilter ingredientFilter = new IngredientFilter(filter);
        ingredientFilter.setTagIds(tagIds);

        List<IngredientDto> result = ingredientService.getIngredients(ingredientFilter).stream().map(IngredientDto::new).toList();
        return ResponseEntity.ok(new ListResponse<>(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable Long id){
        return ResponseEntity.ok(new IngredientDto(ingredientService.getIngredientById(id)));
    }

    @PostMapping()
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientCreateRequest request){
        return ResponseEntity.status(201).body(
                new IngredientDto(ingredientService.createIngredient(request))
        );
    }

    @PutMapping()
    public ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientUpdateRequest request){
        return ResponseEntity.status(201).body(
                new IngredientDto(ingredientService.updateIngredient(request))
        );
    }

    @PutMapping("/{ingredientId}/add-tag/{tagId}")
    public ResponseEntity<IngredientDto> addTagToIngredient(@PathVariable Long ingredientId, @PathVariable Long tagId){
        return ResponseEntity.ok(
                new IngredientDto(ingredientService.addTagToIngredient(ingredientId, tagId))
        );
    }

    @PutMapping("/{ingredientId}/remove-tag/{tagId}")
    public ResponseEntity<IngredientDto> removeTagFromIngredient(@PathVariable Long ingredientId, @PathVariable Long tagId){
        return ResponseEntity.ok(
                new IngredientDto(ingredientService.removeTagFromIngredient(ingredientId, tagId))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id){
        ingredientService.deleteIngredientById(id);
        return ResponseEntity.status(204).build();
    }
}
