package side.boardservice.domain.reply;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import side.boardservice.domain.post.Post;
import side.boardservice.domain.user.User;

import java.sql.Timestamp;

@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "replys_tb")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_code")
    private Long replyCode;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "posting_code")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Column(name = "emoji_code")
    private Long emojiCode;

    @Column(name = "reply_content", length = 500)
    private String replyContent;

    @CreationTimestamp
    @Column(name = "ins_time")
    private Timestamp insTime;

    @Builder
    public Reply(Post post, User user, Long emojiCode, String replyContent) {
        this.post = post;
        this.user = user;
        this.emojiCode = emojiCode;
        this.replyContent = replyContent;
    }

}
