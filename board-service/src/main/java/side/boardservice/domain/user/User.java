package side.boardservice.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@DynamicUpdate
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_m_tb")
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

    @Builder
    public User(String userEmailId, String userPassword, String userNickname, String userProfilePath, String userRole) {
        this.userEmailId = userEmailId;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userProfilePath = userProfilePath;
        this.userRole = userRole;
    }

    public void updateUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void updateUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void updateUserProfileImg(String userProfilePath) {
        this.userProfilePath = userProfilePath;
    }
}
