package com.eallard.cms.dao;

import java.util.List;

import com.eallard.basic.dao.IBaseDao;
import com.eallard.basic.model.Pager;
import com.eallard.cms.model.Group;
import com.eallard.cms.model.Role;
import com.eallard.cms.model.RoleType;
import com.eallard.cms.model.User;
import com.eallard.cms.model.UserGroup;
import com.eallard.cms.model.UserRole;

/**
 * 用户对象操作
 * @author renzw
 *
 */
public interface IUserDao extends IBaseDao<User> {
	
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	public User loadByUsername(String username);
	
	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 */
	public User selectUserById(int userId);
	
	/**
	 * 获取用户所有角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	
	/**
	 * 获取用户所有角色信息ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserRoleIds(int userId);
	
	/**
	 * 获取用户所有组信息
	 * @param userId
	 * @return
	 */
	public List<Group> listUserGroups(int userId);
	
	/**
	 * 获取用户所有组ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserGroupIds(int userId);
	
	/**
	 * 根据用户和组获取用户组对象
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public UserGroup loadUserGroup(int userId, int groupId);
	
	/**
	 * 根据用户和角色获取用户角色对象
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int userId, int roleId);
	
	/**
	 * 根据角色获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> listRoleUsers(int roleId);
	
	/**
	 * 获取某个角色类型下的用户
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers(RoleType roleType);
	
	/**
	 * 根据组获取用户列表
	 * @param groupId
	 * @return
	 */
	public List<User> listGroupUsers(int groupId);
	
	/**
	 * 分页显示用户列表
	 * @return
	 */
	public Pager<User> findUser();
	
	/**
	 * 添加用户角色对象
	 * @param user
	 * @param role
	 */
	public void addUserRole(User user, Role role);
	
	/**
	 * 添加用户组对象
	 * @param user
	 * @param group
	 */
	public void addUserGroup(User user, Group group);
	
	/**
	 * 删除用户关联的角色关系
	 * @param userId
	 */
	public void deleteUserRole(int userId);
	
	/**
	 * 删除用户关联的组关系
	 * @param userId
	 */
	public void deleteUserGroup(int userId);
	
	/**
	 * 根据用户和组删除用户组关系
	 * @param userId
	 * @param groupId
	 */
	public void deleteUserGroup(int userId, int groupId);
	
	/**
	 * 根据用户和角色删除角色关系
	 * @param userId
	 * @param roleId
	 */
	public void deleteUserRole(int userId, int roleId);
}















