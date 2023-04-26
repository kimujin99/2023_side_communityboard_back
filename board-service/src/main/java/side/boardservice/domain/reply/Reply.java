package side.boardservice.domain.reply;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "replys_tb")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_code")
    private Long replyCode;

    @Column(name = "posting_code")
    private Long postingCode;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "emoji_code")
    private Long emojiCode;

    @Column(name = "reply_content", length = 500)
    private String replyContent;

    @CreationTimestamp
    @Column(name = "ins_time")
    private Timestamp insTime;
}
