package com.chinasoft.it.wecode.excel.template.utils;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class POIUtils {

	/**
	 * 行复制功能
	 *
	 * @param fromRow
	 * @param toRow
	 */
	public static void copyRow(Row fromRow, Row toRow, boolean copyValueFlag) {
		Sheet worksheet = fromRow.getSheet();
		Sheet newSheet = toRow.getSheet();
		toRow.setHeight(fromRow.getHeight());
		for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			System.out.println("iterater cell " + System.currentTimeMillis());
			Cell tmpCell = (Cell) cellIt.next();
			Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(newSheet, tmpCell, newCell, copyValueFlag);
		}
		for (int i = 0, j = worksheet.getNumMergedRegions(); i < j; i++) {
			System.out.println("num meged " + i + " total:" + worksheet.getNumMergedRegions());
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(),
						(toRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
						cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
				newSheet.addMergedRegionUnsafe(newCellRangeAddress);
			}
		}
	}

	/**
	 * 复制单元格
	 *
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(Sheet newSheet, Cell srcCell, Cell distCell, boolean copyValueFlag) {
		CellStyle cellStyle = srcCell.getCellStyle();

		// 样式
		CellStyle createCellStyle = newSheet.getWorkbook().createCellStyle();
		createCellStyle.cloneStyleFrom(cellStyle);

		distCell.setCellStyle(createCellStyle);

		// 评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}

		// 不同数据类型处理
		CellType srcCellType = srcCell.getCellTypeEnum();
		distCell.setCellType(srcCellType);

		if (copyValueFlag) {
			copyCellValue(srcCell, distCell);
		}
	}

	public static void copyCellValue(Cell srcCell, Cell distCell) {
		CellType srcCellType = srcCell.getCellTypeEnum();

		if (!distCell.getCellTypeEnum().equals(srcCellType)) {
			distCell.setCellType(srcCellType);
		}

		if (srcCellType == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(srcCell)) {
				distCell.setCellValue(srcCell.getDateCellValue());
			} else {
				distCell.setCellValue(srcCell.getNumericCellValue());
			}
		} else if (srcCellType == CellType.STRING) {
			distCell.setCellValue(srcCell.getRichStringCellValue());
		} else if (srcCellType == CellType.BLANK) {

		} else if (srcCellType == CellType.BOOLEAN) {
			distCell.setCellValue(srcCell.getBooleanCellValue());
		} else if (srcCellType == CellType.ERROR) {
			distCell.setCellErrorValue(srcCell.getErrorCellValue());
		} else if (srcCellType == CellType.FORMULA) {
			distCell.setCellFormula(srcCell.getCellFormula());
		} else {
		}
	}

	private static final DecimalFormat df = new DecimalFormat("0");

	public static Object getCellValue(Cell cell) {
		CellType type = cell.getCellTypeEnum();
		if (type == CellType.NUMERIC) {
			return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue()
					: Double.parseDouble(df.format(cell.getNumericCellValue()));
		} else if (type == CellType.STRING) {
			return cell.getRichStringCellValue();
		} else if (type == CellType.BLANK) {
			return "";
		} else if (type == CellType.BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (type == CellType.ERROR) {
			return cell.getErrorCellValue();
		} else if (type == CellType.FORMULA) {
			return cell.getCellFormula();
		}
		throw new IllegalArgumentException("not support cell type of " + type);
	}

	public static void setCellValue(Cell cell, Object value) {
		CellType type = cell.getCellTypeEnum();
		if (value == null) {
			cell.setCellValue("");
		}
		throw new IllegalArgumentException("not support cell type of " + type);
	}
}
