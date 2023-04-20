package side.boardservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/postings")
@RequiredArgsConstructor
public class PostingController {

    @GetMapping
    public String postings(){
        return "html/boards.html";
    }
}
