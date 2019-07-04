package com.cl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.Club;
import com.cl.mapper.ClubMapper;
import com.cl.service.ClubService;

@Service
public class ClubServiceImpl implements ClubService {

	@Autowired
	ClubMapper clubMapper;
	
	@Override
	public Club selectClubByActID(Integer actID) {
		// TODO Auto-generated method stub
		return clubMapper.selectClubByActID(actID);
	}

	@Override
	public void insert(Club club) {
		clubMapper.insert(club);
		
	}

	@Override
	public Club selectClubByClubLogin(String clubLogin) {
		// TODO Auto-generated method stub
		return clubMapper.selectClubByClubLogin(clubLogin);
	}

}
