package user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SxssfDemo {

	public static void main(String[] args) throws InvalidFormatException, IOException { 
		XSSFWorkbook template = new XSSFWorkbook(new File("D:\\excel\\tl\\t.xlsx"));
		SXSSFWorkbook swb = new SXSSFWorkbook(template,10000);
		SXSSFSheet sheet1 = swb.getSheet("Sheet1");
		SXSSFRow row4 = sheet1.createRow(4);
		buildRowData(row4);
		SXSSFRow row10 = sheet1.createRow(9);
		buildRowData(row10);
		
		FileOutputStream fos = new FileOutputStream("D:\\excel\\tg\\templateBuilder.xlsx");
		swb.write(fos);
		
		fos.close();
	}
	
	private static void buildRowData(Row row) {
		row.createCell(0).setCellValue("1");
		row.createCell(1).setCellValue("2");
		row.createCell(2).setCellValue("3");
		row.createCell(3).setCellValue("4");
	}
}
