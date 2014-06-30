package com.eallard.cms.service;

import java.util.List;

import com.eallard.basic.model.Pager;
import com.eallard.cms.model.Group;
import com.eallard.cms.model.Role;
import com.eallard.cms.model.User;

/**
 * 
 * @author renzw
 * @date 2014-4-24 上午11:29:45 
 */
public interface IUserService {
	
	/**
	 * 添加用户，需要判断用户名是否存在
	 * @param user 用户对象信息
	 * @param roleIds 角色
	 * @param groupIds 组
	 */
	public void add(User user, List<Integer> roleIds, List<Integer> groupIds);
	
	/**
	 * 删除用户，级联删除用户与角色，用户与组的关系删除
	 * 用户存在文章，则不能删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 用户更新，同时更新与角色和组的对应关系
	 * @param user
	 * @param roleIds
	 * @param groupIds
	 */
	public void update(User user, Integer[] roleIds, Integer[] groupIds);
	
	/**
	 * 更新用户状态（停用、启用）
	 * @param id
	 */
	public Integer updateStatus(int id);
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public Pager<User> findUser();
	
	/**
	 * 获取用户详细信息
	 * @param userId
	 * @return
	 */
	public User load(int userId);
	
	/**
	 * 获取用户角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	
	/**
	 * 获取用户组信息
	 * @param userId
	 * @return
	 */
	public List<Group> listUserGroup(int userId);
}













