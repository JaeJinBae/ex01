package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.BoardVO;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService service;

	@RequestMapping("/listAll")
	public void listAll(Model model) throws Exception {
		logger.info("listAll");
		service.listAll();

		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);

		// return "/board/listAll";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void regitserGet() {
		logger.info("register get");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String regitserPost(BoardVO board) throws Exception {
		logger.info("register post");

		service.regist(board);

		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(int bno, Model model) throws Exception {
		logger.info("read");
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGet(int bno, Model model) throws Exception {
		logger.info("modify get");
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO board, int bno, Model model) throws Exception {
		logger.info("modify post");
		service.modify(board);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);

		return "redirect:/board/read?bno=" + bno;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String delete(int bno) throws Exception {
		logger.info("delete");
		service.remove(bno);

		return "redirect:/board/listAll";

	}

}
