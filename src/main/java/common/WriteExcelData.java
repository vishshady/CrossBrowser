package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class WriteExcelData {

	public void writeData(String path, String sheetName,
			Map<Integer, Object[]> data) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			objArr = new Object[]{objArr[0], objArr[1],objArr[6],objArr[7]};
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateData(String path, String sheetName,
			Map<Integer, Object[]> data) {
		try {
			FileInputStream file = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(0);
			int cellCount = row.getPhysicalNumberOfCells() + 1;
			Set<Integer> keyset = data.keySet();
			int rownum = 0;
			for (Integer key : keyset) {
			//	rownum = key;
				row = sheet.getRow(rownum);
				Object[] objArr = data.get(key);
				objArr = new Object[]{objArr[0], objArr[1],objArr[6],objArr[7]};
				int cellnum = cellCount;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof Date)
						cell.setCellValue((Date) obj);
					else if (obj instanceof Boolean)
						cell.setCellValue((Boolean) obj);
					else if (obj instanceof String)
						cell.setCellValue((String) obj);
					else if (obj instanceof Double)
						cell.setCellValue((Double) obj);
				}
			}
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(path));
			workbook.write(outFile);
			outFile.close();
			System.out.println("Excel updated successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) throws IOException {
//		WriteExcelData wr = new WriteExcelData();
//		Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
//		data.put(10,new Object[] {"ok","huh?"});
//		wr.updateData("C:\\Data.xls", "MySheet",data);
//	}

}
