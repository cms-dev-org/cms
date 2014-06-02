package com.eallard.cms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eallard.basic.dao.impl.BaseDaoImpl;
import com.eallard.basic.model.Pager;
import com.eallard.cms.dao.IGroupDao;
import com.eallard.cms.model.Group;

@Repository
public class GroupDaoImpl extends BaseDaoImpl<Group> implements IGroupDao{

	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	@Override
	public Pager<Group> findGroup() {
		return this.find("from Group");
	}
	
}
