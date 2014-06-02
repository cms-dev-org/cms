package com.eallard.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author renzw
 * @date 2014-4-27 下午3:42:52
 */
@Controller
public class AdminController {

	@RequestMapping("/admin")
	public String login() {
		
		return "admin/login";
	}
	
	@RequestMapping("/index")
	public String index() {
		
		return "admin/index";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "admin/main";
	}
}
