package com.cl.service;

import java.util.List;

import com.cl.entity.UserAct;

public interface UserActService {
	List<UserAct> selectAllUsersByClubID(Integer clubID);
}
