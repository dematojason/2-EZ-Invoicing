package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Dat_Bulk_Sheet_Row {

	ArrayList<String> dis_row;
	
	public Dat_Bulk_Sheet_Row(ArrayList<String> pass_dat_row) {
		this.dis_row = pass_dat_row;
	}
	
	public String getChargeByName(String charge) {
		switch(charge) {
			case "Ocean Freight":
				return getChargeOceanFreight();
			case "Documentation":
				return getChargeDocumentation();
			case "Clearance":
			case "Customs Broker":
				return getChargeCustomsBroker();
			case "Duty":
				return getChargeDuty();
			case "GST":
				return getChargeGST();
			case "Warehouse":
				return getChargeWarehouse();
			case "Drayage":
				return getChargeDrayage();
			case "Delivery":
				return getChargeDelivery();
			case "Misc":
			case "Miscellaneous":
				return getChargeMisc();
			case "Ex Works":
			case "Inland Europe":
			case "Inland Freight":
				return getChargeExWorks();
			default:
				System.out.println("The charge type you are looking for could not be found");
				System.exit(0);
				return null;
		}
	}
	public String getProductNumber() {
		return dis_row.get(0);
	}
	public String getDescription() {
		return dis_row.get(1);
	}
	public String getVendor() {
		return dis_row.get(2);
	}
	public String getCustomer() {
		return dis_row.get(3);
	}
	public String getPurchaseOrderNumber() {
		return dis_row.get(4);
	}
	public String getPortETA() {
		return dis_row.get(5);
	}
	public String getPort() {
		return dis_row.get(6);
	}
	public String getQuantity() {
		return dis_row.get(7);
	}
	public String getVessel() {
		return dis_row.get(8);
	}
	public String getContainerNumber() {
		return dis_row.get(9);
	}
	public String getChargeOceanFreight() {
		return dis_row.get(10);
	}
	public String getChargeDocumentation() {
		return dis_row.get(11);
	}
	public String getChargeCustomsBroker() {
		return dis_row.get(12);
	}
	public String getChargeDuty() {
		return dis_row.get(13);
	}
	public String getChargeGST() {
		return dis_row.get(14);
	}
	public String getChargeWarehouse() {
		return dis_row.get(15);
	}
	public String getChargeDrayage() {
		return dis_row.get(16);
	}
	public String getChargeDelivery() {
		return dis_row.get(17);
	}
	public String getChargeMisc() {
		return dis_row.get(18);
	}
	public String getChargeExWorks() {
		return dis_row.get(19);
	}
	public String getChargeSubTotal() {
		return dis_row.get(20);
	}
	
}
