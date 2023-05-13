package side.boardservice.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping({"", "/", "/login"})
    public String loginPage() {
        return "html/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "html/signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody UserDto.Request dto) {

        userService.saveUser(dto);

        return ajaxResponseOk(null);
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
