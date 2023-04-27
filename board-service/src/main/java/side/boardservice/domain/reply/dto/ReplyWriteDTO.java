package side.boardservice.domain.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyWriteDTO {
    private Long postingCode;
    private String userNickname;
    private String userProfile;
    private String replyContent;
    private Integer scrollPosition;
}
