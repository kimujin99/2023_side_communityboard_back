package side.boardservice.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

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
