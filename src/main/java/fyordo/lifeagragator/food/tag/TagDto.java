package fyordo.lifeagragator.food.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String title;
    private String color;

    public TagDto(Tag tag){
        id = tag.getId();
        title = tag.getTitle();
        color = tag.getColor();
    }
}
