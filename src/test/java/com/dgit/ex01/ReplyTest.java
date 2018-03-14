package com.dgit.ex01;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ReplyTest {
	@Autowired
	private ReplyDao dao;
	
	
	//@Test
	public void createTest() throws Exception{		
		ReplyVO vo=new ReplyVO();
		vo.setBno(2042);
		vo.setReplytext("댓글입니다잉");
		vo.setReplyer("세종대왕");
		dao.create(vo);	
	}
	
	//@Test
	public void listTest() throws Exception{
		dao.list(2042);
	}
	
	//@Test
	public void updateTest() throws Exception{
		ReplyVO vo=new ReplyVO();
		vo.setReplytext("수정입니다~~");
		vo.setRno(2);
		dao.update(vo);
	}
	
	@Test
	public void deleteTest() throws Exception{
		dao.delete(2);
		
	}

}
