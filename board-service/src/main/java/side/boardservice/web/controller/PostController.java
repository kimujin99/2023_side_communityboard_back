package side.boardservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import side.boardservice.domain.Message;
import side.boardservice.domain.StatusEnum;
import side.boardservice.domain.post.Post;
import side.boardservice.domain.post.PostDto;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.reply.ReplyDto;
import side.boardservice.domain.user.UserDto;
import side.boardservice.web.service.PostService;
import side.boardservice.web.service.UserService;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    //글목록(게시판 홈) 페이지로 이동
    @GetMapping
    public String getAllPosts(
            @PageableDefault(page = 0, size = 15) Pageable pageable,
            @RequestParam(value = "category-code", required = false) Long categoryCode,
            Principal principal,
            Model model){
        //공통 리스트 객체 생성
        List<PostDto.ListResponse> postList =  null;
        Page<PostDto.ListResponse> postListWithPaging =  null;

        //categoryCode 파라미터 값에 따라 분기
        if(categoryCode == null) {
            postList = postService.getPostList();
            postListWithPaging = postListToPageAndSetModel(model, pageable, postList);
        } else {
            postList = postService.getPostList(categoryCode);
            postListWithPaging = postListToPageAndSetModel(model, pageable, postList);
            //페이징을 위해 선택된 카테고리 코드도 같이 넘김
            model.addAttribute("selectedCategoryCode", categoryCode);
        }

        //카테고리 리스트 가져오기
        addCategoryListToModel(model);

        //인증된 사용자 정보 가져오기
        addPrincipalToModel(model, principal);

        model.addAttribute("postList", postListWithPaging);
        return "html/boards";
    }

    //글 작성 페이지로 이동
    @GetMapping("/write")
    public String writePosting(Principal principal, Model model){

        //인증된 사용자 정보 가져오기
        addPrincipalToModel(model, principal);

        //카테고리 리스트 가져오기
        addCategoryListToModel(model);

        return "html/postingForm";
    }

    //글 저장 - ajax
    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<Message> savePost(@RequestBody PostDto.Request dto, Principal principal){

        // 인증된 사용자 정보 넘기기
        String userEmailID = principal.getName();

        //글 저장
        Post savedPost = postService.savePost(userEmailID, dto);

        return ajaxResponseOk(savedPost);
    }

    //글 삭제 - ajax
    @ResponseBody
    @DeleteMapping("/{postingCode}")
    public ResponseEntity<Message> deletePosting(@PathVariable long postingCode) {
        //글 삭제
        postService.deletePost(postingCode);

        return ajaxResponseOk(null);
    }

    //글 상세 페이지로 이동
    @GetMapping("/{postingCode}")
    public String getPostDetail(@PathVariable long postingCode,
//                                     @RequestParam(name = "view-counting", required = false) Boolean viewCounting,
                                Principal principal,
                                Model model){
//        //리로드 시 조회수 카운팅 false
//        if(BooleanUtils.isTrue( viewCounting )) {
//            postService.plusViewCount(postingCode);
//        }

        //글 상세 & 댓글 리스트 가져오기
        PostDto.DetailResponse postDetail = postService.getPostDetail(postingCode);
        model.addAttribute("details", postDetail);

        //댓글 갯수 가져오기
        int cnt = postDetail.getReplies().size();
        model.addAttribute("cnt", cnt);

        //인증된 사용자 정보 가져오기
        addPrincipalToModel(model, principal);

        return "html/postingDetail";
    }

    //글 수정 페이지로 이동
    @GetMapping("/{postingCode}/edit")
    public String editForm(@PathVariable long postingCode, Principal principal, Model model) {
        //디테일 받아오기
        PostDto.DetailResponse details = postService.getPostDetail(postingCode);
        model.addAttribute("details", details);

        //인증된 사용자 정보 가져오기
        addPrincipalToModel(model, principal);

        //카테고리 리스트 뿌리기
        addCategoryListToModel(model);

        return "html/postingForm";
    }

    //글 수정 - ajax
    @ResponseBody
    @PostMapping("/{postingCode}/edit")
    public ResponseEntity<Message> editPosting(@PathVariable("postingCode") Long postingCode,
                                               @RequestBody PostDto.Request dto) {
        //글 수정
        Post savedPost = postService.eidtPost(postingCode, dto);

        return ajaxResponseOk(savedPost);
    }

    //댓글 삭제 - ajax
    @ResponseBody
    @DeleteMapping("/reply/{replyCode}")
    public ResponseEntity<Message> deleteReply(@PathVariable("replyCode") Long replyCode) {
        //댓글 삭제
        postService.deleteReply(replyCode);

        return ajaxResponseOk(null);
    }

    //댓글 저장 - ajax
    @ResponseBody
    @PostMapping("/{postingCode}/reply")
    public ResponseEntity<Message> saveReply(@PathVariable("postingCode") Long postingCode,
                                             @RequestBody ReplyDto.Request dto,
                                             Principal principal) {
        //개행문자 치환
        String replyContent = dto.getReplyContent();
        replyContent = replyContent.replace("\r\n", "<br>").replace("\n", "<br>");
        dto.setReplyContent(replyContent);

        // 인증된 사용자 정보 넘기기
        String userEmailID = principal.getName();

        //저장
        postService.saveReply(postingCode, userEmailID, dto);

        return ajaxResponseOk(null);
    }


    //카테고리 리스트 불러와서 Model에 넘기는 함수
    public void addCategoryListToModel(Model model) {
        List<Category> categoryList = postService.getcategoryList();
        model.addAttribute("categoryList", categoryList);
    }

    //인증된 사용자 정보 불러와서 Model에 넘기는 함수
    public void addPrincipalToModel(Model model, Principal principal) {
        UserDto.Response userInfo = userService.getUserInfo(principal);
        model.addAttribute("userInfo", userInfo);
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

    //List<PostListDTO>를 Page<PostListDTO>로 바꿔주는 함수
    public Page<PostDto.ListResponse> postListToPageAndSetModel(Model model, Pageable pageable, List<PostDto.ListResponse> postList) {
        //List를 Page로 변환
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), postList.size());

        final Page<PostDto.ListResponse> page = new PageImpl<>(postList.subList(start, end), pageable, postList.size());

        //페이징 정보
        int nowPage = page.getPageable().getPageNumber() + 1;

        int pageGroup = (int)Math.ceil((double)nowPage / 5.0);

        int startPage = ((pageGroup - 1) * 5) + 1;
        int endPage = Math.min(pageGroup * 5, page.getTotalPages());

        int totalPage = page.getTotalPages();

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);

//        log.info("pageGroup : {}", pageGroup);
//        log.info("nowPage : {}", nowPage);
//        log.info("startPage : {}", startPage);
//        log.info("endPage : {}", endPage);
//        log.info("totalPage : {}", totalPage);

        return page;
    }
}
