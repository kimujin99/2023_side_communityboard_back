package side.boardservice.domain.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class ReplyListDTO {
    private Long replyCode;
    private String userNickname;
    private String userProfile;
    private String replyContent;
    private String insTime;

    public void timeSetting(ReplyListDTO replyListDTO, Timestamp insTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        replyListDTO.setInsTime(format.format(insTime));
    }
}
