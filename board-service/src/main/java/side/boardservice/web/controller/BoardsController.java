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
import side.boardservice.domain.reply.dto.ReplyListDTO;
import side.boardservice.web.service.BoardsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    @Autowired
    BoardsService boardsService;

    //글목록(게시판 홈) 페이지로 이동
    @GetMapping
    public String getAllPostings(
            @RequestParam(value = "categoryCode", required = false) Long categoryCode,
            Model model){
        log.info("categoryCode : {}", categoryCode);
        List<BoardListDTO> boardList =  null;

        if(categoryCode == null || categoryCode == 0) {
            boardList = boardsService.getBoardList();
        } else {
            boardList = boardsService.getBoardList(categoryCode);
        }

        addCategoryListToModel(model);

        model.addAttribute("boardList", boardList);
        return "html/boards";
    }

    //글작성 페이지로 이동
    @GetMapping("/write")
    public String writePosting(Model model){
        addCategoryListToModel(model);
        model.addAttribute("dto", new BoardWriteDTO());
        return "html/postingForm";
    }

    //글 저장
    @PostMapping("/write")
    public String savePosting(@ModelAttribute("dto") BoardWriteDTO dto, Model model){

        Long categoryCode = dto.getCategoryCode();
        String postingTitle = dto.getPostingTitle();
        String postingContent = dto.getEditor4();

        //유효성 검사
        Map<String, String> errs = writingValidation(dto);

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
    public String showPostingDetails(@PathVariable long postingCode, Model model){
        //게시물 상세 가져오기
        BoardDetailDTO boardDetail = boardsService.getBoardDetail(postingCode);
        boardDetail.setPostingCode(postingCode);
        model.addAttribute("details", boardDetail);

        //댓글 리스트 가져오기
        List<ReplyListDTO> replyList = boardsService.getReplyList(postingCode);
        model.addAttribute("replyList", replyList);
        return "html/postingDetail";
    }

    //글 삭제
    @GetMapping("/{postingCode}/delete")
    public String deletePosting(@PathVariable long postingCode) {
        boardsService.deletePosting(postingCode);
        return "redirect:/boards";
    }

    //글 수정 페이지로 이동
    @GetMapping("/{postingCode}/edit")
    public String editForm(@PathVariable long postingCode, Model model) {
        //디테일 받아오기
        BoardDetailDTO details = boardsService.getBoardDetail(postingCode);
        Long selectedCategoryCode = boardsService.getCategoryCodeByName(details.getCategoryName());

        //BoardWriteDTO에 옮기기
        BoardWriteDTO dto = new BoardWriteDTO(postingCode, selectedCategoryCode, details.getPostingTitle(), details.getPostingContent());

        //카테고리 리스트 뿌리기
        addCategoryListToModel(model);

        model.addAttribute("dto", dto);
        model.addAttribute("selected", selectedCategoryCode);
        return "html/postingForm";
    }

    //글 수정
    @PostMapping("/{postingCode}/edit")
    public String editPosting(@PathVariable long postingCode,
                              @ModelAttribute("dto") BoardWriteDTO dto,
                              Model model) {
        log.info(" <---  컨트롤러 진입!  --->");

        Long categoryCode = dto.getCategoryCode();
        String postingTitle = dto.getPostingTitle();
        String postingContent = dto.getEditor4();

        //유효성 검사
        Map<String, String> errs = writingValidation(dto);

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
        boardsService.savePosting(postingCode, categoryCode, postingTitle, postingContent);
        return "redirect:/boards/{postingCode}";
    }

    //카테고리 리스트 불러와서 Model에 넘기는 함수
    public void addCategoryListToModel(Model model) {
        List<Category> categoryList = boardsService.getcategoryList();
        model.addAttribute("categoryList", categoryList);
    }

    //글 저장시 유효성 검사 함수
    public Map<String, String> writingValidation(BoardWriteDTO dto){
        Long categoryCode = dto.getCategoryCode();
        String postingTitle = dto.getPostingTitle();
        String postingContent = dto.getEditor4();

        //검증 오류 결과 보관
        Map<String, String> errs = new ConcurrentHashMap<>();

        //검증 로직
        if (!StringUtils.hasText(postingContent)) {
            errs.put("postingContent", "ERROR : 내용을 입력해주세요!");
        }
        if (!StringUtils.hasText(postingTitle)) {
            errs.put("postingTitle", "ERROR : 제목을 입력해주세요!");
        } else if (postingTitle.length() > 100) {
            errs.put("postingTitle", "ERROR : 제목은 100자 내로 입력해주세요!");
        }

        return errs;
    }
}
