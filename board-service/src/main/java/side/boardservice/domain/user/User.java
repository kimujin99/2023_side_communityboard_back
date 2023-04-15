package side.boardservice.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "user_m_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Column(name = "user_email_id", length = 50, nullable = false)
    private String userEmailId;

    @Column(name = "user_password", length = 64, nullable = false)
    private String userPassword;

    @Column(name = "user_nickname", length = 20, nullable = false)
    private String userNickname;

    @Column(name = "user_profile_path", length = 500, nullable = false)
    private String userProfilePath;

    @Column(name = "user_role", length = 10, nullable = false)
    private String userRole;

    @Column(name = "user_role", nullable = false)
    @CreationTimestamp
    private Timestamp insTime;

    public User() {
    }

    public User(String userEmailId, String userPassword, String userNickname, String userProfilePath, String userRole) {
        this.userEmailId = userEmailId;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userProfilePath = userProfilePath;
        this.userRole = userRole;
    }
}
