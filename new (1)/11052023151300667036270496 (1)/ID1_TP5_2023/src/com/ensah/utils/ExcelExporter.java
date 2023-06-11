//package com.ensah.utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//
//public class ExcelExporter {
//	private XSSFWorkbook workbook;
//	private XSSFSheet sheet;
//
//	private String[] columnNames;
//	private String[][] data;
//	private String sheetName = "";
//
//	public ExcelExporter(String[] columnNames, String[][] data, String sheetName) {
//		this.columnNames = columnNames;
//		this.data = data;
//		this.sheetName = sheetName;
//		workbook = new XSSFWorkbook();
//
//	}
//
//	public void export(String fileName) throws IOException {
//		writeHeaderLine();
//		writeDataLines();
//
//		OutputStream outputStream = new FileOutputStream(fileName);
//		workbook.write(outputStream);
//		workbook.close();
//
//		outputStream.close();
//
//	}
//
//	private void writeHeaderLine() {
//		sheet = workbook.createSheet(sheetName);
//
//		Row row = sheet.createRow(0);
//
//		CellStyle style = workbook.createCellStyle();
//		XSSFFont font = workbook.createFont();
//		font.setBold(true);
//		font.setFontHeight(16);
//		style.setFont(font);
//
//		int i = 0;
//		for (String it : columnNames) {
//			createCell(row, (i++), it, style);
//		}
//
//	}
//
//	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//		sheet.autoSizeColumn(columnCount);
//		Cell cell = row.createCell(columnCount);
//		if (value instanceof Integer) {
//			cell.setCellValue((Integer) value);
//		} else if (value instanceof Boolean) {
//			cell.setCellValue((Boolean) value);
//		} else {
//			cell.setCellValue((String) value);
//		}
//		cell.setCellStyle(style);
//	}
//
//	private void writeDataLines() {
//		int rowCount = 1;
//
//		CellStyle style = workbook.createCellStyle();
//		XSSFFont font = workbook.createFont();
//		font.setFontHeight(14);
//		style.setFont(font);
//
//		for (int i = 0; i < data.length; i++) {
//			Row row = sheet.createRow(rowCount++);
//			int columnCount = 0;
//			for (int j = 0; j < data[i].length; j++) {
//				createCell(row, columnCount++, data[i][j], style);
//			}
//		}
//
//	}
//
//
//}
