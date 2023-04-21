package side.boardservice.domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "category_m_tb")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code", nullable = false)
    private Long categoryCode;
    @Column(name = "category_name", length = 100, nullable = false)
    private String categoryName;
    @Column(name = "ins_time", nullable = false)
    private Timestamp insTime;
    @Column(name = "upd_time", nullable = false)
    private Timestamp updTime;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
