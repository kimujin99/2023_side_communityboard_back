package side.boardservice.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.BoardsRepository;
import side.boardservice.domain.board.dto.BoardDetailDTO;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.category.CategoryRepository;
import side.boardservice.domain.category.dto.CategoryListDTO;
import side.boardservice.domain.user.User;
import side.boardservice.domain.user.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BoardsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BoardsRepository boardsRepository;

    public String getCategoryNameById(Long categoryCode) {
        Category category = categoryRepository.findByCategoryCode(categoryCode);

        return category.getCategoryName();
    }

    public Long getCategoryCodeByName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        return category.getCategoryCode();
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

    //게시물 저장하기(신규)
    public void savePosting(Long categoryCode, String postingTitle, String postingContent) {
        Boards boards = new Boards(categoryCode, 1L, postingTitle, postingContent);

        boardsRepository.save(boards);
    }

    //게시물 저장하기(수정)
    public void savePosting(Long postingCode, Long categoryCode, String postingTitle, String postingContent) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Boards boards = boardsRepository.findByPostingCode(postingCode);
        boards.setPostingCode(postingCode);
        boards.setCategoryCode(categoryCode);
        boards.setPostingTitle(postingTitle);
        boards.setPostingContent(postingContent);
        boards.setUpdTime(now);

        log.info(" <---  서비스 진입!  --->");

        boardsRepository.save(boards);
    }

    public void deletePosting(Long postingCode) {
        boardsRepository.deleteById(postingCode);
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
        String categoryName = getCategoryNameById(board.getCategoryCode());
        //유저코드로 닉네임 가져오기
        String userNickname = getUserNickname(board.getUserCode());

        BoardDetailDTO dto = BoardDetailDTO.builder()
                .postingCode(postingCode)
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

        //카테고리리스트 가져오기
        List<Category> categoryList = categoryRepository.findAll();
        //Map으로 저장
        CategoryListDTO categoryListDTO = new CategoryListDTO();
        categoryListDTO.setCategoryListToMap(categoryList);

        for (Boards boards : boardsList) {
            //카테고리코드로 이름 가져오기
            String categoryName = categoryListDTO.getCategoryMap().get(boards.getCategoryCode());
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
