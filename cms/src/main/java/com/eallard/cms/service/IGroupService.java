package com.eallard.cms.service;

import java.util.List;

import com.eallard.basic.model.Pager;
import com.eallard.cms.model.Group;

/**
 * 组
 * @author rzw
 *
 */
public interface IGroupService {

	public void add(Group group);
	
	public void delete(int id);
	
	public void update(Group group);
	
	public Group load(int id);
	
	public List<Group> listGroup();
	
	public Pager<Group> findGroup();
	
	public void deleteGroupUsers(int gid);
}
