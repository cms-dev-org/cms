package com.eallard.cms.service;

import java.util.List;

import com.eallard.cms.model.Role;

/**
 * 角色
 * @author rzw
 * 
 */
public interface IRoleService {
	
	public void add(Role role);
	
	public void delete(int id);
	
	public void update(Role role);
	
	public Role load(int id);
	
	public List<Role> listRole();
	
	public void deleteRoleUsers(int rid);
}
