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
	private int pagerStart;
	
	/** 分页总数 */
	private long totalPage;
	
	/** 总记录数 */
	private long totalSize;
	
	/** 当前页 */
	private int currentPage;
	
	/** 是否有上一页 */
	private boolean hasPrePage;
	
	/** 是否有下一页 */
	private boolean hasNextPage;
	
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

	public int getPagerStart() {
		return pagerStart;
	}

	public void setPagerStart(int pagerStart) {
		this.pagerStart = pagerStart;
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

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
}
