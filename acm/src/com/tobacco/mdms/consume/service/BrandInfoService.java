package com.tobacco.mdms.consume.service;

import org.g4studio.core.metatype.Dto;

public interface BrandInfoService {

	/**
	 * 新增
	 * @param dto
	 */
	public void insert(Dto dto);

	public void update(Dto dto);

	public Integer delete(Dto inDto);
	
}
