package com.cl.resp;

import java.util.List;
import java.util.Map;

public class CommonResp {

	private String code;
	
	private String msg;
	
	private Object obj;
	
	private Map<String, Object> map;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public CommonResp(String code, String msg, Object obj, Map<String, Object> map) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
		this.map = map;
	}

	
	
	
	
}
