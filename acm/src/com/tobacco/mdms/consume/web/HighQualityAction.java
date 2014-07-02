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
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.web.BizAction;
import org.g4studio.core.web.CommonActionForm;
import org.g4studio.core.web.report.excel.ExcelExporter;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.HighQualityService;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class HighQualityAction extends BizAction  {
	
	private HighQualityService highQualityService = (HighQualityService)getService("highQualityService");
	
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
		
		super.setSessionAttribute(request, "QUERYCONSUMEAPPROVAL4EXPORT_EXCEL", dto);//
		
		List list = g4Reader.queryForPage("HighQuality.query", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("HighQuality.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, G4Constants.FORMAT_Date_point);
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
		
		highQualityService.insert(inDto);
		
		Dto outDto = new BaseDto();
		outDto.put("msg", "添加成功！");
		outDto.put("success", new Boolean(true));
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
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
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm actionForm = (CommonActionForm) form;
		Dto inDto = actionForm.getParamAsDto(request);
		String ids = inDto.getAsString("ids");
		
		inDto.put("ids", G4Utils.toInSqlStr(ids));
		
		Integer result = highQualityService.delete(inDto);
		if(result > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}
		
		return mapping.findForward(null);
	}
	
	/**
	 * 修改信息
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
		Integer result = highQualityService.update(inDto);
		if(result > 0) {
			setOkTipMsg("修改成功！", response);
		} else {
			setErrTipMsg("修改失败！", response);
		}
		
		return mapping.findForward(null);
	}
	
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Dto parametersDto = new BaseDto();
		parametersDto.put("reportTitle", "高密公司集团消费审批表");
		parametersDto.put("jbr", super.getSessionContainer(request).getUserInfo().getUsername());
		
		Dto inDto = (BaseDto) super.getSessionAttribute(request, "QUERYCONSUMEAPPROVAL4EXPORT_EXCEL");
		
		List fieldsList = g4Reader.queryForList("HighQuality.query4Export", inDto);
		if(fieldsList != null) {
			for(int i=1; i<=fieldsList.size(); i++) {
				Dto tmp = (Dto)fieldsList.get(i - 1);
				tmp.put("sn", i);
			}
		}
		
		Integer hardBoxCount = (Integer) g4Reader.queryForObject("HighQuality.queryHardBoxCount", inDto);
		Integer softBoxCount = (Integer) g4Reader.queryForObject("HighQuality.querySoftBoxCount", inDto);
		String reportDate = (String) g4Reader.queryForObject("HighQuality.queryReportDate", inDto);
		String unit = (String) g4Reader.queryForObject("HighQuality.queryUnit", inDto);
		
		parametersDto.put("hard_count", hardBoxCount);// 硬盒总数
		parametersDto.put("soft_count", softBoxCount);// 软盒总数
		parametersDto.put("report_date", reportDate);//上报时间
		parametersDto.put("unit", unit);//单位
		
		ExcelExporter excelExporter = new ExcelExporter();
		excelExporter.setTemplatePath("/report/excel/app/tobacco/consumeapproval.xls");
		excelExporter.setData(parametersDto, fieldsList);
		excelExporter.setFilename("高密公司集团" + reportDate + "消费审批表.xls");
		excelExporter.export(request, response);
		
		return mapping.findForward(null);
	}
	
	public ActionForward countNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = (BaseDto) super.getSessionAttribute(request, "QUERYCONSUMEAPPROVAL4EXPORT_EXCEL");
		Integer hardBoxCount = (Integer) g4Reader.queryForObject("HighQuality.queryHardBoxCount", inDto);
		Integer softBoxCount = (Integer) g4Reader.queryForObject("HighQuality.querySoftBoxCount", inDto);
		JSONObject obj = new JSONObject();
		obj.accumulate("hardbox", hardBoxCount);
		obj.accumulate("softbox", softBoxCount);
		write(obj.toString(), response);
		return mapping.findForward(null);
	}
}






















