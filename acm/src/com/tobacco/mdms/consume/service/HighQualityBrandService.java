package com.tobacco.mdms.consume.service;

import org.g4studio.core.metatype.Dto;

public interface HighQualityBrandService {
	/**
	 * 添加动力或政策品牌
	 * @param dto
	 * @return
	 */
	public void insertSelectedBrand(Dto dto);

	/**
	 * 添加优质货源主表
	 * @param dto
	 * @return 返回插入的数据ID
	 */
	public void addHighQuality(Dto dto);

	/**
	 * 修改
	 * @param inDto
	 * @return
	 */
	public Integer update(Dto inDto);

	/**
	 * 删除
	 * @param inDto
	 * @return
	 */
	public Integer delete(Dto inDto);
}
