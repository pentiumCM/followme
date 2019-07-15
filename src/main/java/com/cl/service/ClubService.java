package com.cl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Club;

public interface ClubService {
	
	Club selectClubByActID(Integer actID);
	
	void insert(Club club);
	
	Club selectClubByClubLogin(@Param("clubLogin") String clubLogin);
	
	List<Club> getAllClubs();
}
