package fyordo.lifeagragator.food.tag;

import fyordo.lifeagragator.food.base.utils.WorkspaceUtils;
import fyordo.lifeagragator.food.tag.request.TagCreateRequest;
import fyordo.lifeagragator.food.tag.request.TagFilter;
import fyordo.lifeagragator.food.tag.request.TagUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void testCreateTag() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );
            Tag expected = new Tag(data);

            Tag result = tagService.createTag(data);

            assertEquals(expected.getColor(), result.getColor());
            assertEquals(expected.getTitle(), result.getTitle());
            assertEquals(expected.getTextColor(), result.getTextColor());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, tagService.getTags(new TagFilter()).size());
            tagService.deleteTagById(result.getId());
        }
    }

    @Test
    public void testDeleteTag() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Tag result = tagService.createTag(data);
            tagService.deleteTagById(result.getId());

            assertEquals(0, tagService.getTags(new TagFilter()).size());
        }
    }

    @Test
    public void testUpdateTag() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );

            Tag result = tagService.createTag(data);
            TagUpdateRequest updateData = new TagUpdateRequest(
                    result.getId(), "TEST_TAG_2", "#FFFFFF", "#FFFFFF"
            );

            result = tagService.updateTag(updateData);
            assertEquals(updateData.getColor(), result.getColor());
            assertEquals(updateData.getTitle(), result.getTitle());
            assertEquals(updateData.getTextColor(), result.getTextColor());
            assertEquals(1L, result.getCreatedUserId());
            assertEquals(1, tagService.getTags(new TagFilter()).size());
            tagService.deleteTagById(result.getId());
        }
    }

    @Test
    public void testGetTagById() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagFilter tagFilter = new TagFilter(Map.of());
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );
            var tag = tagService.createTag(data);

            var result = tagService.getTagById(tag.getId());
            assertEquals(data.getColor(), result.getColor());
            assertEquals(data.getTitle(), result.getTitle());
            assertEquals(data.getTextColor(), result.getTextColor());
            assertEquals(1L, result.getCreatedUserId());
            tagService.deleteTagById(tag.getId());
        }
    }

    @Test
    public void testGetTags() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagFilter tagFilter = new TagFilter(Map.of());
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG", "#000000", "#000000"
            );
            var tag1 = tagService.createTag(data);
            var tag2 = tagService.createTag(data);
            var tag3 = tagService.createTag(data);

            var result = tagService.getTags(tagFilter);
            assertEquals(3, result.size());
            tagService.deleteTagById(tag1.getId());
            tagService.deleteTagById(tag2.getId());
            tagService.deleteTagById(tag3.getId());
        }
    }

    @Test
    public void testGetTagsWithFilter() {
        try (MockedStatic<WorkspaceUtils> mockedStatic = Mockito.mockStatic(WorkspaceUtils.class)) {
            mockedStatic.when(WorkspaceUtils::getUserId).thenReturn(1L);
            TagFilter tagFilter = new TagFilter(Map.of());
            TagCreateRequest data = new TagCreateRequest(
                    "TEST_TAG_1", "#000000", "#000000"
            );
            var tag1 = tagService.createTag(data);
            data = new TagCreateRequest(
                    "TEST_TAG_2", "#000000", "#000000"
            );
            var tag2 = tagService.createTag(data);
            data = new TagCreateRequest(
                    "TEST_TAG_3", "#000000", "#000000"
            );
            var tag3 = tagService.createTag(data);

            tagFilter.setSearch("1");
            var result = tagService.getTags(tagFilter);
            assertEquals(1, result.size());
            tagService.deleteTagById(tag1.getId());
            tagService.deleteTagById(tag2.getId());
            tagService.deleteTagById(tag3.getId());
        }
    }
}
