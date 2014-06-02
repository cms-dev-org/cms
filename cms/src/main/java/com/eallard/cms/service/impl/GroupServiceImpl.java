package com.eallard.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eallard.basic.model.Pager;
import com.eallard.cms.dao.IGroupDao;
import com.eallard.cms.dao.IUserDao;
import com.eallard.cms.exception.CmsException;
import com.eallard.cms.model.Group;
import com.eallard.cms.model.User;
import com.eallard.cms.service.IGroupService;

@Service("groupServiceImpl")
public class GroupServiceImpl implements IGroupService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IGroupDao groupDao;

	@Override
	public void add(Group group) {
		
	}

	@Override
	public void delete(int id) {
		List<User> user = userDao.listGroupUsers(id);
		if(user != null && user.size() > 0) {
			throw new CmsException("删除的组对象中存在用户，不能删除");
		}
		groupDao.delete(id);
	}

	@Override
	public void update(Group group) {
		groupDao.update(group);
	}

	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}

	@Override
	public Pager<Group> findGroup() {
		return groupDao.findGroup();
	}

	@Override
	public void deleteGroupUsers(int gid) {
		groupDao.delete(gid);
	}

}
