package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Import_Sheet_To_LogAndInv_Sheets {

	XSSFWorkbook import_wb;
	XSSFSheet import_ws;
	String import_wb_fileName = "Invoice Charge Import Sheet.xlsx";
	/*logistics_wb_fileName (and invoice_tracking_fileName?) will be taken from
	file chooser... Not sure if these strings will be necessary*/
	XSSFWorkbook logistics_wb;
	XSSFSheet logistics_purchaseOrders_ws; //first tab in logistics purchase order workbook
	XSSFSheet logistics_bulk_ws; //
	XSSFSheet logistics_chep_ws;
	String logistics_wb_fileName = "Spring 2015 Purchase Orders.xlsx";
	XSSFWorkbook invoice_tracking_wb;
	String invoice_tracking_fileName = "Invoice Charges Entry Testing 123.xlsm";
	
	XSSFSheet ws;
	String[][] sheet_data;
	
	
	public Import_Sheet_To_LogAndInv_Sheets() {
		try {
			this.import_wb = new XSSFWorkbook(new FileInputStream(new File(import_wb_fileName)));
			this.logistics_wb = new XSSFWorkbook(new FileInputStream(new File(logistics_wb_fileName)));
			this.invoice_tracking_wb = new XSSFWorkbook(new FileInputStream(new File(invoice_tracking_fileName)));
			
		}catch(NullPointerException err){
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
	}
	
}
