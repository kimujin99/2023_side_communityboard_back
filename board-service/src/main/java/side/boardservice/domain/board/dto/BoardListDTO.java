package side.boardservice.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class BoardListDTO {
    private Long postingCode;
    private String categoryName;
    private String userNickname;
    private String postingTitle;
    private Integer viewCount;
    private String insTime;

    public void timeSetting(BoardListDTO boardListDTO, Timestamp insTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        boardListDTO.setInsTime(format.format(insTime));
    }

}
