package side.boardservice.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class BoardDetailDTO {
    private Long postingCode;
    private String categoryName;
    private String userNickname;
    private String postingTitle;
    private String postingContent;
    private Integer viewCount;
    private String insTime;
    private String updTime;

    public void timeSetting(BoardDetailDTO boardDetailDTO, Timestamp insTime,  Timestamp updTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        boardDetailDTO.setInsTime(format.format(insTime));
        boardDetailDTO.setUpdTime(format.format(updTime));
    }

}