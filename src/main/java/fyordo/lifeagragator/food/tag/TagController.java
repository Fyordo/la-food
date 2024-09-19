package fyordo.lifeagragator.food.tag;

import fyordo.lifeagragator.food.base.reponse.ListResponse;
import fyordo.lifeagragator.food.tag.dto.TagDto;
import fyordo.lifeagragator.food.tag.request.TagCreateRequest;
import fyordo.lifeagragator.food.tag.request.TagFilter;
import fyordo.lifeagragator.food.tag.request.TagUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    @GetMapping("")
    public ResponseEntity<ListResponse<TagDto>> getAllTags(@RequestParam Map<String, String> filter){
        TagFilter tagFilter = new TagFilter(filter);
        List<TagDto> result = tagService.getTags(tagFilter).stream().map(TagDto::new).toList();
        return ResponseEntity.ok(new ListResponse<>(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(new TagDto(tagService.getTagById(id)));
    }

    @PostMapping()
    public ResponseEntity<TagDto> createTag(@RequestBody TagCreateRequest request){
        return ResponseEntity.status(201).body(
                new TagDto(tagService.createTag(request))
        );
    }

    @PutMapping()
    public ResponseEntity<TagDto> updateTag(@RequestBody TagUpdateRequest request){
        return ResponseEntity.status(201).body(
                new TagDto(tagService.updateTag(request))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id){
        tagService.deleteTagById(id);
        return ResponseEntity.status(204).build();
    }
}
