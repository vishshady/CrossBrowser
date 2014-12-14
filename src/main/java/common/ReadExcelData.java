package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelData {
	FileInputStream fileInputStream = null;
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	Row row;
	Cell cell;

	ReadExcelData(String path) {
		try {
			fileInputStream = new FileInputStream(path);
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			System.out.println("File not found!!" + e);
		}
	}

	public HashMap<Integer, ArrayList<String>> getAllValues(String sheetName) {
		HashMap<Integer, ArrayList<String>> readValue = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {

			sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();
			int count = 0;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						data.add(String.valueOf(((long) cell
								.getNumericCellValue())));
						break;
					case Cell.CELL_TYPE_STRING:
						data.add(cell.getStringCellValue());
						break;
					}
				}
				readValue.put(count++, new ArrayList<String>(data));
				data.clear();
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		}
		return readValue;
	}

	public Integer findRow(HSSFSheet sheet, int cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					Double d = cell.getNumericCellValue();
					if (d.intValue() == cellContent) {
						return row.getRowNum();
					}
				}
			}
		}
		return null;
	}

	public HashMap<Integer, ArrayList<String>> getValues() {
		HashMap<Integer, ArrayList<String>> readValue = new HashMap<Integer, ArrayList<String>>();
		String value = "";
		ArrayList<String> data = new ArrayList<String>();
		try {
			sheet = workbook.getSheet("Data");
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					cell = row.getCell(j);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						long l = (long) cell.getNumericCellValue();
						value = String.valueOf((l));
						data.add(value);
						break;
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						data.add(value);
						break;
					}
				}
				readValue.put(i, new ArrayList<String>(data));
				data.clear();
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		}
		return readValue;
	}

	public String getCellValue(String sheetName, int index, String heading) {
		String cellValue = "";
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			int cellNumber = 0;
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim()
							.equals(heading)) {
						cellNumber = cell.getColumnIndex();
					}
				}
			}
			row = sheet.getRow(findRow(sheet, index));
			cell = row.getCell(cellNumber);
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(((long) cell.getNumericCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			}
		} catch (NullPointerException e) {
			System.out.println("Data not Found!! " + e);
		}
		return cellValue;
	}

	public static void main(String[] args) throws IOException {
		ReadExcelData read = new ReadExcelData("D:\\sample1.xls");
		HashMap<Integer, ArrayList<String>> keyset = new HashMap<Integer, ArrayList<String>>();
		keyset = read.getAllValues("Data");
		Set<Entry<Integer, ArrayList<String>>> set = keyset.entrySet();

		for (Entry<Integer, ArrayList<String>> me : set)
			System.out.println(me.getKey() + " " + me.getValue());

		System.out.println(read.getCellValue("Data", 0, "URL"));
	}

}
