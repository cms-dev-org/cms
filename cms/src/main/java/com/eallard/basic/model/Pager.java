package com.eallard.basic.model;

import java.util.List;

/**
 * 分页
 * @author renzw
 * @param <T>
 *
 */
public class Pager<T> {
	
	/** 分页大小 */
	private int pagerSize;
	
	/** 分页起始 */
	private int start;
	
	/** 分页总数 */
	private long totalPage;
	
	/** 当前页 */
	private int currentPage;
	
	/** 数据列表 */
	private List<T> data;

	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
