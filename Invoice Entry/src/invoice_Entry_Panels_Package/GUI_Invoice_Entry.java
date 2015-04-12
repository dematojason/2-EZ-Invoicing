package invoice_Entry_Panels_Package;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI_Invoice_Entry {
	JFrame frame;
	Panel_Invoice_Entry_Warehouse panel_invoice_warehouse;
	Panel_Invoice_Entry_Standard panel_invoice_standard;
	Panel_Invoice_Entry_Chep panel_invoice_chep;
	
	public GUI_Invoice_Entry() {
		String invoice_entry_type[] = {"Standard", "Warehouse", "Chep"};
		this.frame = new JFrame(invoice_entry_type[2] + " Invoice Entry");
		
		/*this.panel_invoice_warehouse = new Panel_Invoice_Entry_Warehouse(frame);
		this.panel_invoice_standard = new Panel_Invoice_Entry_Standard(frame);
		this.panel_invoice_chep = new Panel_Invoice_Entry_Chep(frame);*/
		
		//panel_invoice_standard.getInvoiceEntry_Standard();
		//panel_invoice_warehouse.getInvoiceEntry_Warehouse();
		panel_invoice_chep.getInvoiceEntry_Chep();
		
	}
	
	public void getWarehouseInvoiceEntryJPanel() {
		panel_invoice_warehouse.getInvoiceEntry_Warehouse();
	}
	
	public void getStandardInvoiceEntryJPanel() {
		panel_invoice_standard.getInvoiceEntry_Standard();
	}
	
	public void getChepInvoiceEntryJPanel() {
		panel_invoice_chep.getInvoiceEntry_Chep();
	}
	
}