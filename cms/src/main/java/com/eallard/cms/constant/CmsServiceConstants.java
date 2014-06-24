package com.eallard.cms.constant;

/**
 * 业务常量
 * @author renzw
 *
 */
public class CmsServiceConstants {
	
	/**
	 * 角色分类
	 */
	/** 管理员 */
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	/** 文章发布人员 */
	public static final String ROLE_PUBLISH = "ROLE_PUBLISH";
	/** 文章审核人员 */
	public static final String ROLE_AUDIT = "ROLE_AUDIT";
	
	/**
	 * 用户状态
	 */
	/** 停用（无效） */
	public static final int USER_STATUS_USED = 0;
	/** 启用（有效） */
	public static final int USER_STATUS_USING = 1;
}

