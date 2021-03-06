package Import_To_Logistics_And_Invoice_Tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Import_Invoice_Charges {
	
	//will define dest_file[] for testing. Not sure best way to handle.
	/*File dest_po_file[] = {new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Spring 2015 Purchase Orders.xlsx"),
			new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Holiday 2015 Purchase Orders.xlsx")};*/
	File dest_po_file[] = {new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Spring 2015 Purchase Orders.xlsx"),
			new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Holiday 2015 Purchase Orders.xlsx")};
	
	/*File invoice_tracking_file = new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Invoice Tracking.xlsx");*/
	File invoice_tracking_file = new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Invoice Tracking.xlsx");
	
	//define import_file[] with 3 different invoice charge import sheets
	/*File import_files[] = {new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Standard Invoice Charge Import Sheet.xlsx"),
			new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Chep Invoice Charge Import Sheet.xlsx")};*/
	File import_files[] = {new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Standard Invoice Charge Import Sheet.xlsx"), 
			new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Chep Invoice Charge Import Sheet.xlsx")};
		
	public Import_Invoice_Charges(/*File[] file*/) {
		
		/*this.dest_file = file;*/ //Purchase Order Files (can maybe have program decide which files based on current month/year? Not sure yet)
		insertAllChargesAllSheets();
		
	}
	
	private void insertAllChargesAllSheets() {
	/**********************************************************************************************************************
	* Inserts all charges from import sheets into Purchase Order sheets and invoice tracking sheet
	*/
		
		for(int i = 0; i < import_files.length; i++) { //for each import file
			Worksheet_Data import_sheet = new Worksheet_Data(import_files[i], 0);
			if(import_sheet.sheetIsEmpty() == false) {
				importSheet(import_sheet);
			}
		}
		
	}
	
	private void importSheet(Worksheet_Data import_sheet) {
	/**********************************************************************************************************************
	* Imports data from certain import sheet
	*/
		
		for(int i = 0; i < import_sheet.getNumberOfRows(); i++) { //for each row of import sheet
			Worksheet_Data invoice_tracking_wb = new Worksheet_Data(invoice_tracking_file, 0);
			String[][] invoice_tracking_data = invoice_tracking_wb.getAllData();
			if(invoice_tracking_data == null) {
				System.out.println("There was an error while attempting to retrieve data from the invoice tracking excel file.");
				System.exit(0);
			}
			for(int j = 0; j < dest_po_file.length; j++) { //for each destination file
				try {
					XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(dest_po_file[j]));
					for(int k = 0; k < 2; k++) { //for each sheet in destination file (will only be 2 for each file: purchase order tab (tab index 0) and bulk tab (tab index 1)
						XSSFSheet ws = wb.getSheetAt(k);
						Worksheet_Data dest_po_data = new Worksheet_Data(dest_po_file[j], k); //create new object of type Worksheet_Data for referencing destination worksheet
						int[] column_index = new int[2];
						if(k == 0) { //if current tab is "purchase orders" tab, "purchase order" column = 11 and "container number" column = 34
							column_index[0] = 11;
							column_index[1] = 34;
						}else{ //if current tab is "bulk" tab, "purchase order" column = 4 and "container number" column = 9
							column_index[0] = 4;
							column_index[1] = 9;
						}
						String reference_number = import_sheet.getImportRowRefNum(i); //retrieve string of reference number of current row
						String[][] matching_po_sheet_data = dest_po_data.getSheetDataIfMatchFound(import_sheet.getImportRowRefNum(i), column_index);
						
						if(matching_po_sheet_data != null) { //make sure matching_dest_data was given some value
							ArrayList<Integer> matching_row_ints = dest_po_data.getMatchingRowNumbers(reference_number, column_index); //get rows of destination file matching reference number
							ArrayList<String> import_row_data = import_sheet.getRowData(i); //get current row data
							
							@SuppressWarnings("unused")
							Dat_Standard_Import_Row import_row = new Dat_Standard_Import_Row(import_row_data);
							importCurRow(ws, import_row_data, matching_row_ints, invoice_tracking_data.length, dest_po_data, k); //pass current row's data and list of matching integers into method importCurRow for final importing
						}
						wb.close();
					}
				}catch(FileNotFoundException err) {
					System.out.println("There was an error while attempting to open the destination purchase orders file.");
					err.printStackTrace();
				}catch(IOException err) {
					System.out.println("There was an error while attempting to open the destination purchase orders file.");
					err.printStackTrace();
				}
			}
		}
			
	}
	
	private void importCurRow(XSSFSheet ws, ArrayList<String> import_row_data, ArrayList<Integer> matching_row_ints, int next_invoice_tracking_row, Worksheet_Data dest_sheet_data, int po_sheet_index) {
		
		for(int i = 0; i < matching_row_ints.size(); i++) { //for each matching row in destination sheet
			String charge_type = import_row_data.get(3);
			
			if(charge_type != "Delivery") {
				int charge_col_index = getChargeColumnNum(charge_type, po_sheet_index); //get column index for this charge
				
				if(po_sheet_index == 0) {
					Row row = ws.getRow(matching_row_ints.get(i));
					Cell cell = row.getCell(charge_col_index);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(addCurAndNewCharge(getActualChargeValue(import_row_data.get(4), matching_row_ints.size()), cell));
				}
				
			}
		}
		//Worksheet_Data invoice_tracking_sheet = new Worksheet_Data(dest_invoice_tracking_file, 0);
		//int row_index = invoice_tracking_sheet.getNumberOfRows();
		
	}
	
	private String addCurAndNewCharge(String new_chg_amt, Cell cell) {
		
		double cur_amt = cell.getNumericCellValue();
		return Double.toString(Double.parseDouble(new_chg_amt) + cur_amt);
		
	}
		
	private String getActualChargeValue(String charge_amount, int number_of_matching_rows) {
	/**********************************************************************************************************************
	* Returns actual charge amount for output to each row
	*/
		
		if(number_of_matching_rows > 1) {
			return String.valueOf(Double.parseDouble(charge_amount) / number_of_matching_rows);
		}else{
			return charge_amount;
		}
		
	}
	
	private int getChargeColumnNum(String charge_type, int tab_index) {
	/**********************************************************************************************************************
	* Returns the column number where the charge will be entered into the purchase orders spreadsheet
	*/
		
		if(tab_index != 0 && tab_index != 1) {
			System.out.println("Invalid tab index entered into method getChargeColumnNum.");
			System.exit(0);
			return 0;
		}else{
			System.out.println(charge_type);
			switch(charge_type) {
				case "Ocean Freight":
					if(tab_index == 0) { //purchase order tab
						return 14;
					}else{ //bulk tab
						return 10;
					}
				case "Documentation":
					if(tab_index == 0) { //purchase order tab
						return 15;
					}else{ //bulk tab
						return 11;
					}
				case "Clearance":
					if(tab_index == 0) { //purchase order tab
						return 16;
					}else{ //bulk tab
						return 12;
					}
				case "Duty":
					if(tab_index == 0) { //purchase order tab
						return 17;
					}else{ //bulk tab
						return 13;
					}
				case "GST":
					if(tab_index == 0) { //purchase order tab
						return 18;
					}else{ //bulk tab
						return 14;
					}
				case "Warehouse":
					if(tab_index == 0) { //purchase order tab
						return 19;
					}else{ //bulk tab
						return 15;
					}
				case "Drayage":
					if(tab_index == 0) { //purchase order tab
						return 20;
					}else{ //bulk tab
						return 16;
					}
				case "Delivery":
					if(tab_index == 0) { //purchase order tab
						return 21;
					}else{ //bulk tab
						return 17;
					}
				case "Miscellaneous":
					if(tab_index == 0) { //purchase order tab
						return 22;
					}else{ //bulk tab
						return 18;
					}
				case "Inland Europe":
					if(tab_index == 0) { //purchase order tab
						return 23;
					}else{ //bulk tab
						return 19;
					}
				default:
					System.out.println("No bueno... could not read charge type");
					System.exit(0);
					return 0;
			}
		}
		
	}
	
}


