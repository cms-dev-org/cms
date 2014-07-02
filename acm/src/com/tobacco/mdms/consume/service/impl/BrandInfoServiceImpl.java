package com.tobacco.mdms.consume.service.impl;

import org.g4studio.core.metatype.Dto;
import org.g4studio.core.model.service.impl.BizServiceImpl;

import com.tobacco.mdms.consume.service.BrandInfoService;

public class BrandInfoServiceImpl extends BizServiceImpl implements BrandInfoService {

	public void insert(Dto dto) {
		String wholesalePrice = dto.getAsString("wholesale_price");
		String retailPrice = dto.getAsString("retail_price");
		if(wholesalePrice == null || "".equals(wholesalePrice)) {
			dto.put("wholesale_price", 0);
		}
		if(retailPrice == null || "".equals(retailPrice)) {
			dto.put("retail_price", 0);
		}
		g4Dao.insert("BrandInfo.insert", dto);
	}

	public void update(Dto dto) {
		String wholesalePrice = dto.getAsString("wholesale_price");
		String retailPrice = dto.getAsString("retail_price");
		if(wholesalePrice == null || "".equals(wholesalePrice)) {
			dto.put("wholesale_price", 0);
		}
		if(retailPrice == null || "".equals(retailPrice)) {
			dto.put("retail_price", 0);
		}
		g4Dao.update("BrandInfo.update", dto);
	}

	@Override
	public Integer delete(Dto inDto) {
		return g4Dao.update("BrandInfo.delete", inDto);
	}
}
