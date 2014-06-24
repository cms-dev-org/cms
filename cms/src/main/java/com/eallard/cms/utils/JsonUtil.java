package com.eallard.cms.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * 处理json与object转换，应用jackson
 * @author luyan
 * @version 1.0
 */
public class JsonUtil {
	/**
	 * 无日期类型对象转换为Json字符串
	 * @param object Object 需要转换的对象，可以是普通object，也可以是map或者list
	 * @param dateFormat DateFormat 如果需要日期转换，需要传该参数
	 * @return String 返回json串
	 * @throws IOException
	 */
	public static String toJson (Object object) throws IOException {
		ObjectMapper mapper = JacksonMapper.getInstance();
		// 解决hibernate延迟加载
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		String json = getJsonStr(mapper, object);
		return json;
	}
	
	/**
	 * 有日期类型对象转换为Json字符串
	 * @param object Object 需要转换的对象，可以是普通object，也可以是map或者list
	 * @param dateFormat DateFormat 如果需要日期转换，需要传该参数
	 * @return String 返回json串
	 * @throws IOException
	 */
	public static String toJson (Object object, DateFormat dateFormat) throws IOException {
		ObjectMapper mapper = JacksonMapper.getInstance();
		// 解决hibernate延迟加载
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		if (dateFormat != null) {
			mapper.setDateFormat(dateFormat);
		} else {
			mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
		String json = getJsonStr(mapper, object);
		return json;
	}
	
	/**
	 * 根据给定的ObjectMapper对象把Object转换为Json字符串
	 * @param mapper ObjectMapper 单独设置时需要的ObjectMapper对象
	 * @param object Object 需要转换的对象，可以是普通object，也可以是map或者list
	 * @return String 返回json串
	 * @throws IOException
	 */
	public static String getJsonStr (ObjectMapper mapper, Object object) throws IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		mapper.writeValue(gen, object);
		gen.close();
		String json = sw.toString();
		return json;
	}
}
