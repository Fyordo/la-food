package fyordo.lifeagragator.food.tag;

import fyordo.lifeagragator.food.base.exceptions.ModelNotFoundException;
import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.tag.request.TagCreateRequest;
import fyordo.lifeagragator.food.tag.request.TagUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getTags(){
        return tagRepository.findAcessable(WorkspaceUtils.getUserId());
    }

    public Tag getTagById(Long id){
        return tagRepository
                .findById(id)
                .filter((Tag tag) -> Objects.equals(tag.getCreatedUserId(), WorkspaceUtils.getUserId()))
                .orElseThrow(ModelNotFoundException::new);
    }

    public Tag createTag(TagCreateRequest data){
        Tag tag = new Tag(data);
        tag.setCreatedUserId(WorkspaceUtils.getUserId());
        return tagRepository.save(tag);
    }

    public Tag updateTag(TagUpdateRequest data){
        getTagById(data.getId());

        Tag tag = new Tag(data);
        tag.setCreatedUserId(WorkspaceUtils.getUserId());
        return tagRepository.save(tag);
    }

    public void deleteTagById(Long id){
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}
