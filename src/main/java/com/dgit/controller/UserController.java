package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDto;
import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public void loginGet(){
		
	}
	
	@RequestMapping(value="/loginPost",method=RequestMethod.POST)
	public void loginPost(LoginDto dto, Model model) throws Exception{
		logger.info("[loginPost]=======");
		logger.info(dto.toString());
		
		MemberVO vo=service.readWithPW(dto.getUserid(), dto.getUserpw());
		if(vo==null){
			logger.info("user없읍.............");
			logger.info("loginPOST  Return.............");
			return;
		}
		
		dto.setUsername(vo.getUsername());
		dto.setUserpw("");
		logger.info("loginPOST  model add.............");
		model.addAttribute("loginDTO",dto);
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session)throws Exception{
		logger.info("logout======================");
		session.invalidate();
		
		return "redirect:/";//home으로 감
		
	}
	
}







