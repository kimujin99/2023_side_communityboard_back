package side.boardservice.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.boardservice.domain.post.PostDto;
import side.boardservice.domain.reply.ReplyDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private Long userCode;
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
                    .userProfilePath(this.userProfile)
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

        public Response(User user) {
            this.userCode = user.getUserCode();
            this.userNickname = user.getUserNickname();
            this.userProfilePath = user.getUserProfilePath();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyPageResponse {
        private Long userCode;
        private String userEmailId;
        private String userNickname;
        private String userProfilePath;
        private List<PostDto.ListResponse> posts;
        private List<ReplyDto.Response> replies;

        public MyPageResponse(User user) {
            this.userCode = user.getUserCode();
            this.userEmailId = user.getUserEmailId();
            this.userNickname = user.getUserNickname();
            this.userProfilePath = user.getUserProfilePath();
            //List<Reply>를 List<ReplyDto.Response>로 변환
            this.posts = user.getPosts().stream().map(PostDto.ListResponse::new).collect(Collectors.toList());
            //List<Reply>를 List<ReplyDto.Response>로 변환
            this.replies = user.getReplies().stream().map(ReplyDto.Response::new).collect(Collectors.toList());
        }
    }
}
