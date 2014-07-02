package com.tobacco.mdms.consume.service.impl;

import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.g4studio.core.json.JsonHelper;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.metatype.impl.BaseDto;
import org.g4studio.core.model.service.impl.BizServiceImpl;
import org.g4studio.core.properties.PropertiesFactory;
import org.g4studio.core.properties.PropertiesFile;
import org.g4studio.core.properties.PropertiesHelper;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;
import org.g4studio.system.common.dao.vo.UserInfoVo;

import com.tobacco.mdms.consume.service.ConsumeDistributionService;

@SuppressWarnings("unchecked")
public class ConsumeDistributionServiceImpl extends BizServiceImpl implements ConsumeDistributionService {

	//查询当前用户子用户
	@SuppressWarnings("rawtypes")
	public String querySubUser(Dto dto) {
		JSONArray result = new JSONArray();
		
		List list = g4Dao.queryForList("Distribution.querySubUser", dto);
		
		if(list != null) {
			for(int i=0; i<list.size(); i++) {
				Dto item = (Dto)list.get(i);
				JSONArray tmp = new JSONArray();
				tmp.add(item.get("userid"));
				tmp.add(item.get("username"));
				result.add(tmp);
			}
		}
		return result.toString();
	}

	//添加分配
	public void insert(Dto inDto) {
		g4Dao.insert("Distribution.insert", inDto);
	}

	//查询分配
	public String query(Dto dto) {
		
		UserInfoVo user = (UserInfoVo) dto.get("user");
		String userId = user.getUserid();
		
		dto.put("userid", userId);
		
		PropertiesHelper g4PHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.TOBACCO);
		String queryUserIds = g4PHelper.getValue("tobacco.distributequery");
		//根据配置 查看分配 配置的用户可以查看所有分配信息
		if(queryUserIds.indexOf(userId) != -1) {
			dto.put("all", "1");
		}
		List list = null;
		try {
			list = g4Dao.queryForPage("Distribution.query", dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer countInteger = (Integer) g4Dao.queryForObject("Distribution.queryCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, G4Constants.FORMAT_Date_point);
		return jsonString;
	}

	//修改
	public void update(Dto dto) {
		g4Dao.update("Distribution.update", dto);
	}

	//删除
	public Integer delete(Dto inDto) {
		return g4Dao.delete("Distribution.delete", inDto);
	}

	
	public List querySubUserDistributeAmount(Dto dto) {
		String userId = dto.getAsString("distributeAuthor");
		Dto d = new BaseDto();
		d.put("userid", userId);
		String deptId = (String)g4Dao.queryForObject("Distribution.queryDeptIdByUser", d);
		dto.put("deptid", deptId);
		
		return g4Dao.queryForList("Distribution.querySubUserDistributeAmount", dto);
	}

	//分配卷烟
	public void distribute(Dto dto) {
		String distributeType = dto.getAsString("type");
		String distributeDate = dto.getAsString("date");
		String distriubteData = dto.getAsString("distributeData");
		String zhHardBox = dto.getAsString("zhHardBox");
		String zhSoftBox = dto.getAsString("zhSoftBox");
		String unit = dto.getAsString("unit");
		String updateAuthor = dto.getAsString("update_author");
		String author = dto.getAsString("pAuthor");
		double zhHardBoxSum = 0;
		double zhSoftBoxSum = 0;
		
		JSONArray dataArr = JSONArray.fromObject(distriubteData);
		
		double[] zhHardBoxAmount = new double[dataArr.size()];
		double[] zhSoftBoxAmount = new double[dataArr.size()];
		
		for(int i=0; i<dataArr.size(); i++) {
			JSONObject item = (JSONObject) dataArr.get(i);
			double zh_hardbox = Double.valueOf(item.getString("zh_hardbox"));
			zhHardBoxAmount[i] = zh_hardbox;
			double zh_softbox = Double.valueOf(item.getString("zh_softbox"));
			zhSoftBoxAmount[i] = zh_softbox;
		}
		
		for(int i=0; i<dataArr.size(); i++) {
			zhHardBoxSum += zhHardBoxAmount[i];
			zhSoftBoxSum += zhSoftBoxAmount[i];
		}
		
		if("1".equals(distributeType)) {
			for(int i=0; i<dataArr.size(); i++) {
				JSONObject item = (JSONObject) dataArr.get(i);
				Dto dataItem = new BaseDto();
				dataItem.put("pAuthor", author);//分配人
				dataItem.put("update_author", updateAuthor);
				dataItem.put("distribute_date", distributeDate);
				dataItem.put("userid", item.getString("author"));
				dataItem.put("zh_hardbox", item.getString("zh_hardbox"));
				dataItem.put("zh_softbox", item.getString("zh_softbox"));
				dataItem.put("zh_hardbox_distribute", item.getString("zh_hardbox"));
				dataItem.put("zh_softbox_distribute", item.getString("zh_softbox"));
				dataItem.put("unit", unit);
				dataItem.put("type", distributeType);
				insertData(dataItem);
			}
			
		} else {
			for(int i=0; i<dataArr.size(); i++) {
				JSONObject item = (JSONObject) dataArr.get(i);
				Dto dataItem = new BaseDto();
				double hardRate = 0.0, softRate = 0.0;
				if(zhHardBoxSum != 0.0) {
					hardRate = Double.valueOf(item.getString("zh_hardbox")) / Double.valueOf(zhHardBoxSum);
				}
				if(zhSoftBoxSum != 0.0) {
					softRate = Integer.valueOf(item.getString("zh_softbox")) / Double.valueOf(zhSoftBoxSum);
				}
				
				dataItem.put("pAuthor", author);//分配人
				dataItem.put("update_author", updateAuthor);
				dataItem.put("distribute_date", distributeDate);
				dataItem.put("userid", item.getString("author"));
				dataItem.put("zh_hardbox", item.getString("zh_hardbox"));
				dataItem.put("zh_softbox", item.getString("zh_softbox"));
				dataItem.put("zh_hardbox_distribute", hardRate * Integer.valueOf(zhHardBox));
				dataItem.put("zh_softbox_distribute", softRate * Integer.valueOf(zhSoftBox));
				dataItem.put("unit", unit);
				dataItem.put("type", distributeType);
				insertData(dataItem);
			}
		}
	}
	
	public List queryDistribute(Dto dto) {
		String userId = dto.getAsString("distributeAuthor");
		Dto d = new BaseDto();
		d.put("userid", userId);
		String deptId = (String)g4Dao.queryForObject("Distribution.queryDeptIdByUser", d);
		dto.put("deptid", deptId);
		
		return g4Dao.queryForList("Distribution.queryDistribute", dto);
	}
	
	/**
	 * 插入分配比例数据和分配数据
	 * @param dataItem
	 */
	private void insertData(Dto dataItem) {
		String isRateExist = (String)g4Dao.queryForObject("Distribution.isDistributeDataExist", dataItem);
		if(G4Utils.isEmpty(isRateExist)) {//id为空 新增记录
			g4Dao.insert("Distribution.insertDistributeRate", dataItem);
		} else {//id不为空 修改记录
			dataItem.put("rateid", isRateExist);
			g4Dao.update("Distribution.updateDistributeRate", dataItem);
		}
		
		String isDistributeExist = (String)g4Dao.queryForObject("Distribution.isDistributeExist", dataItem);
		if(G4Utils.isEmpty(isDistributeExist)) {
			g4Dao.insert("Distribution.insertDistribute", dataItem);
		} else {
			dataItem.put("distributeid", isDistributeExist);
			g4Dao.update("Distribution.updateDistribute", dataItem);
		}
	}

}







