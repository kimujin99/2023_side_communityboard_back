package side.boardservice.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "postings_tb")
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_code", nullable = false)
    private Long postingCode;

    @Column(name = "category_name", nullable = false)
    private Long categoryCode;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Column(name = "posting_title", length = 100, nullable = false)
    private String postingTitle;

    @Column(name = "posting_content", columnDefinition = "TEXT", nullable = false)
    private String postingContent;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "ins_time", nullable = false)
    private Timestamp insTime;

    @Column(name = "upd_time", nullable = false)
    private Timestamp updTime;

    public Posting() {
    }

    public Posting(Long categoryCode, Long userCode, String postingTitle, String postingContent) {
        this.categoryCode = categoryCode;
        this.userCode = userCode;
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
    }

}
