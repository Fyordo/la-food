package fyordo.lifeagragator.food.dish;

import fyordo.lifeagragator.food.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 31, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = true)
    private String description;

    @Lob
    @Column(name = "receipt", nullable = true)
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

}