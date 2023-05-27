package hello.board.controller;
import hello.board.entity.Board;
import hello.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writePro")
        public String boardWritePro(Board board, Model model) {

        boardService.write(board);

        model.addAttribute("message","글이 작성되었습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        List<Board> boardList = boardService.boardList();

        // 내림차순으로 정렬
        Collections.sort(boardList, Comparator.comparing(Board::getId).reversed());

        model.addAttribute("list", boardList);

        return "boardList";
    }

    @GetMapping("/board/view")
    public String boardView(Model model , Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardView(@PathVariable("id") Integer id , Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id , Board board, Model model) throws Exception {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp);

        model.addAttribute("message","글이 수정되었습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }

}
