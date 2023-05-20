package side.boardservice.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import side.boardservice.domain.post.Post;
import side.boardservice.domain.reply.Reply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@DynamicUpdate
@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_m_tb")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userCode")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "user_email_id", length = 50)
    private String userEmailId;

    @Column(name = "user_password", length = 64)
    private String userPassword;

    @Column(name = "user_nickname", length = 20)
    private String userNickname;

    @Column(name = "user_profile_path", length = 500)
    private String userProfilePath;

    @Column(name = "user_role")
    private String userRole;

    @CreationTimestamp
    @Column(name = "ins_time")
    private Timestamp insTime;

    //post와 양방향 관계 설정
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    //reply와 양방향 관계 설정
    @OneToMany(mappedBy = "user")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public User(String userEmailId, String userPassword, String userNickname, String userProfilePath, String userRole) {
        this.userEmailId = userEmailId;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userProfilePath = userProfilePath;
        this.userRole = userRole;
    }

    public void updateUser(String userNickname, String userProfilePath) {
        this.userNickname = userNickname;
        this.userProfilePath = userProfilePath;
    }

    public void updateUserNickname(String userNickname) {
        this.userNickname =userNickname;
    }
}
