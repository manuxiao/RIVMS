package org.gaixie.micrite.download;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExcelUtils {

	final public static void insertRow(HSSFSheet sheet, int rowIndex,
			int cols) {
		HSSFRow row = sheet.createRow(rowIndex);
		for (int i = 0; i < cols; i++) {
			row.createCell(i);
		}
	}
}
