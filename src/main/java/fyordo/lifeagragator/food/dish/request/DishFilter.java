package fyordo.lifeagragator.food.dish.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishFilter {
    @Nullable
    private String search;

    private List<Long> tagIds;

    private Boolean onlyMy = true;

    public DishFilter(Map<String, String> queryFilter){
        tagIds = new ArrayList<>();
        search = queryFilter.getOrDefault("search", null);
        onlyMy = Boolean.parseBoolean(queryFilter.get("onlyMy"));
    }
}
