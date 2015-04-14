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
	
	FileInputStream fis;
	
	XSSFWorkbook wb;
	
	File dest_file;
	File import_file;
	
	String[][] import_data;
	String[][] output_po_data;
	String[][] output_bulk_data;
	String[][] output_chep_data;
	
	XLSX_Extractor extract;
	XLSX_Extractor extract_monster;
	
	public Import_Invoice_Charges(File file) {
		
		this.dest_file = file;
		
		//get extracted data from sheet index = 0 (Purchase Orders Tab) of output file
		extract = new XLSX_Extractor(import_file, 0); 
		this.import_data = extract.convertObjToStringArray(extract.getCellData());
		
		try {
			
			this.fis = new FileInputStream(dest_file);
			this.wb = new XSSFWorkbook(fis);
			
		}catch(FileNotFoundException err) {
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
		importAll();
		
	}
	
	private void importAll() {
		importStandard();
		importDelivery();
		importChep();
	}
	
	private void importStandard() {
		
		//loop through all rows in import sheet
		for(int i = 0; i < chep_import_data.length; i++) {
			ArrayList<String> row_data = getRowData(chep_import_data, i);
			ArrayList<Integer> matchingRowIndexes = getMatchingRows(getMatchingFile(row_data.get(3)));
			importRow(row_data);
		}
			
			
			
			
		for(int cur_row = 0; cur_row < output_po_ws.getLastRowNum(); cur_row++) {
						
			/*String[] data = getRowData() //put row cells from import sheet into string array
			
			ArrayList<Integer> matchingRows = getMatchingRowsPO(data[3]);
			if(matchingRows != null) {
				
			}*/
			
		}
		
		
	}
	
	private void importDelivery() {
		
		
		
	}
	
	private void importChep() {
		
		
		
	}
	
	private int getMatchingWorkbook() {
		
		//get extracted data from sheet index = 0 (Purchase Orders Tab) of output file
		XLSX_Extractor extractor_output_po = new XLSX_Extractor(passed_output_file, 0); 
		this.output_po_data = extractor_output_po.convertObjToStringArray(extractor_output_po.getCellData());
		
	}
	
	private void importRow(ArrayList<String> passed_row) {
		
		ArrayList<Integer> matchingRows = getMatchingRowsPO(row_data[3]);
		
		if(matchingRows != null) {
			row_data[5] = getActualChargeValue(row_data[5], matchingRows); //set charge amount (row_data[5]) = charge_amount / number of matching rows
			int charge_col = getChargeColumnNum(row_data[4]); //set charge column = correct charge column index of purchase orders spreadsheet
			for(Integer i : matchingRows) { //loop through each element of ArrayList of matching row indexes
				
			}
		}else{
			matchingRows = getMatchingRowsBulk(row_data[3]);
			if(matchingRows != null) {
				
			}
		}
		
	}
	
	private int getChargeColumnNum(String charge_type) {
		//Returns the column number where the charge will be entered into the purchase orders spreadsheet
		
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
	
	private String getActualChargeValue(String charge_amount, ArrayList<Integer> matching_rows) {
		//Will return the charge amount divided by the number of matching rows
		
		if(matching_rows.size() > 1) {
			return String.valueOf(Double.parseDouble(charge_amount) / matching_rows.size());
		}else{
			return charge_amount;
		}
		
	}
	
}


