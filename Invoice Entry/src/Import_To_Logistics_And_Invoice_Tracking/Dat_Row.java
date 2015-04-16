package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Dat_Row {

	ArrayList<String> dis_row;
	int dis_sheet_type;
	/*
	 * Row Types:
	 * 0 = import sheet
	 * 1 = invoice tracking sheet
	 * 2 = purchase order destination sheet
	 * 3 = bulk destination sheet
	 */
	
	public Dat_Row(ArrayList<String> pass_dat_row, int pass_dat_sheet_type) {
		this.dis_row = pass_dat_row;
		this.dis_sheet_type = pass_dat_sheet_type;
	}
	
	public String getInvoiceNumber() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(0);
			case 2: //purchase order destination sheet
				 return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getInvoiceCompany() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(1);
			case 2: //purchase order destination sheet
				 return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getInvoiceDate() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(2);
			case 2: //purchase order destination sheet
				 return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}	
	}
	
	public String getChargeType() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(3);
			case 2: //purchase order destination sheet
				 return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}	
	}
	
	public String getExistingCharge(int charge_col) {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return "#ERR";
			case 1: //invoice tracking sheet
				return dis_row.get(4);
			case 2: //purchase order destination sheet
				return dis_row.get(charge_col);
			case 3: //bulk destination sheet
				return dis_row.get(charge_col);
			default:
				return "default #ERR";
		}	
	}
	
	public String getFinalCharge() {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return "#ERR";
			case 1: //invoice tracking sheet
				return dis_row.get(5);
			case 2: //purchase order destination sheet
				return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getPodDeliveryDate() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(6);
			case 2: //purchase order destination sheet
				return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getEntryDate() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(7);
			case 2: //purchase order destination sheet
				return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getNotes() {
		switch(dis_sheet_type) {
			case 0: //import sheet
			case 1: //invoice tracking sheet
				return dis_row.get(8);
			case 2: //purchase order destination sheet
				return "#ERR";
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getReferenceNumber() {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return dis_row.get(5);
			case 1: //invoice tracking sheet
				return dis_row.get(10);
			case 2: //purchase order destination sheet
				return "#ERR: Must specify type of reference number";
			case 3: //bulk destination sheet
				return "#ERR Must specify type of reference number";
			default:
				return "default #ERR";
		}
	}
	
	public String getReferenceNumber(int reference_type) {
		/*
		 * reference types:
		 * 0 = purchase order number
		 * 1 = container number
		 */
		if(reference_type != 0 && reference_type != 1) {
			System.out.println("Invalid reference type passed into method getReferenceNumber.");
			System.exit(0);
			return null;
		}
		
		switch(dis_sheet_type) {
			case 0: //import sheet
				return dis_row.get(5);
			case 1: //invoice tracking sheet
				return dis_row.get(10);
			case 2: //purchase order destination sheet
				if(reference_type == 0) { //purchase order number
					return dis_row.get(11);
				}else{ //container number
					return dis_row.get(34);
				}
			case 3: //bulk destination sheet
				if(reference_type == 0) { //purchase order number
					return dis_row.get(4);
				}else{ //container number
					return dis_row.get(9);
				}
			default:
				return "default #ERR";
		}
		
	}
	
	public String getVendorname() {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return "#ERR";
			case 1: //invoice tracking sheet
				return dis_row.get(11);
			case 2: //purchase order destination sheet
				return dis_row.get(0);
			case 3: //bulk destination sheet
				return dis_row.get(2);
			default:
				return "default #ERR";
		}
	}
	
	public String getRegion() {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return "#ERR";
			case 1: //invoice tracking sheet
				return dis_row.get(12);
			case 2: //purchase order destination sheet
				return dis_row.get(4);
			case 3: //bulk destination sheet
				return "#ERR";
			default:
				return "default #ERR";
		}
	}
	
	public String getDeliveryDatePortETA() {
		switch(dis_sheet_type) {
			case 0: //import sheet
				return "#ERR";
			case 1: //invoice tracking sheet
				return dis_row.get(13);
			case 2: //purchase order destination sheet
				return dis_row.get(6);
			case 3: //bulk destination sheet
				return dis_row.get(5);
			default:
				return "default #ERR";
		}
	}
	
}












