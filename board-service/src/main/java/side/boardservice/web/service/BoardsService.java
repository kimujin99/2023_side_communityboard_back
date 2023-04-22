package side.boardservice.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.BoardsRepository;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.category.CategoryRepository;
import side.boardservice.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BoardsRepository boardsRepository;

    //board 리스트 가져오기
    public List<BoardListDTO> getboardList() {
        List<Boards> boardsList = boardsRepository.findAll(Sort.by(Sort.Direction.ASC, "postingCode"));
        List<BoardListDTO> boardListDTOS = new ArrayList<>();

        for (Boards boards : boardsList) {
            //카테고리코드로 이름 가져오기
            String categoryName = categoryRepository.findByCategoryCode(boards.getCategoryCode()).getCategoryName();
            //유저코드로 닉네임 가져오기
            String userNickname = userRepository.findByUserCode(boards.getUserCode()).getUserNickname();

            //dto 생성
            BoardListDTO dto = BoardListDTO.builder()
                    .postingCode(boards.getPostingCode())
                    .categoryName(categoryName)
                    .userNickname(userNickname)
                    .postingTitle(boards.getPostingTitle())
                    .viewCount(boards.getViewCount())
                    .build();

            //insTime 설정
            dto.timeSetting(dto, boards.getInsTime());
            //dto를 리스트에 추가
            boardListDTOS.add(dto);

        }

        return boardListDTOS;
    }

    //카테고리 리스트 가져오기
    public Integer savePosting(Long categoryCode, String postingTitle, String postingContent) {
        Boards boards = new Boards(categoryCode, 1L, postingTitle, postingContent);

        boardsRepository.save(boards);

        return 1;
    }

    //카테고리 리스트 가져오기
    public List<Category> categoryList() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryCode"));
    }
}
