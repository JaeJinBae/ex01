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
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/")
public class SearchBoardController {
	private static final Logger logger=LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/listPage")
	public void listPage(@ModelAttribute("cri") SearchCriteria cri,Model model) throws Exception{
		logger.info("listPage");
		
			List<BoardVO> list=service.listSearch(cri);
			
			PageMaker pageMaker=new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.makeSearch(cri.getPage());
			pageMaker.setTotalCount(service.listSearchCount(cri));
			
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

		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void readPage(int bno, String i, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("readPage");
		if(i==null){
			service.updateCnt(bno);
		}
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.makeSearch(cri.getPage());
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
		model.addAttribute("pageMaker",pageMaker);
		//model.addAttribute("cri",cri);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGet(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("modify get");
		BoardVO vo = service.read(bno);
		
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.makeSearch(cri.getPage());
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("board", vo);
		model.addAttribute("pageMaker",pageMaker);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO board, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rtts) throws Exception {
		logger.info("modify post");
		service.modify(board);

		rtts.addAttribute("bno", board.getBno());
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);

		return "redirect:/sboard/readPage"+pageMaker.makeSearch(cri.getPage())+"&i=1";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET) 
	public String delete(int bno, SearchCriteria cri,RedirectAttributes rtts) throws Exception {
		logger.info("delete");
		service.remove(bno);
		
		rtts.addAttribute("perPageNum",cri.getPerPageNum());
		rtts.addAttribute("page",cri.getPage());

		return "redirect:/sboard/listPage";

	}
	
	
}
