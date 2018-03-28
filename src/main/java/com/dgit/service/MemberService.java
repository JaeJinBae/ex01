package com.dgit.service;

import java.util.List;

import com.dgit.domain.MemberVO;

public interface MemberService {
	public List<MemberVO> list() throws Exception;
	public void insert(MemberVO vo) throws Exception;
	public void update(MemberVO vo) throws Exception;
	public void delete(String userid) throws Exception;
	public MemberVO readWithPW(String userid, String userpw) throws Exception;
}
