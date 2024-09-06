package fyordo.lifeagragator.food.ingredient;

import fyordo.lifeagragator.food.dish.DishIngredient;
import fyordo.lifeagragator.food.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 31, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = true)
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

}