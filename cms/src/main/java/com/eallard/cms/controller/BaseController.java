package com.eallard.cms.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class BaseController {
	/**
	 * 返回json信息给客户端
	 * 
	 * @param response
	 * @param text
	 * @throws Exception
	 */
	public void sendJson(HttpServletResponse response, String text) throws Exception {
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(text);
		out.flush();
		out.close();
	}
}
