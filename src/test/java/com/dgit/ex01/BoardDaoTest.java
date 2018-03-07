package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDaoTest {

	@Autowired
	private BoardDao dao;
	
	//@Test
	public void testCreate() throws Exception{
		BoardVO vo= new BoardVO();
		vo.setTitle("안녕하세요.");
		vo.setContent("하이하이하이하이하이하이하이하이하이하이");
		vo.setWriter("배재진");
		dao.create(vo);
	}
	
	@Test
	public void testRead() throws Exception{
		dao.read(1);
	}
	
	//@Test
	public void testListAll() throws Exception{
		dao.listAll();
	}
	
	
	//@Test
	public void testUpdate() throws Exception{
		BoardVO vo= new BoardVO();
		vo.setBno(2);
		vo.setTitle("잘가세요~");
		vo.setContent("바이바이방~~~");
		dao.update(vo);
	}
	
	//@Test
	public void testDelete() throws Exception{
		dao.delete(2);
	}
	

	
}
