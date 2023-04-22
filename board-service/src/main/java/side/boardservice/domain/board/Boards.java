package side.boardservice.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name = "postings_tb")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_code", nullable = false)
    private Long postingCode;

    @Column(name = "category_code", nullable = false)
    private Long categoryCode;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Column(name = "posting_title", length = 100, nullable = false)
    private String postingTitle;

    @Column(name = "posting_content", columnDefinition = "TEXT", nullable = false)
    private String postingContent;

    @Column(name = "view_count")
    private Integer viewCount;

    @CreationTimestamp
    @Column(name = "ins_time", nullable = false)
    private Timestamp insTime;

    @CreationTimestamp
    @Column(name = "upd_time", nullable = false)
    private Timestamp updTime;

    public Boards(Long categoryCode, Long userCode, String postingTitle, String postingContent) {
        this.categoryCode = categoryCode;
        this.userCode = userCode;
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
    }
}
