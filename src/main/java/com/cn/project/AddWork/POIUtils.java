package com.cn.project.AddWork;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIUtils {
	public class XSSFDateUtil extends DateUtil {

	}

	public static void copyCellStyle(XSSFCellStyle fromStyle, XSSFCellStyle toStyle) {
		toStyle.cloneStyleFrom(fromStyle);// 此一行代码搞定
	}

	public static void mergeSheetAllRegion(XSSFSheet fromSheet, XSSFSheet toSheet) {// 合并单元格
		int num = fromSheet.getNumMergedRegions();
		CellRangeAddress cellR = null;
		for (int i = 0; i < num; i++) {
			cellR = fromSheet.getMergedRegion(i);
			toSheet.addMergedRegion(cellR);
		}
	}

	public static void mergeSheetAllRegion(XSSFSheet fromSheet, XSSFSheet toSheet, int fromSheetStart) {
		// 合并单元格

		int num = fromSheet.getNumMergedRegions();
		CellRangeAddress cellR = null;
		for (int i = 0; i < num; i++) {
			cellR = fromSheet.getMergedRegion(i);
			cellR.setFirstRow(cellR.getFirstRow() + fromSheetStart);
			cellR.setLastRow(cellR.getLastRow() + fromSheetStart);
			toSheet.addMergedRegion(cellR);
		}
		XSSFRow row = fromSheet.getRow(0);
		int coloumNum = row.getPhysicalNumberOfCells();
		for (int i = 0; i < coloumNum; i++) {
			toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
		}
	}

	public static void copyCell(XSSFWorkbook wb, XSSFCell fromCell, XSSFCell toCell) {
		XSSFCellStyle newstyle = wb.createCellStyle();
		copyCellStyle(fromCell.getCellStyle(), newstyle);
		CopyClass.copy(fromCell.getCellStyle(), newstyle);
		// toCell.setEncoding(fromCell.getEncoding());
		// 样式
		toCell.setCellStyle(newstyle);
		
		if (fromCell.getCellComment() != null) {
			toCell.setCellComment(fromCell.getCellComment());
		}
		
		// 不同数据类型处理
		int fromCellType = fromCell.getCellType();
		toCell.setCellType(fromCellType);
		if (fromCellType == XSSFCell.CELL_TYPE_NUMERIC) {
			if (XSSFDateUtil.isCellDateFormatted(fromCell)) {
				toCell.setCellValue(fromCell.getDateCellValue());
			} else {
				toCell.setCellValue(fromCell.getNumericCellValue());
			}
		} else if (fromCellType == XSSFCell.CELL_TYPE_STRING) {
			toCell.setCellValue(fromCell.getRichStringCellValue());
		} else if (fromCellType == XSSFCell.CELL_TYPE_BLANK) {
			// nothing21
		} else if (fromCellType == XSSFCell.CELL_TYPE_BOOLEAN) {
			toCell.setCellValue(fromCell.getBooleanCellValue());
		} else if (fromCellType == XSSFCell.CELL_TYPE_ERROR) {
			toCell.setCellErrorValue(fromCell.getErrorCellValue());
		} else if (fromCellType == XSSFCell.CELL_TYPE_FORMULA) {
			toCell.setCellFormula(fromCell.getCellFormula());
		} else { // nothing29

		}

	}

	public static void copyRow(XSSFWorkbook wb, XSSFRow oldRow, XSSFRow toRow) {
		toRow.setHeight(oldRow.getHeight());
		toRow.setHeightInPoints(toRow.getHeightInPoints());
		for (Iterator<Cell> cellIt = oldRow.cellIterator(); cellIt.hasNext();) {
			XSSFCell tmpCell = (XSSFCell) cellIt.next();
			XSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(wb, tmpCell, newCell);
		}
	}

	public static void copySheet(XSSFWorkbook wb, XSSFSheet fromSheet, XSSFSheet toSheet) {
		mergeSheetAllRegion(fromSheet, toSheet);
		// 设置列宽
		for (int i = 0; i <= fromSheet.getRow(fromSheet.getFirstRowNum()).getLastCellNum(); i++) {
			toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
		}
		for (Iterator<Row> rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			XSSFRow oldRow = (XSSFRow) rowIt.next();
			XSSFRow newRow = toSheet.createRow(oldRow.getRowNum());
			copyRow(wb, oldRow, newRow);
		}
	}

}