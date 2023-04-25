package side.boardservice.domain.category.dto;

import lombok.Getter;
import side.boardservice.domain.category.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CategoryListDTO {
    private Map<Long, String> categoryMap = new HashMap<>();

    public void setCategoryListToMap(List<Category> list) {
        Map<Long, String> map = new ConcurrentHashMap<>();

        for(Category category : list) {
            Long categoryCode = category.getCategoryCode();
            String categoryName = category.getCategoryName();

            map.put(categoryCode, categoryName);
        }

        this.categoryMap = map;
    }
}
