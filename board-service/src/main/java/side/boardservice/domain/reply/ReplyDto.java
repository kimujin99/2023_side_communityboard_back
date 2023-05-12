package side.boardservice.domain.reply;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import side.boardservice.domain.post.Post;
import side.boardservice.domain.user.User;

import java.text.SimpleDateFormat;

public class ReplyDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private Post post;
        private User user;
        private String replyContent;

        public Reply toEntity() {
            return Reply.builder()
                    .post(this.post)
                    .user(this.user)
                    .replyContent(this.replyContent)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long replyCode;
        private String userNickname;
        private String userProfilePath;
        private String replyContent;
        private String insTime;

        public Response(Reply reply) {
            this.replyCode = reply.getReplyCode();
            this.userNickname = reply.getUser().getUserNickname();
            this.userProfilePath = reply.getUser().getUserProfilePath();
            this.replyContent = reply.getReplyContent();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            this.insTime = formatter.format(reply.getInsTime());
        }
    }

}
