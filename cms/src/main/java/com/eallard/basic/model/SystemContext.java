package com.eallard.basic.model;

/**
 * 分页参数传送对象
 * @author renzw
 * @
 */
public class SystemContext {

	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	
	private static ThreadLocal<Integer> pageStart = new ThreadLocal<Integer>();
	
	private static ThreadLocal<Integer> totalCount = new ThreadLocal<Integer>();
	
	private static ThreadLocal<Integer> currentPage = new ThreadLocal<Integer>();
	
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	
	private static ThreadLocal<String> order = new ThreadLocal<String>();

	public static Integer getPageSize() {
		return pageSize.get();
	}

	public static void setPageSize(Integer _pageSize) {
		SystemContext.pageSize.set(_pageSize);
	}

	public static Integer getPageStart() {
		return pageStart.get();
	}

	public static void setPageStart(Integer _pageStart) {
		SystemContext.pageStart.set(_pageStart);
	}

	public static String getSort() {
		return sort.get();
	}

	public static void setSort(String _sort) {
		SystemContext.sort.set(_sort);
	}

	public static String getOrder() {
		return order.get();
	}

	public static void setOrder(String _order) {
		SystemContext.order.set(_order);
	}
	
	public static Integer getTotalCount() {
		return totalCount.get();
	}

	public static void setTotalCount(Integer _totalCount) {
		SystemContext.totalCount.set(_totalCount);
	}

	public static Integer getCurrentPage() {
		return currentPage.get();
	}

	public static void setCurrentPage(Integer _currentPage) {
		SystemContext.currentPage.set(_currentPage);
	}

	public static void removeCurrentPage() {
		currentPage.remove();
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static void removePageStart() {
		pageStart.remove();
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static void removeOrder() {
		order.remove();
	}
	
	public static void removeTotalCount() {
		totalCount.remove();
	}
}
