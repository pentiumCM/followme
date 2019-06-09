package com.cl.service;

import com.cl.entity.Vedio;
import com.github.pagehelper.PageInfo;

public interface VedioService {
	void insert(Vedio vedio);
	
	PageInfo<Vedio> selectGif(Integer pageNum,Integer pageSize);
	
	 Vedio selectVedioByActID(Integer actID);
}
