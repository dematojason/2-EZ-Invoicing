package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Dat_Standard_Import_Row {

	ArrayList<String> dis_row;
	
	public Dat_Standard_Import_Row(ArrayList<String> pass_dat_row) {
		this.dis_row = pass_dat_row;
	}
	
	public ArrayList<String> getCodingInfo() {
		ArrayList<String> codingInfo = new ArrayList<String>();
		
		codingInfo.add("Invoice Number:  " + getInvoiceNumber());
		codingInfo.add("Invoice Company:  " + getInvoiceCompany());
		codingInfo.add("Invoice Date:  " + getInvoiceDate());
		codingInfo.add("Product Reference Number" + getReferenceNumber());
		codingInfo.add("Vendor:  " + getVendorName());
		codingInfo.add("Region:  " + getRegion());
		codingInfo.add("Charge Type:  " + getChargeType());
		codingInfo.add("Charge Amount: " + getChargeTotalAmount());
		codingInfo.add("Workbook:  " + getRowWorkbook());
		codingInfo.add("Sheet:  " + getRowSheet());
		
		return codingInfo;
	}
	
	public String getInvoiceNumber() {
		return dis_row.get(0);
	}
	public String getInvoiceCompany() {
		return dis_row.get(1);
	}
	public String getInvoiceDate() {
		return dis_row.get(2);
	}
	public String getChargeType() {
		return dis_row.get(3);
	}
	public String getChargeExistingAmount() {
		return dis_row.get(4);
	}
	public String getChargeTotalAmount() {
		return dis_row.get(5);
	}
	public String getEntryDate() {
		return dis_row.get(6);
	}
	public String getNotes() {
		return dis_row.get(7);
	}
	public String getReferenceNumber() {
		return dis_row.get(9);
	}
	public String getVendorName() {
		return dis_row.get(10);
	}
	public String getRegion() {
		return dis_row.get(11);
	}
	public String getDeliveryDate() {
		return dis_row.get(12);
	}
	public String getPortETA() {
		return dis_row.get(12);
	}
	public String getRowWorkbook() {
		return dis_row.get(13);
	}
	public String getRowSheet() {
		return dis_row.get(14);
	}
	
}








