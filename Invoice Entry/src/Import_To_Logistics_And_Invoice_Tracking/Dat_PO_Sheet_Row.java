package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Dat_PO_Sheet_Row {

	ArrayList<String> dis_row;
	
	public Dat_PO_Sheet_Row(ArrayList<String> pass_dat_row) {
		this.dis_row = pass_dat_row;
	}
	
	public ArrayList<String> getInvoiceCodingInfo(boolean isDelivery) {
		ArrayList<String> codingInfo = new ArrayList<String>();
		
		codingInfo.add(getSupplier());
		codingInfo.add(getProductName());
		codingInfo.add(getRegion());
		codingInfo.add(getPurchaseOrderNumber());
		codingInfo.add(getContainerNumber());
		if(isDelivery) {
			codingInfo.add(getChargeDelivery());
			codingInfo.add(getDateDelivered());
			codingInfo.add(getCasesOrdered());
			codingInfo.add(getCasesReceived());
		}
		return codingInfo;
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
	public String getSupplier() {
		return dis_row.get(0);
	}
	public String getCustomer() {
		return dis_row.get(1);
	}
	public String getDepot() {
		return dis_row.get(2);
	}
	public String getShipToAddress() {
		return dis_row.get(3);
	}
	public String getRegion() {
		return dis_row.get(4);
	}
	public String getReqETA() {
		return dis_row.get(5);
	}
	public String getDateDelivered() {
		return dis_row.get(6);
	}
	public String getPortETA() {
		return dis_row.get(7);
	}
	public String getPortOfEntry() {
		return dis_row.get(8);
	}
	public String getEstShip() {
		return dis_row.get(9);
	}
	public String getTransitTime() {
		return dis_row.get(10);
	}
	public String getPurchaseOrderNumber() {
		return dis_row.get(11);
	}
	public String getProductName() {
		return dis_row.get(12);
	}
	public String getItemNumber() {
		return dis_row.get(13);
	}
	public String getChargeOceanFreight() {
		return dis_row.get(14);
	}
	public String getChargeDocumentation() {
		return dis_row.get(15);
	}
	public String getChargeCustomsBroker() {
		return dis_row.get(16);
	}
	public String getChargeDuty() {
		return dis_row.get(17);
	}
	public String getChargeGST() {
		return dis_row.get(18);
	}
	public String getChargeWarehouse() {
		return dis_row.get(19);
	}
	public String getChargeDrayage() {
		return dis_row.get(20);
	}
	public String getChargeDelivery() {
		return dis_row.get(21);
	}
	public String getChargeMisc() {
		return dis_row.get(22);
	}
	public String getChargeExWorks() {
		return dis_row.get(23);
	}
	public String getChargeSubTotal() {
		return dis_row.get(24);
	}
	public String getInvoiceDate() {
		return dis_row.get(25);
	}
	public String getInvoiceNumber() {
		return dis_row.get(26);
	}
	public String getInvoiceAmount() {
		return dis_row.get(27);
	}
	public String getCasePrice() {
		return dis_row.get(28);
	}
	public String getCasesOrdered() {
		return dis_row.get(29);
	}
	public String getCasesReceived() {
		return dis_row.get(30);
	}
	public String getHandling() {
		return dis_row.get(31);
	}
	public String getInlandCarrier() {
		return dis_row.get(32);
	}
	public String getVessel() {
		return dis_row.get(33);
	}
	public String getContainerNumber() {
		return dis_row.get(34);
	}
	public String getSealNumber() {
		return dis_row.get(35);
	}
	public String getContainerType() {
		return dis_row.get(36);
	}
	public String getComments() {
		return dis_row.get(37);
	}
	public String getInvoiceValue() {
		return dis_row.get(38);
	}
	public String getDiscount() {
		return dis_row.get(39);
	}
	public String getGrossInvoice() {
		return dis_row.get(40);
	}
	public String getInvoice() {
		return dis_row.get(41);
	}
	public String getChepReported() {
		return dis_row.get(42);
	}
	
}
