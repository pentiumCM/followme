package com.cl.service;

import com.cl.entity.Admin;

public interface AdminService {

	Admin selectAdmin(String login, String password);
	
}
