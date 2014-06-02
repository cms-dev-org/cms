package com.eallard.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eallard.cms.dao.IRoleDao;
import com.eallard.cms.dao.IUserDao;
import com.eallard.cms.exception.CmsException;
import com.eallard.cms.model.Role;
import com.eallard.cms.model.User;
import com.eallard.cms.service.IRoleService;

@Service("roleServiceImpl")
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserDao userDao;

	@Override
	public void add(Role role) {
		roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		List<User> users = userDao.listRoleUsers(id);
		if(users != null && users.size() > 0) {
			throw new CmsException("删除的角色对象中存在用户，不能删除");
		}
		roleDao.delete(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}

	@Override
	public List<Role> listRole() {
		return roleDao.listRole();
	}

	@Override
	public void deleteRoleUsers(int rid) {
		roleDao.delete(rid);
	}

}
