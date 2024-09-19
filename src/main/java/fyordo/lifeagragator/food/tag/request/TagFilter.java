package fyordo.lifeagragator.food.tag.request;

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
public class TagFilter {
    @Nullable
    private String search;

    private Boolean onlyMy;

    public TagFilter(Map<String, String> queryFilter){
        search = queryFilter.getOrDefault("search", null);
        onlyMy = Boolean.parseBoolean(queryFilter.getOrDefault("onlyMy", String.valueOf(true)));
    }
}
