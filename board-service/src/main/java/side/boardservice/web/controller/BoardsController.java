package side.boardservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.dto.BoardListDTO;
import side.boardservice.domain.category.Category;
import side.boardservice.web.service.BoardsService;

import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    @Autowired
    BoardsService boardsService;

    @GetMapping
    public String getAllPostings(Model model){
        List<BoardListDTO> boardList = boardsService.getboardList();
        List<Category> categoryList = boardsService.categoryList();

        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryList", categoryList);
        return "html/boards.html";
    }

    @GetMapping("/write")
    public String writePost(Model model){
        List<Category> categoryList = boardsService.categoryList();
        model.addAttribute("categoryList", categoryList);
        return "html/postingForm.html";
    }

    @PostMapping("/write")
    public String savePost(@RequestParam("category_code") Long categoryCode,
                              @RequestParam("posting_title") String postingTitle,
                              @RequestParam("editor4") String postingContent){
        Integer rst = boardsService.savePosting(categoryCode, postingTitle, postingContent);
        System.out.println(rst);
        return "redirect:/boards";
    }
}
