package com.chinasoft.it.wecode.excel.service;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel 数据读写配置
 * 
 * @author Administrator
 *
 */
public class ExcelDataConfig {

	private static final Logger logger = LoggerFactory.getLogger(ExcelDataConfig.class);

	/**
	 * 最小允许的批次处理量
	 */
	public static final int PAGE_MIN = 20;

	/**
	 * 最大允许的批次处理量
	 */
	public static final int PAGE_MAX = 500;

	/**
	 * 批次处理数
	 */
	private int pageSize = 0;

	/**
	 * 当前页
	 */
	private int curPage = 0;

	/**
	 * 分页处理
	 * 
	 * 数据生产时：
	 * 
	 * 数据消费时：
	 */
	private boolean pageable = true;

	/**
	 * 排序信息
	 * TODO:待实现导出排序逻辑
	 */
	private String sort;
	/**
	 * 总记录数
	 * 
	 * 数据生产时：总数用于确定在分页场景下调用的次数
	 * 
	 * 数据消费时：总数用于记录处理的总记录数
	 */
	private AtomicLong total = new AtomicLong(0);

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 批次设置，当值小于或者大于允许的值，会被替换为小于或大于阀值的允许值
	 * 
	 * @param batchSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < PAGE_MIN) {
			logger.warn("page size {} not be little min value {}", pageSize, PAGE_MIN);
			this.pageSize = PAGE_MIN;
		} else if (pageSize > PAGE_MAX) {
			logger.warn("page size {} not be greate max value {}", pageSize, PAGE_MAX);
			this.pageSize = PAGE_MAX;
		} else {
			this.pageSize = pageSize;
		}
	}

	public long getTotal() {
		return total.get();
	}

	public void setTotal(long total) {
		this.total.set(total < 0 ? 0 : total);
	}

	/**
	 * 总数累加（在批量消费时可以实现多线程消费的情况）
	 * 
	 * @param val
	 * @return
	 */
	public long addTotal(long val) {
		if (val < 0)
			throw new IllegalArgumentException("val " + val + " cannot be little min value 0");
		return this.total.addAndGet(val);
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean nextPage() {
		if (!pageable)
			throw new IllegalStateException("pageable is false ,not supoort this method");
		long total = this.total.get();
		if (total == 0)
			return false;

		if (curPage * pageSize < total) {
			curPage++;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public ExcelDataConfig() {
	}

	public ExcelDataConfig(boolean pageable) {
		this.pageable = pageable;
	}

	public ExcelDataConfig(int pageSize, int curPage, boolean pageable, long total) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.pageable = pageable;
		this.total.set(total);
	}

	public static ExcelDataConfig DEFAULT() {
		return new ExcelDataConfig();
	}

	public static ExcelDataConfig PAGE(int total) {
		return new ExcelDataConfig(PAGE_MAX, 0, true, total);
	}

	public static ExcelDataConfig NOPAGE() {
		return new ExcelDataConfig(false);
	}
}
