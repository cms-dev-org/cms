package com.eallard.cms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eallard.cms.dto.UserDto;
import com.eallard.cms.exception.CmsException;
import com.eallard.cms.service.IGroupService;
import com.eallard.cms.service.IRoleService;
import com.eallard.cms.service.IUserService;
import com.eallard.cms.utils.JsonUtil;

/**
 * @author renzw
 * @date 2014-4-30 上午9:23:30 
 */
@Controller
@RequestMapping("/user")
public class UserConstroller extends BaseController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IGroupService groupService;
	
	@RequestMapping("/listMain")
	public String findUsers(Model model) {
		return "admin/user/userList";
	}
	
	@RequestMapping("/listGrid")
	public String findUsersList(Model model) {
		model.addAttribute("pager", userService.findUser());
		return "admin/user/userListGrid";
	}
	
	@RequestMapping(value = "/saveUserInit", method=RequestMethod.GET)
	public String saveUserInit(Model model) {
		
		model.addAttribute("group", groupService.listGroup());
		model.addAttribute("role", roleService.listRole());
		
		return "admin/user/addUser";
	}
	
	@RequestMapping(value = "/saveUser", method=RequestMethod.POST)
	public String saveUser(UserDto userDto) {
		userService.add(userDto.getUser(), userDto.getRoleIds(), userDto.getGroupIds());
		return "redirect:listMain";
	}
	
	/**
	 * 更换用户状态：1启用 0停用
	 * @param response
	 */
	@RequestMapping(value = "/editUserStatus", method=RequestMethod.GET)
	public void editUserStatus(HttpServletResponse response, String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean success = false;
		Integer sign = null;
		try {
			sign = userService.updateStatus(Integer.valueOf(id));
			success = true;
		} catch(Exception e) {
			success = false;
			throw new CmsException("修改用户状态发生异常！");
		}
		result.put("success", success);
		result.put("data", sign);
		try {
			this.sendJson(response, JsonUtil.toJson(result));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CmsException("发送数据异常！");
		}
	}
	
	@RequestMapping(value = "/delUser/{id}", method = RequestMethod.GET)
	public String delUser(@PathVariable Integer id) {
		System.out.println(id);
		userService.delete(Integer.valueOf(id));
		
		return "redirect:listMain";
	}
}








