package com.tobacco.mdms.consume.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.g4studio.core.json.JsonHelper;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.metatype.impl.BaseDto;
import org.g4studio.core.mvc.xstruts.action.ActionForm;
import org.g4studio.core.mvc.xstruts.action.ActionForward;
import org.g4studio.core.mvc.xstruts.action.ActionMapping;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.web.BizAction;
import org.g4studio.core.web.CommonActionForm;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.ConsumeCustomerService;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class ConsumeCustomerAction extends BizAction  {
	
	private ConsumeCustomerService consumeCustomerService = (ConsumeCustomerService)getService("consumeCustomerService");
	
	/**
	 * 主界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward mainView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("mainview");
	}
	
	/**
	 * 查询
	 * @param mapping
	 * @param form
	 * @param reuqest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("deptid", user.getDeptid());
		dto.put("userid", user.getUserid());
		
		List list = g4Reader.queryForPage("Customer.query", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("Customer.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, G4Constants.FORMAT_DateTime);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}
	/**
	 * 新增客户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		
		String userId = super.getSessionContainer(request).getUserInfo().getUserid();
		inDto.put("update_author", userId);
		inDto.put("enabled", "1");
		
		consumeCustomerService.insert(inDto);
		
		Dto outDto = new BaseDto();
		outDto.put("msg", "新增客户信息成功！");
		outDto.put("success", new Boolean(true));
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * 删除客户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		String ids = inDto.getAsString("ids");
		
		inDto.put("ids", G4Utils.toInSqlStr(ids));
		
		Integer result = consumeCustomerService.delete(inDto);
		if(result > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}
		
		return mapping.findForward(null);
	}
	
	/**
	 * 修改客户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		inDto.put("update_author", super.getSessionContainer(request).getUserInfo().getUserid());
		Integer result = consumeCustomerService.update(inDto);
		if(result > 0) {
			setOkTipMsg("修改成功！", response);
		} else {
			setErrTipMsg("修改失败！", response);
		}
		
		return mapping.findForward(null);
	}
	
	/**
	 * 检查客户编码是否已经存在
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCustomerCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		
		List list = g4Reader.queryForList("Customer.checkCustomerCode");
		String customerCode = inDto.getAsString("customerCode").trim();
		if(list.contains(customerCode)) {
			setOkTipMsg("1", response);
		} else {
			setOkTipMsg("0", response);
		}
		return mapping.findForward(null);
	}
		
}
