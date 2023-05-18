package side.boardservice.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import side.boardservice.domain.Message;
import side.boardservice.domain.StatusEnum;
import side.boardservice.domain.user.UserDto;
import side.boardservice.web.service.UserService;

import java.nio.charset.Charset;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

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
