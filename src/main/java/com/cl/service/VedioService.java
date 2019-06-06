package com.cl.service;

import java.util.List;

import com.cl.entity.Vedio;
import com.github.pagehelper.PageInfo;

public interface VedioService {
	void insert(Vedio vedio);
	
	PageInfo<Vedio> selectGif(Integer pageNum,Integer pageSize);
}
