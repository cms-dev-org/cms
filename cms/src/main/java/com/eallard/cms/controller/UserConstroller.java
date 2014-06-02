package com.eallard.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eallard.cms.service.IGroupService;
import com.eallard.cms.service.IRoleService;
import com.eallard.cms.service.IUserService;

/**
 * 
 * @author renzw
 * @date 2014-4-30 上午9:23:30 
 */
@Controller
@RequestMapping("/user")
public class UserConstroller {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IGroupService groupService;
	
	@RequestMapping("/list")
	public String findUsers(Model model) {
		model.addAttribute("datas", userService.findUser());
		return "admin/user/userList";
	}
	
	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public String addUser(Model model) {
		
		model.addAttribute("group", groupService.listGroup());
		model.addAttribute("role", roleService.listRole());
		
		return "admin/user/addUser";
	}
}
