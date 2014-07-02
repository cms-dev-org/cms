package com.tobacco.mdms.consume.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.g4studio.core.metatype.Dto;
import org.g4studio.core.metatype.impl.BaseDto;
import org.g4studio.core.model.service.impl.BizServiceImpl;
import org.g4studio.core.util.G4Utils;

import com.tobacco.mdms.consume.service.HighQualityBrandService;

public class HighQualityBrandServiceImpl extends BizServiceImpl
	implements HighQualityBrandService {

	/**
	 * 添加动力或政策品牌
	 */
	public void insertSelectedBrand(Dto dto) {
		List<Dto> selectedBrandList = (List<Dto>) dto.get("selectedBrandList");
		
		g4Dao.delete("HighQualityBrand.deleteSelectedBrand", dto);
		for(Dto item : selectedBrandList) {
			g4Dao.insert("HighQualityBrand.insertSelectedBrand", item);
		}
	}

	/**
	 * 添加优质货源主表
	 */
	public void addHighQuality(Dto dto) {
		String id = (String)g4Dao.insert("HighQualityBrand.insertHighQualityMain", dto);
		
		Set set = dto.keySet();
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			String value = dto.getAsString(key);
			
			if(!G4Utils.isEmpty(key) && key.startsWith("name")) {
				Dto tmp = new BaseDto();
				tmp.put("high_quality_main_id", id);
				tmp.put("brand_id", key.substring(4));
				tmp.put("num", value);
				tmp.put("update_author", dto.getAsString("update_author"));
				g4Dao.insert("HighQualityBrand.insertHightQualityNum", tmp);
			}
		}
	}

	/**
	 * 修改
	 */
	public Integer update(Dto dto) {
		Set set = dto.keySet();
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			String value = dto.getAsString(key);
			
			if(!G4Utils.isEmpty(key) && key.startsWith("name")) {
				Dto tmp = new BaseDto();
				tmp.put("high_quality_main_id", dto.getAsString("id"));
				tmp.put("brand_id", key.substring(4));
				tmp.put("distribute_num", value);
				tmp.put("update_author", dto.getAsString("update_author"));
				g4Dao.update("HighQualityBrand.updateNum", tmp);
			}
		}
		return g4Dao.update("HighQualityBrand.updateMain", dto);
	}

	/**
	 * 删除
	 */
	public Integer delete(Dto inDto) {
		return g4Dao.update("HighQualityBrand.delete", inDto);
	}
	
	
}
