package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.dish.DishIngredient;
import fyordo.lifeagragator.food.ingredient.request.IngredientCreateRequest;
import fyordo.lifeagragator.food.ingredient.request.IngredientUpdateRequest;
import fyordo.lifeagragator.food.tag.Tag;
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
@NoArgsConstructor
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 31, nullable = false)
    private String title;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @Column(name = "created_user_id", nullable = false)
    private Long createdUserId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(name = "ingredient_tags",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ingredient", orphanRemoval = true)
    private Set<DishIngredient> dishIngredients = new LinkedHashSet<>();


    public Ingredient(IngredientCreateRequest data){
        title = data.getTitle();
        description = data.getDescription();
    }

    public Ingredient(IngredientUpdateRequest data){
        id = data.getId();
        title = data.getTitle();
        description = data.getDescription();
    }
}