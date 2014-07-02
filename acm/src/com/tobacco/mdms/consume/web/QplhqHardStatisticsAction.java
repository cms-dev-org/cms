package com.tobacco.mdms.consume.web;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.web.BizAction;
import org.g4studio.core.web.CommonActionForm;
import org.g4studio.core.web.report.excel.ExcelExporter;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.QplhqHardStatisticsService;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class QplhqHardStatisticsAction extends BizAction {

	private QplhqHardStatisticsService qplhqHardStatisticsService = (QplhqHardStatisticsService)getService("qplhqHardStatisticsService");
	
	public ActionForward mainView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			    throws Exception {
		return mapping.findForward("mainview");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			    throws Exception {
		CommonActionForm aForm = (CommonActionForm)form;
		Dto dto = aForm.getParamAsDto(request);
		
		UserInfoVo user = super.getSessionContainer(request).getUserInfo();
		dto.put("deptid", user.getDeptid());
		dto.put("userid", user.getUserid());
		
		String firstLoad = dto.getAsString("firstLoad");
		String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		if ("1".equals(firstLoad)) {
			dto.put("report_date_start", today);
			dto.put("report_date_end", today);
		}
		
		super.setSessionAttribute(request, "QUERYQPLHQHARDSTATISTICS4EXPORT_EXCEL", dto);
		
		List list = this.g4Reader.queryForPage("QplhqHardStatistics.query", dto);
		Integer countInteger = (Integer)this.g4Reader.queryForObject("QplhqHardStatistics.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, "yyyy.MM.dd");
		super.write(jsonString, response);
		return mapping.findForward(null);
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	    CommonActionForm actionForm = (CommonActionForm)form;
	    Dto inDto = actionForm.getParamAsDto(request);

	    String userId = super.getSessionContainer(request).getUserInfo().getUserid();
	    inDto.put("update_author", userId);
	    inDto.put("enabled", "1");

	    this.qplhqHardStatisticsService.insert(inDto);

		Dto outDto = new BaseDto();
		outDto.put("msg", "添加成功！");
		outDto.put("success", new Boolean(true));
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		  HttpServletResponse response) throws Exception {
		CommonActionForm actionForm = (CommonActionForm)form;
		Dto inDto = actionForm.getParamAsDto(request);
		String ids = inDto.getAsString("ids");

		inDto.put("ids", G4Utils.toInSqlStr(ids));

		Integer result = Integer.valueOf(this.qplhqHardStatisticsService.delete(inDto));
		if (result.intValue() > 0) {
			setOkTipMsg("删除成功！", response);
		} else {
			setErrTipMsg("删除失败！", response);
		}

		return mapping.findForward(null);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		  HttpServletResponse response) throws Exception {
	    CommonActionForm actionForm = (CommonActionForm)form;
	    Dto inDto = actionForm.getParamAsDto(request);
	    inDto.put("update_author", super.getSessionContainer(request).getUserInfo().getUserid());
	    Integer result = Integer.valueOf(this.qplhqHardStatisticsService.update(inDto));
	    if (result.intValue() > 0) {
	    	setOkTipMsg("修改成功！", response);
	    } else {
	    	setErrTipMsg("修改失败！", response);
	    }

	    return mapping.findForward(null);
	}

	public ActionForward exportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		  HttpServletResponse response) throws Exception {
		Dto parametersDto = new BaseDto();
		parametersDto.put("reportTitle", "软哈计数");
		parametersDto.put("jbr", super.getSessionContainer(request).getUserInfo().getUsername());

		Dto inDto = (BaseDto)super.getSessionAttribute(request, "QUERYQPLHQHARDSTATISTICS4EXPORT_EXCEL");

		List fieldsList = this.g4Reader.queryForList("QplhqHardStatistics.query4Export", inDto);
		if (fieldsList != null) {
			for (int i = 1; i <= fieldsList.size(); i++) {
				Dto tmp = (Dto)fieldsList.get(i - 1);
				tmp.put("sn", Integer.valueOf(i));
			}
		}

		Integer qplhqHardCount = (Integer)this.g4Reader.queryForObject("QplhqHardStatistics.queryQplhqHardCount", inDto);
		String reportDate = (String)this.g4Reader.queryForObject("QplhqHardStatistics.queryReportDate", inDto);
		String unit = (String)this.g4Reader.queryForObject("QplhqHardStatistics.queryUnit", inDto);

		parametersDto.put("qplhq_hard_count", qplhqHardCount);
		parametersDto.put("report_date", reportDate);
		parametersDto.put("unit", unit);

		ExcelExporter excelExporter = new ExcelExporter();
		excelExporter.setTemplatePath("/report/excel/app/tobacco/qplhqhardstatistics.xls");
		excelExporter.setData(parametersDto, fieldsList);
		excelExporter.setFilename("七匹狼豪情" + reportDate + "统计数据.xls");
		excelExporter.export(request, response);

		return mapping.findForward(null);
	}

	public ActionForward countNum(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			  HttpServletResponse response) throws Exception {
		Dto inDto = (BaseDto)super.getSessionAttribute(request, "QUERYQPLHQHARDSTATISTICS4EXPORT_EXCEL");
		Integer qplhqHardCount = (Integer)this.g4Reader.queryForObject("QplhqHardStatistics.queryQplhqHardCount", inDto);
		JSONObject obj = new JSONObject();
		obj.accumulate("qplhqHardCount", qplhqHardCount);
		write(obj.toString(), response);
		return mapping.findForward(null);
	}
}
