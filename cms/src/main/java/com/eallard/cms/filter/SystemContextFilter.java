package com.eallard.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.eallard.basic.model.SystemContext;

/**
 * 
 * @author renzw
 * @date 2014-4-30 下午8:04:40 
 */
public class SystemContextFilter implements Filter {
	
	private Integer pageSize;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			pageSize = Integer.parseInt(filterConfig.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 15;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Integer offset = 0;
		try {
			offset = Integer.parseInt(request.getParameter("pager.start"));
		} catch (NumberFormatException e) {}
		
		try {
			SystemContext.setOrder(request.getParameter("order"));
			SystemContext.setSort(request.getParameter("sort"));
			SystemContext.setPageSize(pageSize);
			SystemContext.setPageStart(offset);
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SystemContext.removeOrder();
			SystemContext.removeSort();
			SystemContext.removePageSize();
			SystemContext.removePageStart();
		}
	}

	@Override
	public void destroy() {
		
	}

}
