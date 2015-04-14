package xlsx_Extractor_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSX_Extractor {
	
	public static Object[][] cell_data;
	public static Object[] column_headers;
	
	public XLSX_Extractor(File file, int sheet_location)   {
		try {
		
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(sheet_location); //finds correct sheet of workbook based on passed integer sheet_location
			int last_row = getActualLastRowNum(ws) + 1; //finds number of rows containing value in worksheet
			int last_column = ws.getRow(0).getLastCellNum(); //finds number of columns in worksheet
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			evaluator.evaluateAll();
			column_headers = new Object[last_column];
			cell_data = new Object[last_row][last_column];
			get_column_headers(ws, last_column);
			get_cell_data(ws, last_row, last_column);
			wb.close();
			fis.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	
	}
	public static void get_column_headers( XSSFSheet ws, int last_column) {
		XSSFCell this_cell = null;
		XSSFRow row = null;
		for(int i = 0; i < last_column; i++) {
			row = ws.getRow(0);
			this_cell = row.getCell(i);
			if(this_cell != null) {
				column_headers[i] = cellToString(this_cell);
			}
		}
	}
	public static void get_cell_data(XSSFSheet ws, int last_column, int last_row) {
			XSSFCell this_cell = null;
			XSSFRow row = null;
			for(int i = 1; i < last_row+1; i++) {
				for(int j = 0; j < last_column+1; j++) {
					row = ws.getRow(i);
					this_cell = row.getCell(j);
					if(this_cell != null)
					cell_data[i][j] = cellToString(this_cell);
				}
			}
	}
	private static Object cellToString(XSSFCell xssfCell) {
		
		switch(xssfCell.getCellType()) {
		case 0: // cell contains numeric value
			String tmp = String.valueOf(xssfCell.getNumericCellValue());
			return tmp; 
		case 1: // cell contains string value
			
			return xssfCell.getRichStringCellValue().getString();
		case 2: // cell contains formula value
			return xssfCell.getCellFormula();
		case 3: //cell contains blank value
			return xssfCell.getStringCellValue();
		case 4: //cell contains boolean value
			String tmp1 = Boolean.toString(xssfCell.getBooleanCellValue());
			return tmp1;
		case 5: //cell contains #VAL or other error value
			return "error";
		default: //it should never come to this...
			throw new RuntimeException("This type of cell cannot be read.");
		}
		
	}
	
	public static int getActualLastRowNum(XSSFSheet ws) {
		
		for(int i = 0; i < ws.getLastRowNum(); i++) {
			for(int j = 0; j < ws.getRow(i).getLastCellNum(); j++) {
				XSSFCell cell = ws.getRow(i).getCell(j);
				if(cell != null) {
					break;
				}else if(j == ws.getRow(i).getLastCellNum() - 1) { //if last cell in column
					return i - 1; //return the row before current row (last row containing value)
				}
			}
		}
		
		return 0;
		
	}
	
	public String[][] convertObjToStringArray(Object[][] obj) {
		
		String[][] str = new String[obj.length][obj[0].length];
		for(int i = 0; i < obj.length; i++) {
			for(int j = 0; j < obj[0].length; j++) {
				str[i][j] = obj[i][j].toString();
			}
		}
		return str;
		
	}
	
	public static void test_print_2D_string_array(String[][] string_array, int index0_length, int index1_length) {
		
		for(int i = 0; i < index0_length; i++) {
			for(int x = 0; x < index1_length; x++) {
				try {
					if(x == 0) {
						System.out.print("| ");
					}
					System.out.print(string_array[i][x]);
					if(x == index1_length - 1) {
						System.out.print(" |");
					}else{
						System.out.print(" | ");
					}
				}catch(NullPointerException e) {
					
					System.out.println("NullPointerException @ row =  " + i + " and column =  " + x);
					
				}
			}
			System.out.println();
		}
		
	}
	public Object[] getColumnHeaders() {
		return column_headers;
	}
	public Object[][] getCellData() {
		return cell_data;
	}

}
