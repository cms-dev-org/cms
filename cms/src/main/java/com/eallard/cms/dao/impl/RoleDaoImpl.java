package com.eallard.cms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eallard.basic.dao.impl.BaseDaoImpl;
import com.eallard.cms.dao.IRoleDao;
import com.eallard.cms.model.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao{

	@Override
	public List<Role> listRole() {
		return this.list("from Role");
	}
	
}
