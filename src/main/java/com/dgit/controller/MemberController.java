package com.dgit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ResponseEntity<String> add(@RequestBody MemberVO vo){
		ResponseEntity<String> entity=null;
		
		try {
			service.insert(vo);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseEntity<List<MemberVO>> list(){
		ResponseEntity<List<MemberVO>> entity=null;
		
		try {
			List<MemberVO> list=service.list();
			entity=new ResponseEntity<List<MemberVO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<List<MemberVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/{userid}",method={RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("userid") String userid,@RequestBody MemberVO vo){
		ResponseEntity<String> entity=null;
		
		try {
			//vo.setUserid(userid);
			service.update(vo);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/{userid}",method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("userid") String userid){
		ResponseEntity<String> entity=null;
		
		try {
			service.delete(userid);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}






