package com.tobacco.mdms.consume.service;

import java.util.List;

import org.g4studio.core.metatype.Dto;

@SuppressWarnings("rawtypes")
public interface ConsumeDistributionService {

	/**
	 * 查询当前用户子用户
	 * @param dto
	 * @return
	 */
	public String querySubUser(Dto dto);

	/**
	 * 添加分配
	 * @param inDto
	 */
	public void insert(Dto inDto);

	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	public String query(Dto dto);

	/**
	 * 修改
	 * @param dto
	 */
	public void update(Dto dto);

	/**
	 * 删除
	 * @param inDto
	 * @return
	 */
	public Integer delete(Dto inDto);

	/**
	 * 查询分配比率数据和客户经理
	 * @param dto
	 * @return
	 */
	public List querySubUserDistributeAmount(Dto dto);

	/**
	 * 分配卷烟
	 * @param dto
	 */
	public void distribute(Dto dto);

	/**
	 * 查询分配数据
	 * @param dto
	 * @return
	 */
	public List queryDistribute(Dto dto);
	
}
