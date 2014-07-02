package com.tobacco.mdms.consume.service.impl;

import org.g4studio.core.metatype.Dto;
import org.g4studio.core.model.service.impl.BizServiceImpl;

import com.tobacco.mdms.consume.service.ConsumeCustomerService;

public class ConsumeCustomerServiceImpl extends BizServiceImpl implements ConsumeCustomerService {

	//新增客户信息
	public void insert(Dto inDto) {
		g4Dao.insert("Customer.insert", inDto);
	}

	//删除客户信息
	public int delete(Dto inDto) {
		return g4Dao.update("Customer.delete", inDto);
	}

	//修改客户信息
	public int update(Dto inDto) {
		return g4Dao.update("Customer.update", inDto);
	}

}
