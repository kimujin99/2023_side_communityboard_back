package side.boardservice.domain.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.reply.ReplyDto;
import side.boardservice.domain.user.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private Long postingCode;
        private Category category;
        private User user;
        private String postingTitle;
        // editor4 = postingContent
        private String editor4;

        public void setUser(User user) {
            this.user = user;
        }

        public Post toEntity() {
            return Post.builder()
                    .postingCode(this.postingCode)
                    .category(this.category)
                    .user(this.user)
                    .postingTitle(this.postingTitle)
                    .postingContent(this.editor4)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ListResponse {
        private Long postingCode;
        private String categoryName;
        private String userNickname;
        private String postingTitle;
        private Integer viewCount;
        private String insTime;

        public ListResponse(Post post) {
            this.postingCode = post.getPostingCode();
            this.categoryName = post.getCategory().getCategoryName();
            this.userNickname = post.getUser().getUserNickname();
            this.postingTitle = post.getPostingTitle();
            this.viewCount = post.getViewCount();
            //시간 포맷 변경
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            this.insTime = formatter.format(post.getInsTime());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailResponse {
        private Long postingCode;
        private Long categoryCode;
        private String categoryName;
        private String userNickname;
        private String postingTitle;
        private String postingContent;
        private Integer viewCount;
        private String insTime, updTime;
        private List<ReplyDto.Response> replies;

        public DetailResponse(Post post) {
            this.postingCode = post.getPostingCode();
            this.categoryCode = post.getCategory().getCategoryCode();
            this.categoryName = post.getCategory().getCategoryName();
            this.userNickname = post.getUser().getUserNickname();
            this.postingTitle = post.getPostingTitle();
            this.postingContent = post.getPostingContent();
            this.viewCount = post.getViewCount();
            //시간 포맷 변경
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            this.insTime = formatter.format(post.getInsTime());
            this.updTime = formatter.format(post.getUpdTime());
            //List<Reply>를 List<ReplyDto.Response>로 변환
            this.replies = post.getReplies().stream().map(ReplyDto.Response::new).collect(Collectors.toList());
        }
    }

}
