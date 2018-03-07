package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.BoardVO;
import com.dgit.persistence.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao dao;
	
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);
	}

	@Override
	public void remove(int bno) throws Exception {
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

}
