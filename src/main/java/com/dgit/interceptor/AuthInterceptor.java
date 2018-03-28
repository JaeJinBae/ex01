package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger=LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle ==========");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle ==========");
		HttpSession session=request.getSession();
		if(session.getAttribute("login")==null){
			saveDest(request);
			logger.info("Go Login ==========");
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;//원래 가려고 했던 controller로 이동 안됨
		}
		return true;
	}
	
	
	private void saveDest(HttpServletRequest req){
		String uri=req.getRequestURI();//주소 줄에 매개변수 뺀 값을 가져옴
		String query=req.getQueryString();
		if(query==null || query.equals("null")){
			query="";
		}else{
			query="?"+query;
		}
		
		if(req.getMethod().equals("GET")){
			logger.info("dest: "+(uri+query));
			req.getSession().setAttribute("dest", uri+query);
		}
	}

	
}




