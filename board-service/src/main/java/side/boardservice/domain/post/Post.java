package side.boardservice.domain.post;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.reply.Reply;
import side.boardservice.domain.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "postings_tb")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "postingCode")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_code")
    private Long postingCode;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Column(name = "posting_title")
    private String postingTitle;

    @Column(name = "posting_content", columnDefinition = "TEXT")
    private String postingContent;

    @Column(name = "view_count")
    private Integer viewCount;

    @CreationTimestamp
    @Column(name = "ins_time")
    private Timestamp insTime;

    @UpdateTimestamp
    @Column(name = "upd_time")
    private Timestamp updTime;

    //reply와 양방향 관계 설정
    @OneToMany(mappedBy = "post")
    @OrderBy("ins_time")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Post(Long postingCode, Category category, User user, String postingTitle, String postingContent) {
        this.postingCode = postingCode;
        this.category = category;
        this.user = user;
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
    }

    public void updatePost(Category category, String postingTitle, String postingContent) {
        this.category = category;
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
    }
}
