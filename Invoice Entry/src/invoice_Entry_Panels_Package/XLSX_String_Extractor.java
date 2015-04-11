package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSX_String_Extractor {

	static XSSFWorkbook wb;
	XSSFSheet ws;
	public int last_row;
	public int last_column;
	
	public XLSX_String_Extractor(File file) {
		try {
			XLSX_String_Extractor.wb = new XSSFWorkbook(new FileInputStream(file));
			this.ws = wb.getSheetAt(0);
			this.last_row = ws.getLastRowNum(); //get last row in sheet
			this.last_column = ws.getRow(0).getLastCellNum(); //get last column in sheet			
			FormulaEvaluator evaluate = wb.getCreationHelper().createFormulaEvaluator();
			evaluate.evaluateAll();
		}catch(FileNotFoundException err) {
			err.printStackTrace();
			System.out.println("The file chosen could not be found.");
			System.exit(0);
		}catch(IOException err) {
			err.printStackTrace();
		}
	}
	
	public Object[] getColumnHeaders() {
		String cell_value = null;
		Object[] column_headers = new String[last_column];
		XSSFCell cell = null;
		XSSFRow row = null;
		for(int cur_row = 0; cur_row < 1; cur_row++) {
			for(int cur_col = 0; cur_col < last_column + 1; cur_col++) {
				try {
					row = ws.getRow(cur_row);
					cell = row.getCell(cur_col);
					cell_value = getCellToString(cell.getCellType(), cell, wb);
					column_headers[cur_col] = (Object)cell_value;
				}catch(NullPointerException err) {
					err.printStackTrace();
					System.out.println("Null Pointer Error at: Row: " + cur_row + ", Column: " + cur_col);
				}
			}
		}
		return column_headers;
	}
	
	public Object[][] getDataStringArray() {
		String cell_value = null;
		Object[][] return_data = new String[last_row][last_column];
		XSSFCell cell = null;
		XSSFRow row = null;
		for(int cur_row = 1; cur_row < last_row + 1; cur_row++) {
			for(int cur_col = 0; cur_col < last_column + 1; cur_col++) {
				try {
					row = ws.getRow(cur_row);
					cell = row.getCell(cur_col);
					cell_value = getCellToString(cell.getCellType(), cell, wb);
					return_data[cur_row][cur_col] = (Object)cell_value;
				}catch(NullPointerException err) {
					err.printStackTrace();
					System.out.println("Null Pointer Error at: Row: " + cur_row + ", Column: " + cur_col);
				}
			}
		}
		return return_data;
	}
	
	public String getCellToString(int cell_type, XSSFCell cell, XSSFWorkbook wb) {
		switch(cell_type) {
		case 0: // cell contains numeric value
			return getStringNumericCell(cell);
		case 1: // cell contains string value
			return getStringRichStringCell(cell);
		case 2: // cell contains formula value
			return getStringFormulaCell(cell);
		case 3: //cell contains blank value
			return getStringBlankCell();
		case 4: //cell contains boolean value
			return getStringBooleanCell();
		case 5: //cell contains #VAL or other error value
			return getStringErrorCell();
		default: //it should never come to this...
			throw new RuntimeException("This type of cell cannot be read.");
		}
		
	}
	
private static String getStringErrorCell() {
		
		return "ERROR";
		
	}
	
	private static String getStringBooleanCell() {
		//not a chance anyone in this company knows what boolean 
		//variables are none-the-less how to put one in a cell
		//figured I'd put it in there just because
		return "BOOLEAN"; 
		
	}
	
	private static String getStringBlankCell() {
		
		return "";
		
	}
	
	private static String getStringFormulaCell(XSSFCell cell) {
		
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		CellValue cell_value_evaluator = evaluator.evaluate(cell);
		
		switch(cell_value_evaluator.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue()); // get boolean cell value from evaluator then convert to String
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(cell.getNumericCellValue()); // get numeric cell value from evaluator then convert to string
			case Cell.CELL_TYPE_STRING:
				return cell.getRichStringCellValue().toString(); // get string value from evaluator then return
			case Cell.CELL_TYPE_BLANK:
				return ""; // blank cell value
			case Cell.CELL_TYPE_ERROR:
				return "ERROR"; // error cell value (e.g. #VAL)
			case Cell.CELL_TYPE_FORMULA: // this should never happen!
			default:
				throw new RuntimeException("This type of cell should not exist... what have you done?"); //and this should definitely never happen
		}
		
	}
	
	private static String getStringRichStringCell(XSSFCell cell) {
		
		return cell.getRichStringCellValue().toString();
		
	}

	private static String getStringNumericCell(XSSFCell cell) {
		
		if (DateUtil.isCellDateFormatted(cell) == true) { //if cell value is a date
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
				return dateFormatter.format(cell.getDateCellValue()).toString();	// get date value of current cell and format to "MM/dd/yyyy" using SimpleDateFormat method
			} catch(RuntimeException e) {
				System.out.print("An error was encountered in function 'cellToString' while trying to convert a date value to string value");
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			return Double.toString(cell.getNumericCellValue());
		}
		return null;
	}
	
}
