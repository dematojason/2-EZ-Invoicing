package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Import_Sheet_To_LogAndInv_Sheets {
	
	/*logistics_wb_fileName (and invoice_tracking_fileName?) will be taken from
	file chooser... Not sure if these strings will be necessary*/
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
	String[][] invoice_tracking_data;
	
	public Import_Sheet_To_LogAndInv_Sheets() {
		try {
			this.import_wb = new XSSFWorkbook(new FileInputStream(new File(import_wb_fileName)));
			this.logistics_wb = new XSSFWorkbook(new FileInputStream(new File(logistics_wb_fileName)));
			this.invoice_tracking_wb = new XSSFWorkbook(new FileInputStream(new File(invoice_tracking_fileName)));
			
			this.import_ws = import_wb.getSheetAt(0);
			this.logistics_purchaseOrders_ws = logistics_wb.getSheetAt(0);
			this.logistics_bulk_ws = import_wb.getSheetAt(1);
			this.logistics_chep_ws = import_wb.getSheetAt(3);
			this.invoice_tracking_ws = invoice_tracking_wb.getSheetAt(0);
			
		}catch(NullPointerException err){
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
	}
	
	public void importStandard() {
		String invoice_num = null;
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
		}
	}
	
	public void importWarehouse() {
		String invoice_number = null;
		String company = null;
		String invoice_date = null;
		String reference = null;
		String date_received = null;
		String quantity = null;
		String charge_type = null;
		String charge_amount = null;
		String today = null;
		
		//check if there is any data in Warehouse section of invoice charges import sheet
		if(import_data[2][8] == "" && import_data[2][9] == "" && import_data[2][10] == ""
				 && import_data[2][11] == "" && import_data[2][12] == "" && import_data[2][13] == ""
				 && import_data[2][14] == "" && import_data[2][15] == ""&& import_data[2][16] == "")
		{
			//no Warehouse invoice data to be transferred to other worksheets
		}else
		{
			
		}
	}
	
	public void importChep() {
		String account = null;
		String invoice_number = null;
		String invoice_date = null;
		String reference = null;
		String description = null;
		String region = null;
		String percent = null;
		String sub_total = null;
		String tax = null;
		String net_total = null;
		String today = null;
		
		//check if there is any data in CHEP sections of invoice charge import sheet
		if(import_data[2][18] == "" && import_data[2][19] == "" && import_data[2][20] == "" && 
				import_data[2][21] == "" && import_data[2][22] == "" && import_data[2][23] == "" && 
				import_data[2][24] == "" && import_data[2][25] == "" && import_data[2][26] == "" && 
				import_data[2][27] == "")
		{
			//no Chep invoice data to be transferred to other worksheets
		}else
		{
			
		}
	}
	
}
