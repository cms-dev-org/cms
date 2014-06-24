package com.eallard.cms.utils;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 用于获得ObjectMapper(json转换时)
 * @author luyan
 * @version 1.0
 */
public class JacksonMapper {

	/** 
     *  
     */
	private static final ObjectMapper mapper = new ObjectMapper();

	/** 
     *  
     */
	private JacksonMapper() {

	}

	/**
	 * 返回ObjectMapper实例
	 * @return
	 */
	public static ObjectMapper getInstance() {
		return mapper;
	}

}
