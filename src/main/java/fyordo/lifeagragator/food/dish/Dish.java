package fyordo.lifeagragator.food.dish;

import fyordo.lifeagragator.food.dish.request.DishCreateRequest;
import fyordo.lifeagragator.food.dish.request.DishUpdateRequest;
import fyordo.lifeagragator.food.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dish")
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 31, nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "receipt", nullable = true, length = 2047)
    private String receipt;

    @Column(name = "created_user_id", nullable = false)
    private Long createdUserId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(name = "dish_tags",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "dish", orphanRemoval = true)
    private Set<DishIngredient> dishIngredients = new LinkedHashSet<>();

    public Dish(DishCreateRequest data){
        title = data.getTitle();
        description = data.getDescription();
        receipt = data.getReceipt();
    }

    public Dish(DishUpdateRequest data){
        id = data.getId();
        title = data.getTitle();
        description = data.getDescription();
        receipt = data.getReceipt();
    }
}