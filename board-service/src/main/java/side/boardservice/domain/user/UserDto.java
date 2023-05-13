package side.boardservice.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private String userEmailId;
        private String userPassword;
        private String userNickname;
        private String userProfile;

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public User toEntity() {
            return User.builder()
                    .userEmailId(this.userEmailId)
                    .userPassword(this.userPassword)
                    .userNickname(this.userNickname)
                    .userRole("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long userCode;
        private String userNickname;
        private String userProfilePath;

        @Builder
        public Response(Long userCode, String userNickname, String userProfilePath) {
            this.userCode = userCode;
            this.userNickname = userNickname;
            this.userProfilePath = userProfilePath;
        }
    }
}
