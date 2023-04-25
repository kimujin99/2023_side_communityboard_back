package side.boardservice.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardWriteDTO {
    private Long postingCode;
    private Long categoryCode;
    private String userNickname;
    private String postingTitle;
    private String editor4;

    public BoardWriteDTO(Long categoryCode, String postingTitle, String editor4) {
        this.categoryCode = categoryCode;
        this.postingTitle = postingTitle;
        this.editor4 = editor4;
    }

    public BoardWriteDTO(Long postingCode,Long categoryCode, String postingTitle, String editor4) {
        this.postingCode = postingCode;
        this.categoryCode = categoryCode;
        this.postingTitle = postingTitle;
        this.editor4 = editor4;
    }
}
