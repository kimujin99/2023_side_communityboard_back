package side.boardservice.domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "category_m_tb")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code", nullable = false)
    private Long categoryCode;
    @Column(name = "category_name", length = 100, nullable = false)
    private String categoryName;
}
