package com.dgit.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.service.ReplyService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/")
public class SearchBoardController {
	private static final Logger logger=LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private ReplyService rService;
	
	@Resource(name="uploadPath")
	private String outUploadPath;
	
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
	public String regitserPost(BoardVO board, List<MultipartFile> imageFiles, RedirectAttributes rttr) throws Exception { 
		logger.info("register post");
		
		if(imageFiles.get(0).getBytes().length != 0){
			String[] files=new String[imageFiles.size()];
			
			for(int i=0;i<files.length;i++){
				logger.info("file : " + imageFiles.get(i).getOriginalFilename());
				String savedName=UploadFileUtils.uploadFile(outUploadPath, imageFiles.get(i).getOriginalFilename(), imageFiles.get(i).getBytes());
				files[i]=outUploadPath+savedName;
			}
			
			board.setFiles(files);
		}
		
		service.regist(board);
		rttr.addFlashAttribute("msg","success");
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void readPage(int bno, String i, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("readPage============");
		if(i==null){
			service.updateCnt(bno);
		}
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.makeSearch(cri.getPage());
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		BoardVO vo = service.read(bno,true);
		model.addAttribute("board", vo);
		model.addAttribute("pageMaker",pageMaker);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGet(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("modify get");
		BoardVO vo = service.read(bno,true);
		
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
		
		rService.deleteByBno(bno);//게시글에 해당하는 댓글 삭제
		service.remove(bno);//게시글, 파일 삭제
		rtts.addAttribute("perPageNum",cri.getPerPageNum());
		rtts.addAttribute("page",cri.getPage());

		return "redirect:/sboard/listPage";

	}
	
	@ResponseBody
	@RequestMapping(value="displayFile",method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity=null;
		InputStream in=null;
		
		logger.info("[displayFilename]: "+filename); // /2018/03/19/asdf.jpg
		
		try {
			String formatName=filename.substring(filename.lastIndexOf(".")+1);
			MediaType type=MediaUtils.getMediaType(formatName);
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(type);
			
			in=new FileInputStream(filename);
			
			entity=new ResponseEntity<>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}
