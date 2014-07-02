package com.tobacco.mdms.consume.service.impl;

import org.g4studio.core.metatype.Dto;
import org.g4studio.core.model.service.impl.BizServiceImpl;

import com.tobacco.mdms.consume.service.HighQualityService;

public class HighQualityServiceImpl extends BizServiceImpl implements HighQualityService {

	//新增客户信息
	public void insert(Dto inDto) {
		g4Dao.insert("HighQuality.insert", inDto);
	}

	public int update(Dto inDto) {
		return g4Dao.update("HighQuality.update", inDto);
	}

	public int delete(Dto inDto) {
		return g4Dao.update("HighQuality.delete", inDto);
	}

}
