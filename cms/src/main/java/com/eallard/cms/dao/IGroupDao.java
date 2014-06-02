package com.eallard.cms.dao;

import java.util.List;

import com.eallard.basic.dao.IBaseDao;
import com.eallard.basic.model.Pager;
import com.eallard.cms.model.Group;

/**
 * 组操作对象
 * @author renzw
 *
 */
public interface IGroupDao extends IBaseDao<Group> {
	
	/**
	 * 查询所有组信息
	 * @return
	 */
	public List<Group> listGroup();
	
	/**
	 * 分页查询组信息
	 * @return
	 */
	public Pager<Group> findGroup();
}
