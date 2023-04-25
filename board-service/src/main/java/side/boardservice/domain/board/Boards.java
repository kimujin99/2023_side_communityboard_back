package side.boardservice.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "postings_tb")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_code")
    private Long postingCode;

    @Column(name = "category_code")
    private Long categoryCode;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "posting_title", length = 100)
    private String postingTitle;

    @Column(name = "posting_content", columnDefinition = "TEXT")
    private String postingContent;

    @Column(name = "view_count")
    private Integer viewCount;

    @CreationTimestamp
    @Column(name = "ins_time", updatable = false)
    private Timestamp insTime;

    @Column(name = "upd_time")
    private Timestamp updTime;

    public Boards(Long categoryCode, Long userCode, String postingTitle, String postingContent) {
        this.categoryCode = categoryCode;
        this.userCode = userCode;
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
    }
}
