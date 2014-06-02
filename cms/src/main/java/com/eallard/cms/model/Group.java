package com.eallard.cms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户组
 * @author renzw
 *
 */
@Entity
@Table(name = "cms_group")
public class Group {
	
	/** 用户组ID */
	private int id;
	
	/** 用户组名称 */
	private String name;
	
	/** 用户组描述信息 */
	private String describtion;

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

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

}
