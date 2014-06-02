package com.eallard.basic.dao;

import java.io.Serializable;

/**
 * 数据库操作接口
 * @author renzw
 *
 */
public interface IBaseDao<T> {
	/**
	 * 新增数据
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * 修改数据
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 根据ID删除数据
	 * @param id
	 */
	public void delete(Serializable id);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public T load(Serializable id);
	
}
