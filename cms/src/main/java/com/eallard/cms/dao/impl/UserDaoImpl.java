package com.eallard.cms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eallard.basic.dao.impl.BaseDaoImpl;
import com.eallard.basic.model.Pager;
import com.eallard.cms.dao.IUserDao;
import com.eallard.cms.model.Group;
import com.eallard.cms.model.Role;
import com.eallard.cms.model.RoleType;
import com.eallard.cms.model.User;
import com.eallard.cms.model.UserGroup;
import com.eallard.cms.model.UserRole;

@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	
	@Override
	public User loadByUsername(String username) {
		String hql = "from User where username=?";
		return (User)this.queryObject(hql, username);
	}

	@Override
	public User selectUserById(int userId) {
		return this.get(userId);
	}

	@Override
	public List<Role> listUserRoles(int userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserRoleIds(int userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Group> listUserGroups(int userId) {
		String hql = "select ug.group from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserGroupIds(int userId) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public UserGroup loadUserGroup(int userId, int groupId) {
		String hql = "select ug from UserGroup ug left join fetch ug.user " +
				"left join fetch ug.group where ug.user.id=? and ug.group.id=?";
		return (UserGroup) this.getSession().createQuery(hql).
				setParameter(0, userId).setParameter(1, groupId).
				uniqueResult();
	}

	@Override
	public UserRole loadUserRole(int userId, int roleId) {
		String hql = "select ur from UserRole ur left join fetch ur.user " +
				"left join fetch ur.role where ur.user.id=? and ur.role.id=?";
		return (UserRole) this.getSession().createQuery(hql).
				setParameter(0, userId).setParameter(1, roleId).
				uniqueResult();
	}

	@Override
	public List<User> listRoleUsers(int roleId) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		return this.list(hql);
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql, roleType);
	}

	@Override
	public List<User> listGroupUsers(int groupId) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql, groupId);
	}

	@Override
	public Pager<User> findUser() {
		return this.find("from User");
	}

	@Override
	public void addUserRole(User user, Role role) {
		UserRole ur = this.loadUserRole(user.getId(), role.getId());
		if(ur == null) {
			ur = new UserRole();
			ur.setRole(role);
			ur.setUser(user);
			this.getSession().save(ur);
		}
	}

	@Override
	public void addUserGroup(User user, Group group) {
		UserGroup ur = this.loadUserGroup(user.getId(), group.getId());
		if(ur == null) {
			ur = new UserGroup();
			ur.setGroup(group);
			ur.setUser(user);
			this.getSession().save(ur);
		}
	}

	@Override
	public void deleteUserRole(int userId) {
		String hql = "delete UserRole ur where ur.user.id=?";
		this.updateByHql(hql, userId);
	}

	@Override
	public void deleteUserGroup(int userId) {
		String hql = "delete UserGroup ug where ug.user.id=?";
		this.updateByHql(hql, userId);
	}

	@Override
	public void deleteUserGroup(int userId, int groupId) {
		String hql = "delete UserGroup ug where ug.user.id=? and ug.group.id=?";
		this.updateByHql(hql, new Object[] {userId, groupId});
	}

	@Override
	public void deleteUserRole(int userId, int roleId) {
		String hql = "delete UserRole ug where ug.user.id=? and ug.role.id=?";
		this.updateByHql(hql, new Object[] {userId, roleId});
	}
	
	
}
