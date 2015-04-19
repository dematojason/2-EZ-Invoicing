package xlsx_Extractor_Package;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CELL_TO_STRING {

	private static XSSFCell cell_value;
	private static Workbook excel_workbook;
	private static int cell_type;
	
	public CELL_TO_STRING(int cellType, XSSFCell cell) {
		this.cell_value = cell;
		this.cell_type = cellType;
	}
	
	public static String getCellToString() throws RuntimeException {
		
		switch(cell_type) {
			case 0: // cell contains numeric value
				return getStringNumericCell();
			case 1: // cell contains string value
				return getStringRichStringCell();
			case 2: // cell contains formula value
				return getStringFormulaCell();
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
		
		return "ERROR CELL";
		
	}
	
	private static String getStringBooleanCell() {
		
		return "BOOLEAN CELL";
		
	}
	
	private static String getStringBlankCell() {
		
		return "";
		
	}
	
	private static String getStringFormulaCell() {
		
		FormulaEvaluator evaluator = excel_workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cell_value_evaluator = evaluator.evaluate(cell_value);
		
		switch(cell_value_evaluator.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell_value.getBooleanCellValue()); // get boolean cell value from evaluator then convert to String
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(cell_value.getNumericCellValue()); // get numeric cell value from evaluator then convert to string
			case Cell.CELL_TYPE_STRING:
				return cell_value.getRichStringCellValue().toString(); // get string value from evaluator then return
			case Cell.CELL_TYPE_BLANK:
				return "*BLANK*"; // blank cell value
			case Cell.CELL_TYPE_ERROR:
				return "ERROR CELL"; // error cell value (e.g. #VAL)
			case Cell.CELL_TYPE_FORMULA: // this should never happen!
			default:
				throw new RuntimeException("This type of cell should not exist... what have you done?"); //and this should definitely never happen
		}
		
	}
		
	private static String getStringRichStringCell() {
		
		return cell_value.getRichStringCellValue().toString();
		
	}

	private static String getStringNumericCell() {
		
		if (DateUtil.isCellDateFormatted(cell_value) == true) { //if cell value is a date
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
				return dateFormatter.format(cell_value.getDateCellValue()).toString();	// get date value of current cell and format to "MM/dd/yyyy" using SimpleDateFormat method
			} catch(RuntimeException e) {
				System.out.print("An error was encountered in function 'cellToString' while trying to convert a date value to string value");
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			return Double.toString(cell_value.getNumericCellValue());
		}
		return null;
	}
	
}
