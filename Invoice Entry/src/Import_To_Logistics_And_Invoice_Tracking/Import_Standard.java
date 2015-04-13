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

public class Import_Standard {
	
	/*logistics_wb_fileName (and invoice_tracking_fileName?) will be taken from
	file chooser... Not sure if these strings will be necessary
	
	String import_wb_fileName = "Invoice Charge Import Sheet.xlsx";
	String logistics_wb_fileName = "Spring 2015 Purchase Orders.xlsx";
	String invoice_tracking_fileName = "Invoice Charges Entry Testing 123.xlsm";
	
	XSSFWorkbook import_wb;
	XSSFWorkbook logistics_wb;
	XSSFWorkbook invoice_tracking_wb;
	
	XSSFSheet import_ws;
	XSSFSheet logistics_purchaseOrders_ws; //first tab in logistics purchase order workbook
	XSSFSheet logistics_bulk_ws; //
	XSSFSheet logistics_chep_ws;
	XSSFSheet invoice_tracking_ws;
	
	String[][] import_data;
	String[][] logistics_purchaseOrders_data;
	String[][] logistics_bulk_data;
	String[][] logistics_chep_data;
	String[][] invoice_tracking_data;*/
	
	File file;
	FileInputStream fis;
	XSSFWorkbook wb;
	File import_file = new File("");
	Object[][] output_file_data;
	Object[][] import_file_data;
	
	public Import_Standard(File passed_import_file, File passed_output_file) {
		
		XLSX_Extractor extract_import = new XLSX_Extractor(passed_import_file);
		this.import_file_data = extract_import.getCellData();
		
		XLSX_Extractor extract_output = new XLSX_Extractor(passed_output_file);
		this.output_file_data = extract_output.getCellData();
		
		try {
			
			this.fis = new FileInputStream(file);
			this.wb = new XSSFWorkbook(fis);
			
		}catch(FileNotFoundException err) {
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
		this.po_ws = wb.getSheetAt(0);
		this.bulk_ws = wb.getSheetAt(1);
		
		/*XSSFRow row = ws.getRow(current_row);*/
		importAll();
		
	}
	
	private void importAll() {
		
		//loop through all rows in import sheet containing value and put each row in string array
		for(int cur_row = 0; cur_row < po_ws.getLastRowNum(); cur_row++) {
			XSSFRow row = po_ws.getRow(cur_row);
			String[] data = getRow(wb, row); //put row cells from import sheet into string array
			
			ArrayList<Integer> matchingRows = getMatchingRowsPO(data[3]);
			if(matchingRows != null) {
				
			}
			/*
			 * invoice number = data[0]
			 * invoice company = data[1]
			 * invoice date = data[2]
			 * reference number = data[3]
			 * charge type = data[4]
			 * charge amount = data[5]
			 * today's date = data[6]
			 */
			
			/*
			 * Purchase Orders Tab:
			 * 
			 * Purchase Order Numbers = row(11)
			 * Charges:
			 *    Ocean Freight = row(14)
			 *    Documentation = row(15)
			 *    Customs Broker = row(16)
			 *    Duty = 17
			 *    GST = 18
			 *    Warehouse = 19
			 *    Drayage = 20
			 *    Delivery = 21
			 *    Misc = 22
			 *    Ex Works = 23
			 * Cases Ordered = 29
			 * Cases Received = 30
			 * Container Number = 34
			 * 
			 * Bulk Tab:
			 * 
			 * Purchase Order Numbers = row(4)
			 * Container Number = row(9)
			 */
		}
		
		
	}
	
	private void importPurchaseOrder(String[] row_data) {
		
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
	
	private ArrayList<Integer> getMatchingRowsBulk(String search_for) {
		//Will return row numbers in the bulk tab that match the string value being searched
		//Will return null if there were no rows with matching values
		
		ArrayList<Integer> return_buffer = new ArrayList<Integer>();
		ArrayList<String> data = getColumnListBulk(9); //get ArrayList of container numbers in bulk tab of spreadsheet
		
		try {
			
			for(int i = 0; i < data.size(); i++) {
				if(search_for == data.get(i)) {
					return_buffer.add(i);
				}
			}
			
			if(return_buffer.size() == 0) {
				data = getColumnListBulk(4); //get ArrayList of the purchase order numbers in bulk tab of spreadsheet
				for(int j = 0; j < data.size(); j++) {
					if(search_for == data.get(j)) {
						return_buffer.add(j);
					}
				}
			}
			
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		
		//check if any matching rows were found.. if not, return null
		if(return_buffer.size() > 0) {
			return return_buffer;
		}else{
			return null;
		}
		
	}
	
	private ArrayList<String> getColumnListBulk(int column_index) {
		
		ArrayList<String> return_buffer = new ArrayList<String>();
		
		try {
			
			for(int i = 1; i < bulk_ws.getLastRowNum(); i++) {
				XSSFRow row = bulk_ws.getRow(i);
				XSSFCell cell = row.getCell(column_index);
				CELL_TO_STRING.setCellToString(cell.getCellType(),  cell, wb);
				return_buffer.add(CELL_TO_STRING.getCellToString());
			}
			
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		
		return return_buffer;
		
	}
	
	private ArrayList<Integer> getMatchingRowsPO(String search_for) {
		//Will return row numbers in the purchase orders tab that match the string value being searched
		//Will return null if there were no rows with matching values
		
		ArrayList<Integer> return_buffer = new ArrayList<Integer>();
		ArrayList<String> data = getColumnListPO(11); //get ArrayList of the Purchase Orders column in Spreadsheet
		
		try {
			
			for(int i = 0; i < data.size(); i++) {
				if(search_for == data.get(i)) {
					return_buffer.add(i);
				}
			}
			
			if(return_buffer.size() == 0) {
				data = getColumnListPO(34); //get ArrayList of the container numbers column in Purchase Orders Spreadsheet
				for(int j = 0; j < data.size(); j++) {
					if(search_for == data.get(j)) {
						return_buffer.add(j);
					}
				}
			}
			
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		
		//check if any matching rows were found.. if not, return null
		if(return_buffer.size() > 0) {
			return return_buffer;
		}else{
			return null;
		}
		
	}
	
	private ArrayList<String> getColumnListPO(int column_index) {
		//Will return all strings contained in the column specified by passed integer
		
		ArrayList<String> return_buffer = new ArrayList<String>();
		
		try {
			
		    for(int cur_row = 1; cur_row < po_ws.getLastRowNum(); cur_row++) {
		    	XSSFRow row = po_ws.getRow(cur_row);
		    	XSSFCell cell = row.getCell(column_index);
		    	CELL_TO_STRING.setCellToString(cell.getCellType(), cell, wb);
		    	return_buffer.add(CELL_TO_STRING.getCellToString());
		    }
		    
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		
		return return_buffer;
	}

	private static String[] getRow(XSSFWorkbook wb, XSSFRow row) {
		//will return all strings in the row specified by the passed row and workbook
		
		int num_of_columns = 7;
		String cell_value_string = null;
		String[] string_array_buffer = new String[num_of_columns];
		XSSFCell this_cell = null;
		
		for(int cur_col = 0; cur_col < num_of_columns; cur_col++) {
			
			try {
				this_cell = row.getCell(cur_col);
				CELL_TO_STRING.setCellToString(this_cell.getCellType(), this_cell, wb);
				cell_value_string = CELL_TO_STRING.getCellToString();
				string_array_buffer[cur_col] = cell_value_string;
				
			}catch(NullPointerException e) {
				e.printStackTrace();
				System.out.println("current row =  " + row.getRowNum() + " and current column =  " + cur_col);
			}
			
		}
		return string_array_buffer;
	}
	
}



/*String invoice_num = null;
String invoice_company = null;
String invoice_date = null;
String reference = null;
String charge = null;
String charge_amount = null;
String today = null;

//check if there is any data in Standard invoices section of Invoice Charges Import Sheet
if(import_data[2][0] == "" && import_data[2][1] == "" && import_data[2][2] == "" &&
		import_data[2][3] == "" && import_data[2][4] == "" &&
		import_data[2][5] == "" && import_data[2][6] == "") {
	
	//no Standard invoice data to be transferred to other worksheets
}else{
	//check for matching cells in data array of the Invoice Import Sheet
	for(int i = 0; i < import_data.length; i++) {
		for(int x = 0; x < import_data[0].length; x++) {
			
		}
	}
}*/