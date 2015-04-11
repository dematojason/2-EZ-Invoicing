package invoice_Entry_Panels_Package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/*Invoice Company Name: [Text Box]
Invoice Number: [Text Box]
Invoice Date (If Available): [Text Box]
Reason For Holding: [Text Box]*/

public class Panel_Invoice_Entry_Warehouse extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JLabel label_company;
	JLabel label_invoice_number;
	JLabel label_date_invoice;
	JLabel label_reference;
	JLabel label_date_rec;
	JLabel label_quantity;
	JLabel label_charge_type;
	JLabel label_dollar;
	
	JTextField text_company;
	JTextField text_invoice_number;
	JTextField text_date_invoice;
	JTextField text_reference;
	JTextField text_date_rec;
	JTextField text_quantity;
	JTextField text_dollar;
	
	JButton button_insert;
	JButton button_cancel;
	
	String[] charge_types_array;
	String[] invoice_companies;
	
	public Panel_Invoice_Entry_Warehouse() {
		this.label_company = new JLabel("Company (Invoice):");
		this.label_invoice_number = new JLabel("Invoice Number:");
		this.label_date_invoice = new JLabel("Date (Invoice):");
		this.label_reference = new JLabel("Product Reference #:");
		this.label_date_rec = new JLabel("Date Received/Port ETA:");
		this.label_quantity = new JLabel("Quantity:");
		this.label_charge_type = new JLabel("Charge Type:");
		this.label_dollar = new JLabel("$:");
		
		this.text_company = new JTextField(15);
		this.text_invoice_number = new JTextField(15);
		this.text_date_invoice = new JTextField(15);
		this.text_reference = new JTextField(15);
		this.text_date_rec = new JTextField(15);
		this.text_quantity = new JTextField(15);
		this.text_dollar = new JTextField(8);
	
		this.button_insert = new JButton("Insert");
		this.button_cancel = new JButton("Cancel");
		
		this.charge_types_array = new String[]{"Storage/Handling",
				"Bill Of Lading", "Richardson-->DUI", "DUI-->Richardson"};
		//not sure if I want to make this into a ComboBox or leave the TextField
		this.invoice_companies = new String[]{"Hermann", "Matson", "WG Pro",
				"Weber", ""};
		
		getInvoiceEntry_Warehouse();
	}
	
	public void getInvoiceEntry_Warehouse() {
		try {
			/*Custom_Functions my_frame = new Custom_Functions(frame); //add object from Custom_Functions*/
			
			setLayout(new MigLayout()); //set window layout type to MigLayout
			
			button_cancel.addActionListener(new EventHandler()); //add cancel button action listener -- exit program
			
			add(label_company, "split, gap 0px 39px");
			add(text_company, "wrap, pushx, growx");
			
			add(label_invoice_number, "split, gap 0px 55px");
			add(text_invoice_number, "wrap, pushx, growx");
			
			add(label_date_invoice, "split, gap 0px 66px");
			add(text_date_invoice, "wrap, pushx, growx");
			
			add(label_reference, "split, gap 0px 26px");
			add(text_reference, "wrap, pushx, growx");
			
			add(label_date_rec, "split, gap 0px 10px");
			add(text_date_rec, "wrap, pushx, growx");
			
			add(label_quantity, "split, gap 0px 96px");
			add(text_quantity, "wrap, pushx, growx, gapbottom 10");
			
			add(label_charge_type, "split4");
			add(new JComboBox<String>(charge_types_array), "pushx");
			add(label_dollar);
			add(text_dollar, "wrap, pushx, gapbottom 15");
			
			add(button_insert, "split, r");
			add(button_cancel);
			
			/*frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			my_frame.setWindowPosition_Center();
			frame.setVisible(true);*/
			
		}catch(NumberFormatException err) { //user clicked cancel
			err.printStackTrace();
			System.exit(0);
		}
	}
	
	private class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
}