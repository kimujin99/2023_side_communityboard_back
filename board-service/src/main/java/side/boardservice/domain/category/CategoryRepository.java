package side.boardservice.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryCode(Long categoryCode);
    Category findByCategoryName(String categoryName);
}