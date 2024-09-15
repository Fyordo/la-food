package fyordo.lifeagragator.food.tag;

import fyordo.lifeagragator.food.dish.Dish;
import fyordo.lifeagragator.food.ingredient.Ingredient;
import fyordo.lifeagragator.food.tag.request.TagCreateRequest;
import fyordo.lifeagragator.food.tag.request.TagUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tag")
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 31, nullable = false)
    private String title;

    @Column(name = "color", length = 7, nullable = false)
    private String color;

    @Column(name = "text_color", length = 7)
    private String textColor;

    @Column(name = "created_user_id", nullable = false)
    private Long createdUserId;

    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Set<Dish> dishes = new LinkedHashSet<>();

    public Tag(TagCreateRequest data){
        title = data.getTitle();
        color = data.getColor();
        textColor = data.getTextColor();
    }

    public Tag(TagUpdateRequest data){
        id = data.getId();
        title = data.getTitle();
        color = data.getColor();
        textColor = data.getTextColor();
    }
}