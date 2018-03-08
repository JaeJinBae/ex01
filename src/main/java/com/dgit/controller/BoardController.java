package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
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
	
	@RequestMapping("/listCri")
	public void listCri(Criteria cri,Model model) throws Exception{
		logger.info("listCri");
		List<BoardVO> list=service.listCriteria(cri);
		model.addAttribute("list",list);
	}
	
	@RequestMapping("/listPage")
	public void listPage(Criteria cri,Model model) throws Exception{
		logger.info("litPage");
		List<BoardVO> list=service.listCriteria(cri);
		
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(1024);
		
		/*int totalCount=service.listCountCriteria();
		System.out.println(service.listCountCriteria());*/
		
		model.addAttribute("list",list);
		model.addAttribute("pageMaker",pageMaker);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void regitserGet() {
		logger.info("register get");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String regitserPost(BoardVO board) throws Exception {
		logger.info("register post");

		service.regist(board);

		return "redirect:/board/listPage";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(int bno, Model model) throws Exception {
		logger.info("read");
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void readPage(int bno, String i, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		logger.info("readPage");
		if(i==null){
			service.updateCnt(bno);
		}
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGet(int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		logger.info("modify get");
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO board, Criteria cri, RedirectAttributes rtts) throws Exception {
		logger.info("modify post");
		service.modify(board);
		
		rtts.addAttribute("perPageNum",cri.getPerPageNum());
		rtts.addAttribute("page",cri.getPage());
		rtts.addAttribute("bno", board.getBno());
		

		return "redirect:/board/readPage?i=1";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String delete(int bno, Criteria cri,RedirectAttributes rtts) throws Exception {
		logger.info("delete");
		service.remove(bno);
		
		rtts.addAttribute("perPageNum",cri.getPerPageNum());
		rtts.addAttribute("page",cri.getPage());

		return "redirect:/board/listPage";

	}

}
