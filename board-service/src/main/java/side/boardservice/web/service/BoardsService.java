package side.boardservice.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.BoardsRepository;
import side.boardservice.domain.board.dto.BoardDetailDTO;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.category.CategoryRepository;
import side.boardservice.domain.user.User;
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

    public String getCategoryName(Long categoryCode) {
        Category category = categoryRepository.findByCategoryCode(categoryCode);

        return category.getCategoryName();
    }

    public String getUserNickname(Long userCode) {
        User user = userRepository.findByUserCode(userCode);

        return user.getUserNickname();
    }

    //게시물 리스트 가져오기
    public List<BoardListDTO> getBoardList() {
        List<Boards> boardsList = boardsRepository.findAll(Sort.by(Sort.Direction.DESC, "viewCount"));
        List<BoardListDTO> boardListDTOS = BoardsToDTOS(boardsList);

        return boardListDTOS;
    }

    public List<BoardListDTO> getBoardList(Long categoryCode) {
        List<Boards> boardsList = boardsRepository.findByCategoryCode(categoryCode, Sort.by(Sort.Direction.DESC, "viewCount"));
        List<BoardListDTO> boardListDTOS = BoardsToDTOS(boardsList);

        return boardListDTOS;
    }

    //게시물 저장하기
    public Integer savePosting(Long categoryCode, String postingTitle, String postingContent) {
        Boards boards = new Boards(categoryCode, 1L, postingTitle, postingContent);

        boardsRepository.save(boards);

        return 1;
    }

    //카테고리 리스트 가져오기
    public List<Category> getcategoryList() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryCode"));
    }

    //게시물 상세 가져오기
    public BoardDetailDTO getBoardDetail(Long postingCode) {
        Boards board = boardsRepository.findByPostingCode(postingCode);

        //조회수 늘리기
        Integer viewCount = board.getViewCount() + 1;
        board.setViewCount(viewCount);
        boardsRepository.save(board);

        //카테고리코드로 이름 가져오기
        String categoryName = getCategoryName(board.getCategoryCode());
        //유저코드로 닉네임 가져오기
        String userNickname = getUserNickname(board.getUserCode());

        BoardDetailDTO dto = BoardDetailDTO.builder()
                .categoryName(categoryName)
                .userNickname(userNickname)
                .postingTitle(board.getPostingTitle())
                .postingContent(board.getPostingContent())
                .viewCount(board.getViewCount())
                .build();

        //insTime 설정
        dto.timeSetting(dto, board.getInsTime(), board.getUpdTime());

        return dto;
    }

    public List<BoardListDTO> BoardsToDTOS(List<Boards> boardsList) {
        List<BoardListDTO> boardListDTOS = new ArrayList<>();

        for (Boards boards : boardsList) {
            //카테고리코드로 이름 가져오기
            String categoryName = getCategoryName(boards.getCategoryCode());
            //유저코드로 닉네임 가져오기
            String userNickname = getUserNickname(boards.getUserCode());

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

}
