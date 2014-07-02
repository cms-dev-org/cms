package com.tobacco.mdms.consume.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.g4studio.core.json.JsonHelper;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.metatype.impl.BaseDto; 
import org.g4studio.core.mvc.xstruts.action.ActionForm;
import org.g4studio.core.mvc.xstruts.action.ActionForward;
import org.g4studio.core.mvc.xstruts.action.ActionMapping;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.web.BizAction;
import org.g4studio.core.web.CommonActionForm;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.ConsumeDistributionService;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class ConsumeDistributionAction extends BizAction  {
	
	private ConsumeDistributionService consumeDistributionService = 
			(ConsumeDistributionService) super.getService("consumeDistributionService");
	
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
	 * 新增分配
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
		
		consumeDistributionService.insert(inDto);
		
		setOkTipMsg("分配成功！", response);
		
		return mapping.findForward(null);
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		dto.put("user", user);
		
		String result = consumeDistributionService.query(dto);
		super.write(result, response);
		return mapping.findForward(null);
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("update_author", user.getUserid());
		
		consumeDistributionService.update(dto);
		
		return mapping.findForward(null);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		
		Integer result = consumeDistributionService.delete(inDto);
		if(result > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}
		return mapping.findForward(null);
	}
	
	public ActionForward queryDistributeAuthor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		String deptId = user.getDeptid();
		
		Dto dto = new BaseDto();
		dto.put("deptid", deptId);
		
		String result = consumeDistributionService.querySubUser(dto);
		super.write(result, response);
		return mapping.findForward(null);
	}
	
	public ActionForward querySubUserDistributeAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		List list = consumeDistributionService.querySubUserDistributeAmount(dto);
		String result = JsonHelper.encodeList2PageJson(list, list.size(), G4Constants.FORMAT_Date_point);
		super.write(result, response);
		return mapping.findForward(null);
	}
	
	public ActionForward distribute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("update_author", user.getUserid());
		consumeDistributionService.distribute(dto);
		
		return mapping.findForward(null);
	}
	
	public ActionForward queryDistribute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		List list = consumeDistributionService.queryDistribute(dto);
		super.setSessionAttribute(request, "QUERYDISTRIBUTEDATA", dto);//
		String result = JsonHelper.encodeList2PageJson(list, list.size(), G4Constants.FORMAT_Date_point);
		super.write(result, response);
		return mapping.findForward(null);
	}
	
	public ActionForward countNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = (BaseDto) super.getSessionAttribute(request, "QUERYDISTRIBUTEDATA");
		Integer hardBoxCount = (Integer) g4Reader.queryForObject("Distribution.queryHardBoxCount", inDto);
		Integer softBoxCount = (Integer) g4Reader.queryForObject("Distribution.querySoftBoxCount", inDto);
		JSONObject obj = new JSONObject();
		obj.accumulate("hardbox", hardBoxCount);
		obj.accumulate("softbox", softBoxCount);
		write(obj.toString(), response);
		return mapping.findForward(null);
	}
}
























