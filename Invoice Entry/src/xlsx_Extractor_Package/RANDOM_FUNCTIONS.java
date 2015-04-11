package xlsx_Extractor_Package;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class RANDOM_FUNCTIONS {

	static XSSFSheet worksheet;
	static int counter_column = 0;
	
	public static int get_number_of_rows(XSSFSheet ws, int column_for_counting) {
		
		worksheet = ws;
		
		
		int notNullCount = 0;

		for (Row row : ws) {
		    for (Cell cell : row) {
		        if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
		            if (cell.getCellType() != Cell.CELL_TYPE_STRING || cell.getStringCellValue().length() > 0) {
		                notNullCount++;
		                break;
		            }
		        }
		    }
		}
		return notNullCount;
	}
	
	public static boolean isValueInArray(String[] arr, String targetValue) {
		
		return Arrays.asList(arr).contains(targetValue);
		
	}
	
}
