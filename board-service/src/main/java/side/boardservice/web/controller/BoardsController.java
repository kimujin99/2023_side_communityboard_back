package side.boardservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import side.boardservice.domain.board.Boards;
import side.boardservice.domain.board.dto.BoardDetailDTO;
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
        List<BoardListDTO> boardList = boardsService.getBoardList();
        List<Category> categoryList = boardsService.categoryList();

        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryList", categoryList);
        return "html/boards";
    }

    @GetMapping("/write")
    public String writePost(Model model){
        List<Category> categoryList = boardsService.categoryList();
        model.addAttribute("categoryList", categoryList);
        return "html/postingForm";
    }

    @PostMapping("/write")
    public String savePost(@RequestParam("category_code") Long categoryCode,
                              @RequestParam("posting_title") String postingTitle,
                              @RequestParam("editor4") String postingContent){
        Integer rst = boardsService.savePosting(categoryCode, postingTitle, postingContent);
        System.out.println(rst);
        return "redirect:/boards";
    }

    @GetMapping("/{postingCode}")
    public String showDetails(@PathVariable long postingCode, Model model){
        BoardDetailDTO boardDetail = boardsService.getBoardDetail(postingCode);
        model.addAttribute("details", boardDetail);
        return "html/postingDetail";
    }
}
