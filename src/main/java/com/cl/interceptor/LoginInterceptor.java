package com.cl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
//		String[] deviceArray = new String[] { "Android", "mac os", "windows phone" };
//		String type = request.getHeader("user-agent");
//		for (int i = 0; i < deviceArray.length; i++) {
//			if (type.indexOf(deviceArray[i]) > 0) {
//				return true;
//			}
//		}
//		String url = request.getHeader("Referer");
//		System.out.println("url:           " + url);
//		if (url == null) {
//			url = request.getRequestURI();
//			System.out.println("zheshi   " + url);
//		}
//		if (!(url.contains("login.html") || url.contains("register.html") || url.contains("forgetPwd.html")
//				|| url.endsWith("/") || url.contains("login.sose"))) {
//			System.out.println("拦截页面");
//			HttpSession session = request.getSession();
//			User user = (User) session.getAttribute("USER_SESSION");
//			if (user == null) {
//				System.out.println("重定向");
//				response.sendRedirect("/gies/login.html");
//				return false;
//			}
//		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mav)
			throws Exception {
	}
}
