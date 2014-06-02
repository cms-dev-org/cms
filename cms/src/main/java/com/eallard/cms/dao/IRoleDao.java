package com.eallard.cms.dao;

import java.util.List;

import com.eallard.basic.dao.IBaseDao;
import com.eallard.cms.model.Role;

/**
 * 权限对象操作
 * @author renzw
 *
 */
public interface IRoleDao extends IBaseDao<Role> {
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> listRole();
}
