package com.chinasoft.it.wecode.annotations.excel;

public class FiledConstant {
	/**
	 * 操作类型：导出导入
	 */
	public static final int TYPE_DEFAULT = 0;

	/**
	 * 操作类型 ：仅导出
	 */
	public static final int TYPE_EXPORT = 1;

	/**
	 * 操作类型：仅导入
	 */
	public static final int TYPE_IMPORT = 2;

	/**
	 * 导出字段对齐方式：自动
	 */
	public static final int ALIGN_DEFAULT = 0;

	/**
	 * 导出字段对齐方式：靠左
	 */
	public static final int ALIGN_LEFT = 1;

	/**
	 * 导出字段对齐方式：居中
	 */
	public static final int ALIGN_CENTER = 2;

	/**
	 * 导出字段对齐方式：靠右
	 */
	public static final int ALIGN_RIGHT = 3;

	/**
	 * 导出字段单元格默认宽度
	 */
	public static final int WIDTH_DEFAULT = 3000;

	/**
	 * 导出字段单元格默认排序 升序
	 */
	public static final int SORT_DEFAULT = 0;
}
