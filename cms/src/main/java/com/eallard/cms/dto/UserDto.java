package com.eallard.cms.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.eallard.cms.model.User;

public class UserDto {
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
	private Date createDate;
	
	private String remark;
	
	private Integer createAuthor;
	
	/**
	 * 角色id
	 */
	private List<Integer> roleIds;
	/**
	 * 组id
	 */
	private List<Integer> groupIds;
	
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

	@NotEmpty(message="用户名不能为空")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotEmpty(message="密码不能为空")
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(Integer createAuthor) {
		this.createAuthor = createAuthor;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public User getUser() {
		User u = new User();
		u.setId(id);
		u.setUsername(username);
		u.setNickname(nickname);
		u.setPassword(password);
		u.setSex(sex);
		u.setEmail(email);
		u.setMobilePhone(mobilePhone);
		u.setStatus(status);
		u.setRemark(remark);
		u.setCreateAuthor(createAuthor);
		return u;
	}
	
	public UserDto(User user) {
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setSex(user.getSex());
		this.setEmail(user.getEmail());
		this.setMobilePhone(user.getMobilePhone());
		this.setStatus(user.getStatus());
		this.setRemark(user.getRemark());
		this.setCreateAuthor(user.getCreateAuthor());
	}
	
	public UserDto(User user,List<Integer> roleIds,List<Integer> groupIds) {
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setSex(user.getSex());
		this.setEmail(user.getEmail());
		this.setMobilePhone(user.getMobilePhone());
		this.setStatus(user.getStatus());
		this.setRemark(user.getRemark());
		this.setCreateAuthor(user.getCreateAuthor());
		this.setGroupIds(groupIds);
		this.setRoleIds(roleIds);
	}
	
	public UserDto() {}
}
