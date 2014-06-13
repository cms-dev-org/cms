package com.eallard.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 * @author renzw
 *
 */
@Entity
@Table(name = "cms_role")
public class Role {
	
	/** 角色ID */
	private int id;
	
	/** 角色名称 */
	private String name;
	
	/** 角色类型 枚举类型 */
	private String roleType;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="role_type")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
