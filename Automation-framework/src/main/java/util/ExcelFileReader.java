package util;

import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.DriverTestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ExcelFileReader extends DriverTestCase{
	String filePath = null;
	String sheetName = null;

	public ExcelFileReader(String filePath) {
		this.filePath = filePath;
	}

	public static List<String> readExcel(String filePath, String sheetName) throws IOException {
		List<String> columnNames = new ArrayList<>();

		FileInputStream file = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheet(sheetName);

		// Assuming the headers are in the first row (index 0)
		Row headerRow = sheet.getRow(0);
		if (headerRow != null) {
			// Iterate through each cell in the header row
			for (int i = headerRow.getFirstCellNum(); i < headerRow.getLastCellNum(); i++) {
				Cell cell = headerRow.getCell(i);
				if (cell != null) {
					columnNames.add(cell.getStringCellValue());
				} else {
					// Handle empty cells or any other cases
					columnNames.add(""); // Add an empty string or handle as needed
				}
			}
		}

		file.close();
		workbook.close();

		return columnNames;
	}


	public static void compareColumnNames(List<String> actual, List<String> expected) {
		getLogger().log(Status.INFO, "Comparing column names");
		for (int i = 0; i < Math.min(actual.size(), expected.size()); i++) {
			String actualName = actual.get(i);
			String expectedName = expected.get(i);
			Assert.assertEquals(actualName, expectedName,"The Expected Columns : " + expectedName + "is not matching with Actual column : " + expectedName);
		}
		getLogger().log(Status.PASS, "Successfully compared the column names");

		// Check for any extra columns in actual or expected (if needed)
		if (actual.size() != expected.size()) {
			getLogger().log(Status.PASS, "Number of columns mismatched - Actual: " + actual.size() + ", Expected: " + expected.size());
		}
	}


	public static List<String> readFilteredRecord(String filePath, String sheetName, String filterValue) throws IOException {
		List<String> record = new ArrayList<>();

		FileInputStream file = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheet(sheetName);

		// Assuming the unique identifier is in column A (index 0)
		for (Row row : sheet) {
			Cell cell = row.getCell(0); // Assuming column A
			if (cell != null && cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(filterValue)) {
				// Found the row matching the filter
				for (int i = 0; i < row.getLastCellNum(); i++) {
					Cell dataCell = row.getCell(i);
					if (dataCell != null) {
						switch (dataCell.getCellType()) {
						case STRING:
							record.add(dataCell.getStringCellValue());
							break;
						case NUMERIC:
							record.add(String.valueOf(dataCell.getNumericCellValue()));
							break;
						case BOOLEAN:
							record.add(String.valueOf(dataCell.getBooleanCellValue()));
							break;
						case BLANK:
							record.add("[BLANK]");
							break;
						default:
							record.add("[UNKNOWN]");
						}
					} else {
						record.add(""); // Handle empty cells or any other cases
					}
				}
				break; // Assuming only one record matches the filter (if multiple, handle as needed)
			}
		}

		file.close();
		workbook.close();

		return record;
	}


	// Updated method signature to accept a list of column names
	public static void compareColumnValues(List<String> actual, List<String> expected) {
		getLogger().log(Status.INFO, "Comparing column values");
		for (int i = 0; i < Math.min(actual.size(), expected.size()); i++) {
			String actualName = actual.get(i);
			String expectedName = expected.get(i);
			Assert.assertEquals(actualName, expectedName,"The Expected value : " + expectedName + " is not matching with Actual value : " + expectedName);
		}
		getLogger().log(Status.PASS, "Successfully compared the column values");

	}
	
	public static File getLatestFilefromDir(String dirPath, String fileNamePrefix) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles((dir1, name) -> name.startsWith(fileNamePrefix) && name.endsWith(".xlsx"));

		if (files == null || files.length == 0) {
			return null;
		}

		return getLatestFile(files);
	}

	private static File getLatestFile(File[] files) {
		return Arrays.stream(files)
				.max(Comparator.comparingLong(File::lastModified))
				.orElse(null);
	}

	
	

}
