package com.cl.mapper;

import java.util.List;

import com.cl.entity.Vedio;

public interface VedioMapper {

	 void insert(Vedio vedio);
	 
	 List<Vedio> selectGif();
	
}
