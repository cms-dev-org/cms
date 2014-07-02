package com.tobacco.mdms.consume.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.g4studio.core.json.JsonHelper;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.mvc.xstruts.action.ActionForm;
import org.g4studio.core.mvc.xstruts.action.ActionForward;
import org.g4studio.core.mvc.xstruts.action.ActionMapping;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.web.BizAction;
import org.g4studio.core.web.CommonActionForm;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.BrandInfoService;

/**
 * 卷烟品牌信息
 * @author rzw
 * @since 2013/11/22
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class BrandInfoAction extends BizAction  {
	
	private BrandInfoService brandInfoService = (BrandInfoService) super.getService("brandInfoService");
	
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
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		List list = g4Reader.queryForPage("BrandInfo.query", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("BrandInfo.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, G4Constants.FORMAT_Date_point);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}
	
	public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("update_author", user.getUserid());
		dto.put("enabled", 1);
		brandInfoService.insert(dto);
		setOkTipMsg("修改成功！", response);
		return mapping.findForward(null);
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("update_author", user.getUserid());
		
		brandInfoService.update(dto);
		setOkTipMsg("修改成功！", response);
		return mapping.findForward(null);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		String ids = inDto.getAsString("ids");
		
		inDto.put("ids", G4Utils.toInSqlStr(ids));
		
		Integer result = brandInfoService.delete(inDto);
		if(result > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}
		
		return mapping.findForward(null);
	}
}





