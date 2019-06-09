package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.Vedio;
import com.cl.mapper.VedioMapper;
import com.cl.service.VedioService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class VedioServiceImpl implements VedioService {

	@Autowired
	VedioMapper vedioMapper;

	@Override
	public void insert(Vedio vedio) {
		// TODO Auto-generated method stub
		vedioMapper.insert(vedio);

	}

	@Override
	public PageInfo<Vedio> selectGif(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Vedio> list = vedioMapper.selectGif();
		PageInfo<Vedio> pageInfo = new PageInfo<Vedio>(list);
		return pageInfo;
	}

	@Override
	public Vedio selectVedioByActID(Integer actID) {
		// TODO Auto-generated method stub
		return vedioMapper.selectVedioByActID(actID);
	}

}
