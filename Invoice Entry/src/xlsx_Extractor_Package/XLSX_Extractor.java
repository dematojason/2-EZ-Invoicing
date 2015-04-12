package xlsx_Extractor_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSX_Extractor {
	
	static Scanner my_scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		Scanner scan = my_scanner;
		
		try {
			//C:\Users\Jdemato\Desktop\IBC\Purchase Order Workbook Copies\Format Type IBC
			String excel_file_name = "C:\\Users\\Jdemato\\Documents\\Invoice Charge Import Sheet.xlsx";
			File file_name = new File(excel_file_name);
			FileInputStream fis = new FileInputStream(file_name);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			int last_row = ws.getLastRowNum() + 1; //finds number of rows in worksheet
			int last_column = ws.getRow(0).getLastCellNum(); //finds number of columns in worksheet
			String[][] file_data = new String[last_row][last_column];
			
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			
			evaluator.evaluateAll(); //takes all formula cells and evaluates them
			
			file_data = iterate_all_cells(wb, ws, last_row, last_column);
			
			//test_print_2D_string_array(file_data, file_data.length, file_data[0].length);
			
			INVOICE_SEARCH_FUNCTIONS.set_data_array(file_data);
			
			/*
			 * String for set_search_array_index(currently "Invoice Number") 
			 * is to be taken from user input on GUI 
			 * (on GUI, after user selects the "Search" function, 
			 * will output to user list of possible search parameters to choose from.
			 */
			
			/*
			 * "P.O. Files", "Tab", "P.O. #", "Container #", "Date Delivered/Port ETA", "Region", 
			 * "Company of Invoice", "Invoice Number", "Invoice Date", "Charge Type", "Date Input"
			*/
			System.out.println("file_data array:  ");
			test_print_2D_string_array(file_data, last_row, last_column);
			
			/*System.out.println("Enter Search Parameter: ");
			String search_param = scan.nextLine().trim(); //get from user... will be taken from GUI
						
			System.out.println("Enter the value you would like to search: ");
			String search_param_value = scan.nextLine().trim(); //get from user... will be taken from GUI
			
			INVOICE_SEARCH_FUNCTIONS.set_search_array_index(search_param); //will set which column will be searched based on user specified search category
			
			INVOICE_SEARCH_FUNCTIONS.set_string_to_be_searched(search_param_value); //will be taken from user input on GUI (text box)
			
			ArrayList<ArrayList<String>> invoice_search_matching_rows = new ArrayList<ArrayList<String>>();
			invoice_search_matching_rows = INVOICE_SEARCH_FUNCTIONS.get_matching_rows();
			
			if(invoice_search_matching_rows.size() == 0) {
				System.out.println("The " + search_param + " '" + search_param_value + "' was not found."); //output ex. "The Invoice Number '127312' was not found."
				System.exit(0);
			}
			
			System.out.println("invoice search matching rows arraylist size =  " + invoice_search_matching_rows.size());
			for(int i = 0; i < invoice_search_matching_rows.size(); i++) {
				System.out.println("invoice search matching rows get (" + i + ")  = " + invoice_search_matching_rows.get(i));
			}*/
			
		}catch(IOException e) {
			System.out.println("Que?");
			e.printStackTrace();
		}
		
	}
	
	private static String[][] iterate_all_cells(XSSFWorkbook wb, XSSFSheet ws, int last_row, int last_column) {
		
		String cell_value_string = null;
		String[][] string_array_buffer = new String[last_row][last_column];
		XSSFCell this_cell = null;
		XSSFRow row = null;
		
		for(int current_row = 0; current_row < last_row; current_row++) {
			for(int current_column = 0; current_column < last_column; current_column++) {
				
				try {
					row = ws.getRow(current_row);
					this_cell = row.getCell(current_column);
					CELL_TO_STRING.setCellToString(this_cell.getCellType(), this_cell, wb);
					cell_value_string = CELL_TO_STRING.getCellToString();
					string_array_buffer[current_row][current_column] = cell_value_string;
					
				}catch(NullPointerException e) {
					e.printStackTrace();
					System.out.println("current row =  " + current_row + " and current column =  " + current_column);
				}
				
			}
		}
		return string_array_buffer;
	}
	
	private static void test_print_2D_string_array(String[][] string_array, int index0_length, int index1_length) {
		
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

}
