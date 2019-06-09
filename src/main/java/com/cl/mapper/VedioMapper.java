package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Vedio;

public interface VedioMapper {

	 void insert(Vedio vedio);
	 
	 List<Vedio> selectGif();
	
	 Vedio selectVedioByActID(@Param("actID") Integer actID);
}
