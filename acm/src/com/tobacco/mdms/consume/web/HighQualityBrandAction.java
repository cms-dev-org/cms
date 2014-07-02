package com.tobacco.mdms.consume.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import com.tobacco.mdms.consume.service.HighQualityBrandService;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class HighQualityBrandAction extends BizAction {
	
	private  HighQualityBrandService highQualityBrandService = (HighQualityBrandService) super.getService("highQualityBrandService");

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
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		Dto dto = new BaseDto();
		dto.put("update_author", user.getUserid());
		
		dto.put("type", 0);
		List<Dto> powerBrand = g4Reader.queryForList("HighQualityBrand.queryBrand", dto);
		dto.put("type", 1);
		List<Dto> policyBrand = g4Reader.queryForList("HighQualityBrand.queryBrand", dto);
		
		String powerBrandJson = JsonHelper.encodeList2PageJson(powerBrand, powerBrand.size(), G4Constants.FORMAT_Date_point);
		String policyBrandJson = JsonHelper.encodeList2PageJson(policyBrand, policyBrand.size(), G4Constants.FORMAT_Date_point);
		
		request.setAttribute("powerBrandJson", powerBrandJson);
		request.setAttribute("policyBrandJson", policyBrandJson);
		
		return mapping.findForward("mainview");
	}
	
	public ActionForward querySelectedBrand(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		dto.put("update_author", user.getUserid());
		
		List list = g4Reader.queryForList("HighQualityBrand.querySelectedPowerBrand", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, list.size(), G4Constants.FORMAT_Date_point);
		super.write(jsonString, response);
		
		return mapping.findForward(null);
	}
	
	/**
	 * 设置品牌 查询所有品牌信息（除去已经选择的品牌信息）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryBrand(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		dto.put("update_author", user.getUserid());
		
		List<Dto> list = g4Reader.queryForList("HighQualityBrand.queryBrandRemoveSelected", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, list.size(), G4Constants.FORMAT_Date_point);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * 设置动力品牌和政策品牌
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSelectedBrand(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);

		String selectedBrandData = dto.getAsString("selectedBrandData");
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		List<Dto> selectedBrandList = JsonHelper.parseJson2List(selectedBrandData);
		for(Dto item : selectedBrandList) {
			item.put("type", dto.getAsString("type"));
			item.put("update_author", user.getUserid());
		}
		dto.put("update_author", user.getUserid());
		dto.put("selectedBrandList", selectedBrandList);
		highQualityBrandService.insertSelectedBrand(dto);
		return mapping.findForward(null);
	}
	
	/**
	 * 添加优质货源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		dto.put("enabled", 1);
		dto.put("update_author", user.getUserid());
		
		highQualityBrandService.addHighQuality(dto);
		
		Dto outDto = new BaseDto();
		outDto.put("msg", "添加成功！");
		outDto.put("success", new Boolean(true));
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		
		return mapping.findForward(null);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		
		dto.put("enabled", 1);
		dto.put("update_author", user.getUserid());
		
		List<String> nameList = new ArrayList<String>();
		Set set = dto.keySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = dto.getAsString(key);
			
			if(key.startsWith("name")) {
				nameList.add(value);
			}
		}
		dto.put("nameList", nameList);
		
		List<Dto> list = g4Reader.queryForPage("HighQualityBrand.query", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("HighQualityBrand.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, G4Constants.FORMAT_Date_point);
		
		super.write(jsonString, response);
		
		return mapping.findForward(null);
	}
	
	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		inDto.put("update_author", super.getSessionContainer(request).getUserInfo().getUserid());
		Integer result = highQualityBrandService.update(inDto);
		if(result > 0) {
			setOkTipMsg("修改成功！", response);
		} else {
			setErrTipMsg("修改失败！", response);
		}
		
		return mapping.findForward(null);
	}
	
	/**
	 * 删除信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		String ids = inDto.getAsString("ids");
		
		inDto.put("ids", G4Utils.toInSqlStr(ids));
		
		Integer result = highQualityBrandService.delete(inDto);
		if(result > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}
		
		return mapping.findForward(null);
	}
}


