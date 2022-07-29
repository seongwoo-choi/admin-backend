//package kr.co.pincar.controller;
//
//
//import kr.co.pincar.jpa.dto.BoardReq;
//import kr.co.pincar.jpa.entity.Board;
//import kr.co.pincar.jpa.repository.BoardRepository;
//import kr.co.pincar.mybatis.dto.BoardDto;
//import kr.co.pincar.mybatis.mapper.BoardMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@Controller
//@RequestMapping("/test")
//public class TestController {
//
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Resource
//    private BoardMapper boardMapper;
//
//    @Resource
//    BoardRepository boardRepository;
//
//
//    @GetMapping("/view")
//    public String view(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        List<BoardDto> result = boardMapper.findAll();
//        model.addAttribute("result", result);
//        return "test";
//    }
//
//    @ResponseBody
//    @GetMapping("/mybatis/find-all")
//    public List<BoardDto> mybatisFindAll(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        List<BoardDto> result = boardMapper.findAll();
//        return result;
//    }
//
//    @ResponseBody
//    @GetMapping("/mybatis/find-by-id")
//    public BoardDto mybatisFindById(
//            @RequestParam("id") Long id,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        BoardDto result = boardMapper.findById(id);
//        return result;
//    }
//
//    @ResponseBody
//    @GetMapping("/mybatis/find-by-writer")
//    public BoardDto mybatisFindByWriter(
//            @RequestParam("writer") String writer,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        BoardDto result = boardMapper.findByWriter(writer);
//        return result;
//    }
//
//    @ResponseBody
//    @PostMapping("/mybatis/save")
//    public int mybatisSave(
//            @RequestBody BoardDto board,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        int id = boardMapper.save(board);
//        return id;
//    }
//
//
//    @ResponseBody
//    @GetMapping("/jpa/find-all")
//    public List<Board> jpaFindAll(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        List<Board> result = boardRepository.findAll();
//        return result;
//    }
//
//    @ResponseBody
//    @GetMapping("/jpa/find-by-id")
//    public Board jpaFindById(
//            @RequestParam("id") Long id,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        Board result = boardRepository.findById(id).orElse(null);
//        return result;
//    }
//
//    @ResponseBody
//    @GetMapping("/jpa/find-by-writer")
//    public Board jpaFindByWriter(
//            @RequestParam("writer") String writer,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        Board result = boardRepository.findByWriter(writer);
//        return result;
//    }
//
//    @ResponseBody
//    @PostMapping("/jpa/save")
//    public Board jpaSave(
//            @RequestBody BoardReq boardReq,
//            Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//
//
//        Board board = new Board(boardReq.getContent(), boardReq.getTitle(), boardReq.getWriter());
//
//        Board result = boardRepository.save(board);
//
//        return result;
//    }
//
//
//}
