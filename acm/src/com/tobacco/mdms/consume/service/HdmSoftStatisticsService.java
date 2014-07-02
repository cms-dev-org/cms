package com.tobacco.mdms.consume.service;

import org.g4studio.core.metatype.Dto;

public interface HdmSoftStatisticsService {

	/**
	 * 新增信息
	 * @param inDto
	 */
	public void insert(Dto inDto);

	/**
	 * 修改
	 * @param inDto
	 * @return
	 */
	public int update(Dto inDto);

	/**
	 * 刪除
	 * @param inDto
	 * @return
	 */
	public int delete(Dto inDto);
	
}
