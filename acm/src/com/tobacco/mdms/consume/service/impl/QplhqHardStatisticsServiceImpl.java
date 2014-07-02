package com.tobacco.mdms.consume.service.impl;

import org.g4studio.core.metatype.Dto;
import org.g4studio.core.model.service.impl.BizServiceImpl;

import com.tobacco.mdms.consume.service.QplhqHardStatisticsService;

public class QplhqHardStatisticsServiceImpl extends BizServiceImpl
	implements QplhqHardStatisticsService {

	public void insert(Dto inDto) {
		this.g4Dao.insert("QplhqHardStatistics.insert", inDto);
	}

	public int update(Dto inDto) {
		return this.g4Dao.update("QplhqHardStatistics.update", inDto);
	}

	public int delete(Dto inDto) {
		return this.g4Dao.update("QplhqHardStatistics.delete", inDto);
	}


	/**
	 * 添加动力或政策品牌
	 */
	public int insertSelectedBrand(Dto dto) {
		int result = 0;
		
		
		
		return result;
	}

}
