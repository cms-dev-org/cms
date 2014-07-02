package com.tobacco.mdms.consume.service;

import org.g4studio.core.metatype.Dto;

public interface ConsumeCustomerService {

	/**
	 * 新增客户信息
	 * @param inDto
	 */
	public void insert(Dto inDto);

	/**
	 * 删除客户信息
	 * @param inDto
	 * @return
	 */
	public int delete(Dto inDto);

	/**
	 * 修改客户信息
	 * @param inDto
	 * @return
	 */
	public int update(Dto inDto);
	
}
