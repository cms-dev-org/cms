package com.eallard.cms.service.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eallard.basic.model.Pager;
import com.eallard.cms.constant.CmsServiceConstants;
import com.eallard.cms.dao.IGroupDao;
import com.eallard.cms.dao.IRoleDao;
import com.eallard.cms.dao.IUserDao;
import com.eallard.cms.exception.CmsException;
import com.eallard.cms.model.Group;
import com.eallard.cms.model.Role;
import com.eallard.cms.model.User;
import com.eallard.cms.service.IUserService;

/**
 * 
 * @author renzw
 * @date 2014-4-24 上午11:41:40 
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IGroupDao groupDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	

	@Override
	public void add(User user, List<Integer> roleIds, List<Integer> groupIds) {
		if(user == null) throw new CmsException("用户对象为空，请重新添加！");
		User tu = userDao.loadByUsername(user.getUsername().trim());
		if(tu != null) throw new CmsException("添加的用户已经存在，不能添加！");
		
		user.setStatus(CmsServiceConstants.USER_STATUS_USED);
		userDao.add(user);
		
		if(roleIds != null) {
			for(Integer rId : roleIds) {
				addUserRole(user, rId);
			}
		}
		
		if(groupIds != null) {
			for(Integer gId : groupIds) {
				addUserGroup(user, gId);
			}
		}
	}

	@Override
	public void delete(int id) {
		//TODO 需要判断用户是否有文章，如果有文章则不能删除
		
		//1.删除用户组关系
		userDao.deleteUserGroup(id);
		//2.删除用户角色关系
		userDao.deleteUserRole(id);
		
		userDao.delete(id);
	}

	@Override
	public void update(User user, Integer[] roleIds, Integer[] groupIds) {
		List<Integer> existsRoleIds = userDao.listUserRoleIds(user.getId());
		List<Integer> existsGroupIds = userDao.listUserGroupIds(user.getId());
		
		//1.遍历前台传递过来的角色ID，如果用户没有前台传递过来ID则添加用户角色关系
		for(Integer item : roleIds) {
			if(!existsRoleIds.contains(item)) {
				addUserRole(user, item);
			}
		}
		
		//2.遍历前台传递过来的组ID，如果用户没有前台传递过来ID则添加用户组关系
		for(Integer item : groupIds) {
			if(!existsGroupIds.contains(item)) {
				addUserGroup(user, item);
			}
		}
		
		//3.delete UserRole
		for(Integer existsRoleId : existsRoleIds) {
			if(!ArrayUtils.contains(roleIds, existsRoleId)) {
				userDao.deleteUserGroup(user.getId(), existsRoleId);
			}
		}
		
		//4. delete UserGroup
		for(Integer existsGroupId : existsGroupIds) {
			if(!ArrayUtils.contains(roleIds, existsGroupId)) {
				userDao.deleteUserRole(user.getId(), existsGroupId);
			}
		}
	}

	@Override
	public void updateStatus(int id) {
		User user = userDao.load(id);
		if(user == null) throw new CmsException("修改的用户不存在！");
		if(user.getStatus() == 0) user.setStatus(1);
		else user.setStatus(0);
		userDao.update(user);
	}

	@Override
	public Pager<User> findUser() {
		
		return userDao.findUser();
	}

	@Override
	public User load(int userId) {
		return userDao.load(userId);
	}

	@Override
	public List<Role> listUserRoles(int userId) {
		return userDao.listUserRoles(userId);
	}

	@Override
	public List<Group> listUserGroup(int userId) {
		return userDao.listUserGroups(userId);
	}
	
	
	/***********************私有方法***********************/
	
	/**
	 * 根据用户和角色ID添加用户角色信息
	 * @param user
	 * @param roleId
	 */
	private void addUserRole(User user, Integer roleId) {
		Role role = roleDao.load(roleId);
		if(role == null) throw new CmsException("用户角色不存在，不能添加！");
		userDao.addUserRole(user, role);
	}
	
	/**
	 * 根据用户和组ID添加用户组信息
	 * @param user
	 * @param gId
	 */
	private void addUserGroup(User user, Integer groupId) {
		Group group = groupDao.load(groupId);
		if(group == null) throw new CmsException("用户角色不存在，不能添加！");
		userDao.addUserGroup(user, group);
	}
}
