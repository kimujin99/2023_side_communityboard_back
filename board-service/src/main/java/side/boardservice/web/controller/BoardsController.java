package side.boardservice.web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import side.boardservice.domain.Message;
import side.boardservice.domain.StatusEnum;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.dto.BoardDetailDTO;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.board.dto.BoardWriteDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.reply.dto.ReplyListDTO;
import side.boardservice.domain.reply.dto.ReplyWriteDTO;
import side.boardservice.web.service.BoardsService;

import java.nio.charset.Charset;
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
            @RequestParam(value = "category-code", required = false) Long categoryCode,
            Model model){
        //공통 리스트 객체 생성
        List<BoardListDTO> boardList =  null;

        //categoryCode 파라미터 값에 따라 분기
        if(categoryCode == null || categoryCode == 0) {
            boardList = boardsService.getBoardList();
        } else {
            boardList = boardsService.getBoardList(categoryCode);
        }

        //카테고리 리스트 가져오기
        addCategoryListToModel(model);

        model.addAttribute("boardList", boardList);
        return "html/boards";
    }

    //글작성 페이지로 이동
    @GetMapping("/write")
    public String writePosting(Model model){
        //카테고리 리스트 가져오기
        addCategoryListToModel(model);
        model.addAttribute("dto", new BoardWriteDTO());
        return "html/postingForm";
    }

    //글 저장 - ajax
    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<Message> savePosting(@RequestBody BoardWriteDTO boardWriteDTO){

        Long categoryCode = boardWriteDTO.getCategoryCode();
        String postingTitle = boardWriteDTO.getPostingTitle();
        String postingContent = boardWriteDTO.getEditor4();

        //글 저장
        Boards savedBoards = boardsService.savePosting(categoryCode, postingTitle, postingContent);

        return ajaxResponseOk(savedBoards);
    }

    //글 상세 페이지로 이동
    @GetMapping("/{postingCode}")
    public String showPostingDetails(@PathVariable long postingCode,
                                     @RequestParam(name = "view-counting", required = false) Boolean viewCounting,
                                     Model model){
        //리로드 시 조회수 카운팅 false
        if(BooleanUtils.isTrue( viewCounting )) {
            boardsService.plusViewCount(postingCode);
        }

        //글 상세 가져오기
        BoardDetailDTO boardDetail = boardsService.getBoardDetail(postingCode);
        model.addAttribute("details", boardDetail);

        //댓글 리스트 가져오기
        List<ReplyListDTO> replyList = boardsService.getReplyList(postingCode);
        model.addAttribute("replyList", replyList);

        //댓글 갯수 가져오기
        int cnt = replyList.size();
        model.addAttribute("cnt", cnt);

        //댓글 작성용 모델
        ReplyWriteDTO replyForm = new ReplyWriteDTO();
        replyForm.setPostingCode(postingCode);
        model.addAttribute("replyForm", replyForm);

        return "html/postingDetail";
    }

    //글 삭제 - ajax
    @ResponseBody
    @DeleteMapping("/{postingCode}")
    public ResponseEntity<Message> deletePosting(@PathVariable long postingCode) {
        //글 삭제
        boardsService.deletePosting(postingCode);

        return ajaxResponseOk(null);
    }

    //글 수정 페이지로 이동
    @GetMapping("/{postingCode}/edit")
    public String editForm(@PathVariable long postingCode, Model model) {
        //디테일 받아오기
        BoardDetailDTO details = boardsService.getBoardDetail(postingCode);

        //BoardWriteDTO에 옮기기
        BoardWriteDTO dto = new BoardWriteDTO(postingCode, details.getCategoryCode(), details.getPostingTitle(), details.getPostingContent());

        //카테고리 리스트 뿌리기
        addCategoryListToModel(model);

        model.addAttribute("dto", dto);
        return "html/postingForm";
    }

    //글 수정 - ajax
    @ResponseBody
    @PostMapping("/{postingCode}/edit")
    public ResponseEntity<Message> editPosting(@PathVariable long postingCode,
                                               @RequestBody BoardWriteDTO boardWriteDTO) {
        Long categoryCode = boardWriteDTO.getCategoryCode();
        String postingTitle = boardWriteDTO.getPostingTitle();
        String postingContent = boardWriteDTO.getEditor4();

        //글 수정
        Boards savedBoards = boardsService.savePosting(postingCode, categoryCode, postingTitle, postingContent);
        return ajaxResponseOk(savedBoards);
    }

    //댓글 삭제 - ajax
    @ResponseBody
    @DeleteMapping("/reply/{replyCode}")
    public ResponseEntity<Message> deleteReply(@PathVariable("replyCode") Long replyCode) {
        //댓글 삭제
        boardsService.deleteReply(replyCode);

        return ajaxResponseOk(null);
    }

    //댓글 저장 - ajax
    @ResponseBody
    @PostMapping("/{postingCode}/reply")
    public ResponseEntity<Message> saveReply(@PathVariable("postingCode") Long postingCode,
                                             @RequestBody ReplyWriteDTO replyWriteDTO) {
        //개행문자 치환
        String replyContent = replyWriteDTO.getReplyContent();
        replyContent = replyContent.replace("\r\n", "<br>").replace("\n", "<br>");
        replyWriteDTO.setReplyContent(replyContent);

        //저장
        boardsService.saveReply(replyWriteDTO);

        return ajaxResponseOk(null);
    }


    //카테고리 리스트 불러와서 Model에 넘기는 함수
    public void addCategoryListToModel(Model model) {
        List<Category> categoryList = boardsService.getcategoryList();
        model.addAttribute("categoryList", categoryList);
    }

    //JSON으로 성공 메시지 뿌려주는 함수
    public ResponseEntity<Message> ajaxResponseOk(Object data) {
        //응답 객체
        Message msg = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //메시지 설정
        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공");
        msg.setData(data);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
}
