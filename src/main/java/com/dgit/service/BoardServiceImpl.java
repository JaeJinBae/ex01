package com.dgit.service;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDao;
import com.dgit.persistence.ReplyDao;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao dao;
	
	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		String[] files=board.getFiles();
		if(files==null){
			return;
		}
		for(String fileName:files){
			dao.addAttach(fileName, board.getBno());
		}
		
	}

	@Override
	public BoardVO read(int bno,boolean isRead) throws Exception {		
		
		if(isRead){
			dao.updateCnt(bno);
		}
		BoardVO vo=dao.read(bno);
		List<String> list=dao.getAttach(bno);
		vo.setFiles(list.toArray(new String[list.size()]));
		return vo;
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);
	}

	@Override
	@Transactional
	public void remove(int bno) throws Exception {		
		//upload파일 삭제
		List<String> list=dao.getAttach(bno);
		System.gc();
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				String path=list.get(i).substring(0,25);
				String filename=list.get(i).substring(27);
				
				File delOrigin=new File(path+filename);
				File delThum = new File(list.get(i));
				
				if(delThum.exists()||delOrigin.exists()){
					delThum.delete();
					delOrigin.delete();
				}
			}
			dao.deleteAttach(bno);
		}
		
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		return dao.countPaging();
	}

	@Override
	public void updateCnt(int bno) throws Exception {
		dao.updateCnt(bno);
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

}
