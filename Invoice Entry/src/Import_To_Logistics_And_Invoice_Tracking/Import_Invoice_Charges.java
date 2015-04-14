package Import_To_Logistics_And_Invoice_Tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





import xlsx_Extractor_Package.CELL_TO_STRING;
import xlsx_Extractor_Package.XLSX_Extractor;

public class Import_Invoice_Charges {
	
	//will define dest_file[] for testing. Not sure best way to handle.
	File dest_file[] = {new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Spring 2015 Purchase Orders.xlsx"),
			new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Holiday 2015 Purchase Orders.xlsx")};
	
	//define import_file[] with 3 different invoice charge import sheets
	File import_file[] = {new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Standard Invoice Charge Import Sheet.xlsx"),
			new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Chep Invoice Charge Import Sheet.xlsx")};
		
	public Import_Invoice_Charges(/*File[] file*/) {
		
		/*this.dest_file = file;*/ //Purchase Order Files (can maybe have program decide which files based on current month/year? Not sure yet)
		insertAllCharges();
		
	}
	
	private void insertAllCharges() {
	/**********************************************************************************************************************
	* Inserts all charges from import sheets into Purchase Order sheets and invoice tracking sheet
	*/
		
		for(int i = 0; i < import_file.length; i++) {
			if(importSheetIsEmpty(i) == false) {
				importSheet(i);
			}
		}
		
	}
	
	private void importSheet(int import_type) {
	/**********************************************************************************************************************
	* Imports data from certain import sheet based on passed integer import_type
	*/
		
		String[][] import_data = getImportData(import_type);
		Workbook_Data_Searcher ws_data = new Workbook_Data_Searcher(import_data);
		
		if(import_data == null) {
			System.out.println("Error retrieving data from import Sheet (import type index = " + import_type + ")");
			System.exit(0);
		}
		
		//loop through all rows in import sheet
		for(int i = 0; i < import_data.length; i++) {
			importRow(ws_data.getRowData(i), import_type); //get ArrayList<String> of current row, then pass to importRow for insertion
		}
			
	}
	
	private void importRow(ArrayList<String> passed_row, int import_type) {
	/**********************************************************************************************************************
	* Inserts certain row based on passed integer import_type and ArrayList passed_row
	*/
		
		switch()
		
	}
	
	private String[][] getMatchingWorkSheetData(int invoice_type, String ref) {
	/**********************************************************************************************************************
	* Returns String[][] of worksheet containing reference number
	*/
		
		for(int i = 0; i < dest_file.length; i++) { //loop through 2 destination files
			for(int j = 0; j < 2; j++) { //loop through first 2 sheets of each destination file
				String[][] data = getOutputWsData(j); //get import file data
				Workbook_Data_Searcher wb_search = new Workbook_Data_Searcher(data);
				if(wb_search.sheetIsMatch(j, ref) == true) {
					return data;
				}
			}
		}
		return null; //no sheet found
		
	}
	
	private boolean importSheetIsEmpty(int invoice_type) {
	/**********************************************************************************************************************
	* Returns true if certain import sheet (based on int invoice_type) is empty
	*/
		
		if(getImportData(invoice_type).length <= 0) {
			return true;
		}else{
			return false;
		}
		
	}
	
	private String[][] getOutputWsData(int sheet_index) {
	/**********************************************************************************************************************
	* Calls methods getCellData() and convertObjToStringArray() to get String[][] of data from destination worksheet
	*/
		
		if(sheet_index != 0 && sheet_index != 1) {
			System.out.println("Invalid sheet index passed into Import_Invoice_Charge.getOutputWsData().");
			return null;
		}else{
			XLSX_Extractor extract = new XLSX_Extractor(dest_file[sheet_index], sheet_index);
			return extract.convertObjToStringArray(extract.getCellData());
		}
		
	}
	
	private String[][] getImportData(int invoice_type) {
	/**********************************************************************************************************************
	* Calls methods getCellData(0 and converObjToStringArray() to get String[][] of data from import worksheet
	*/
		
		if(invoice_type != 0 && invoice_type != 1) {
			System.out.println("Invalid integer invoice type passed into Import_Invoice_Charge.getImportData().");
			return null;
		}
		XLSX_Extractor extract = new XLSX_Extractor(import_file[invoice_type], 0);
		return extract.convertObjToStringArray(extract.getCellData());
		
	}
	
	private String getActualChargeValue(String charge_amount, int number_of_matching_rows) {
	/**********************************************************************************************************************
	* Returns actual charge amount for each row
	*/
		
		if(number_of_matching_rows > 1) {
			return String.valueOf(Double.parseDouble(charge_amount) / number_of_matching_rows);
		}else{
			return charge_amount;
		}
		
	}
	
	private int getChargeColumnNum(String charge_type) {
	/**********************************************************************************************************************
	* Returns the column number where the charge will be entered into the purchase orders spreadsheet
	*/
		
		switch(charge_type) {
			case "Ocean Freight":
				return 14;
			case "Documentation":
				return 15;
			case "Clearance":
				return 16;
			case "Duty":
				return 17;
			case "GST":
				return 18;
			case "Warehouse":
				return 19;
			case "Drayage":
				return 20;
			case "Delivery":
				return 21;
			case "Inland Europe":
				return 22;
			case "Miscellaneous":
				return 23;
			default:
				System.out.println("No bueno... could not read charge type");
				System.exit(0);
				return 0;
		}
		
	}
	
}


