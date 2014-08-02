package com.eallard.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * 用户表
 * @author renzw
 *
 */
@Entity
@Table(name = "cms_user")
public class User {

	/** 用户ID*/
	private int id;
	
	/** 用户姓名*/
	private String username;
	
	/** 用户账号*/
	private String nickname;
	
	/** 用户密码*/
	private String password;
	
	/** 性别 0未知1男2女 */
	private Integer sex;
	
	/** 用户邮箱*/
	private String email;
	
	/** 用户手机号*/
	private String mobilePhone;
	
	/** 用户状态：1启用 0停用*/
	private Integer status;
	
	/** 用户创建时间*/
	private Date createTime;
	
	private String remark;
	
	private Integer createAuthor;
	
	private Integer updateAuthor;
	
	private Date updateTime;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@NotNull(message="用户名不能为空")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull(message="密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Email(message="邮箱格式不正确")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="mobile_phone")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="create_author")
	public Integer getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(Integer createAuthor) {
		this.createAuthor = createAuthor;
	}

	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="update_author")
	public Integer getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(Integer updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
