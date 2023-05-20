package side.boardservice.web.controller;

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
import side.boardservice.domain.post.PostDto;
import side.boardservice.domain.user.User;
import side.boardservice.domain.user.UserDto;
import side.boardservice.web.service.S3FileService;
import side.boardservice.web.service.UserService;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    S3FileService s3FileService;

    //로그인 페이지로 이동
    @GetMapping({"", "/", "/login"})
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {

        // 로그인 실패 메시지
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "html/login";
    }

    //회원가입 페이지로 이동
    @GetMapping("/signup")
    public String signupPage() {
        return "html/signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody UserDto.Request dto) {

        userService.saveUser(dto);

        return fetchResponseOk(null);
    }

    //회원가입 - fetch API
    @ResponseBody
    @PostMapping("/email-check.do")
    public ResponseEntity<Message> emailDuplicateCheck(@RequestBody UserDto.Request dto) {
        Boolean emailedDuplicateChecking = userService.emailDuplicateCheck(dto);

        return fetchResponseOk(emailedDuplicateChecking);
    }

    //마이 페이지로 이동
    @GetMapping("/boards/my")
    public String myPage(@PageableDefault(page = 0, size = 15) Pageable pageable,
                         Principal principal, Model model) {
        //인증된 사용자 정보 가져오기
        UserDto.MyPageResponse userInfo = userService.getMyPageUserInfo(principal);
        model.addAttribute("userInfo", userInfo);

        Page<PostDto.ListResponse> postListWithPaging = postListToPageAndSetModel(model, pageable, userInfo.getPosts());
        model.addAttribute("postList", postListWithPaging);

        //사용자가 작성한 글,댓글 수 넘기기
        Integer myPostingsCnt = userInfo.getPosts().size();
        Integer myRepliesCnt = userInfo.getReplies().size();
        if(myPostingsCnt == null) {
            myPostingsCnt = 0;
        }
        if(myRepliesCnt == null) {
            myRepliesCnt = 0;
        }
        model.addAttribute("myPostingsCnt", myPostingsCnt);
        model.addAttribute("myRepliesCnt", myRepliesCnt);

        return "html/myPage";
    }

    @ResponseBody
    @PostMapping("/boards/my/edit")
    public ResponseEntity<Message> updateUser(@RequestBody UserDto.Request dto) {

        User user = userService.getUserByUserCode(dto.getUserCode());

        if(dto.getUserProfile() != null) {
            //기존 프로필 사진 삭제(unknown 제외)
            s3FileService.s3FileDelete(user.getUserProfilePath(), "prf");
        }

        userService.editUser(user, dto);

        return fetchResponseOk(null);
    }

    //List<PostListDTO>를 Page<PostListDTO>로 바꿔주는 함수
    public Page<PostDto.ListResponse> postListToPageAndSetModel(Model model, Pageable pageable, List<PostDto.ListResponse> postList) {
        //List를 Page로 변환
        Integer postsSize = postList.size();
        log.info("postsSize : {}", postsSize);

        if(postsSize == 0 || postsSize == null) {
            postsSize = 1;
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), postList.size());


        final Page<PostDto.ListResponse> page = new PageImpl<>(postList.subList(start, end), pageable, postList.size());

        //페이징 정보
        int nowPage = page.getPageable().getPageNumber() + 1;
        int pageGroup = (int)Math.ceil((double)nowPage / 5.0);
        int startPage = ((pageGroup - 1) * 5) + 1;

        int totalPages = page.getTotalPages();
        if(totalPages == 0) {
            totalPages = 1;
        }
        int endPage = Math.min(pageGroup * 5, totalPages);
        log.info("endPage : {}", endPage);
        log.info("totalPages : {}", totalPages);

        int totalPage = totalPages;

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

    //JSON으로 성공 메시지 뿌려주는 함수
    public ResponseEntity<Message> fetchResponseOk(Object data) {
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
