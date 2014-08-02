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
	
	private Integer pagerSize;
	
	private Integer currentPage;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			pagerSize = Integer.parseInt(filterConfig.getInitParameter("pagerSize"));
			currentPage = Integer.parseInt(filterConfig.getInitParameter("currentPage"));
		} catch (NumberFormatException e) {
			pagerSize = 10;
			currentPage = 1;
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
			SystemContext.setPageSize(pagerSize);
			SystemContext.setPageStart(offset);
			SystemContext.setCurrentPage(currentPage);
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SystemContext.removeOrder();
			SystemContext.removeSort();
			SystemContext.removePageSize();
			SystemContext.removePageStart();
			SystemContext.removeCurrentPage();
		}
	}

	@Override
	public void destroy() {
		
	}

}
