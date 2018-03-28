package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.LoginDto;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandel~~~~~~~~~~~~~~~~~");
		
		HttpSession session=request.getSession();
		Object object=modelAndView.getModel().get("loginDTO");
		if(object != null){
			LoginDto dto=(LoginDto)object;
			logger.info("userid: "+dto.getUserid());
			session.setAttribute("login", dto);
			
			Object dest=session.getAttribute("dest");
			String path=(dest!=null)?(String)dest:request.getContextPath();
			session.removeAttribute("dest");
			
			response.sendRedirect(path);//homeController로 간다
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandel~~~~~~~~~~~~~~~~~");
		HttpSession session=request.getSession();
		if(session.getAttribute("login") != null){
			logger.info("이전 login정보 삭제");
			session.removeAttribute("login");
		}
		return true;
	}

	
	
}
