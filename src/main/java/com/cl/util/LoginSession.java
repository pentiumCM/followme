package com.cl.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cl.entity.User;

/**
 * 用户登陆信息存放session中，登陆时相关接口调用当前方法实现权限管理
 * 
 * @author: 王越
 * @date: 2019年5月22日 下午2:06:02
 */
public class LoginSession {
	/**
	 * 从session中读取用户信息，如果是系统管理员返回-1，一级权限账户返回0，否则返回当前账号关联的单位信息
	 * 
	 * @param session
	 * @return
	 */
	public static User getUser() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		User user = (User) request.getSession().getAttribute("USER_SESSION");
		return user;
	}
}
