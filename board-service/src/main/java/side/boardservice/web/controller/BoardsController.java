package side.boardservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import side.boardservice.domain.board.dto.BoardDetailDTO;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.board.dto.BoardWriteDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.web.service.BoardsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    @Autowired
    BoardsService boardsService;

    //글목록(게시판 홈) 페이지로 이동
    @GetMapping
    public String getAllPostings(Model model){
        List<BoardListDTO> boardList = boardsService.getBoardList();
        List<Category> categoryList = boardsService.categoryList();

        addCategoryListToModel(model);

        model.addAttribute("boardList", boardList);
        return "html/boards";
    }

    //글작성 페이지로 이동
    @GetMapping("/write")
    public String writePost(Model model){
        addCategoryListToModel(model);
        model.addAttribute("dto", new BoardWriteDTO());
        return "html/postingForm";
    }

    //글 저장
    @PostMapping("/write")
    public String savePost(@ModelAttribute("dto") BoardWriteDTO dto, Model model){

        Long categoryCode = dto.getCategoryCode();
        String postingTitle = dto.getPostingTitle();
        String postingContent = dto.getEditor4();

        //검증 오류 결과 보관
        Map<String, String> errs = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(postingContent)) {
            errs.put("postingContent", "ERROR : 내용을 입력해주세요!");
        }
        if (!StringUtils.hasText(postingTitle)) {
            errs.put("postingTitle", "ERROR : 제목을 입력해주세요!");
        }

        //실패 로직
        if(!errs.isEmpty()) {
            log.info("error : {}", errs);
            //에러 메시지
            model.addAttribute("errs", errs);
            //카테고리 리스트
            addCategoryListToModel(model);
            return "html/postingForm";
        }

        //성공 로직
        boardsService.savePosting(categoryCode, postingTitle, postingContent);
        return "redirect:/boards";
    }

    //글 상세 페이지로 이동
    @GetMapping("/{postingCode}")
    public String showDetails(@PathVariable long postingCode, Model model){
        BoardDetailDTO boardDetail = boardsService.getBoardDetail(postingCode);
        model.addAttribute("details", boardDetail);
        return "html/postingDetail";
    }

    //카테고리 리스트 불러와서 Model에 넘기는 함수
    public void addCategoryListToModel(Model model) {
        List<Category> categoryList = boardsService.getcategoryList();
        model.addAttribute("categoryList", categoryList);
    }
}
