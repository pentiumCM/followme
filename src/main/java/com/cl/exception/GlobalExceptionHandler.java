package com.cl.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局捕获异常
 * 
 * @author: dylan
 * @date: 2019年4月4日 下午11:08:20
 */
@ControllerAdvice(basePackages = "org.jit.sose.controller") // 作为全局异常处理的切面类，可以设置包的范围
public class GlobalExceptionHandler {

	/**
	 * 捕获运行时异常
	 * 
	 * @param e 异常内容
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(RuntimeException.class) // 设置具体捕获异常类
	public Map<String, Object> RuntimeException(Exception e) {
		System.out.println("*****************全局捕获运行时异常****************");
		Map<String, Object> map = new HashMap<>();
//		LogUtil.logResult("RuntimeException", e);
		e.printStackTrace();
		map.put("info", "error");
		map.put("status", 500);
		map.put("errorMsg", "全局捕获运行时异常");
		return map;
	}

	/**
	 * 捕获空指针异常
	 * 
	 * @param e 异常内容
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> NullPointException(Exception e) {
		System.out.println("*****************全局捕获空指针异常*****************");
		Map<String, Object> map = new HashMap<>();
//		LogUtil.logResult("NullPointerException", e);
		e.printStackTrace();
		map.put("info", "error");
		map.put("status", 500);
		map.put("errorMsg", "全局捕获空指针异常");
		return map;
	}

	/**
	 * 捕获数据库异常
	 * 
	 * @param e 异常内容
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(SQLException.class) // 设置具体捕获异常类
	public Map<String, Object> SQLException(Exception e) {
		System.out.println("*****************全局捕获数据库异常****************");
		Map<String, Object> map = new HashMap<>();
//		LogUtil.logResult("RuntimeException", e);
		e.printStackTrace();
		map.put("info", "error");
		map.put("status", 500);
		map.put("errorMsg", "全局捕获数据库异常");
		return map;
	}
}
