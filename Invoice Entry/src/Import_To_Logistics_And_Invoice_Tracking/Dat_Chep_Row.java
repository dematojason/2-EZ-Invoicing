package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Dat_Chep_Row {

	ArrayList<String> dis_row;
	
	public Dat_Chep_Row(ArrayList<String> pass_dat_row) {
		this.dis_row = pass_dat_row;
	}
	public String getCompanyAccountName() {
		return dis_row.get(0);
	}
	public String getInvoiceNumber() {
		return dis_row.get(1);
	}
	public String getInvoiceDate() {
		return dis_row.get(2);
	}
	public String getProductName() {
		return dis_row.get(3);
	}
	public String getVendorName() {
		return dis_row.get(4);
	}
	public String getRegion() {
		return dis_row.get(5);
	}
	public String getPercent() {
		return dis_row.get(6);
	}
	public String getChargeNetTotal() {
		return dis_row.get(7);
	}
	public String getEntryDate() {
		return dis_row.get(8);
	}
	
}
