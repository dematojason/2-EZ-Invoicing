package invoice_Entry_Panels_Package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

//Creates standard invoice entry panelPleebo1
public class Panel_Invoice_Entry_Standard extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JLabel label_company;
	JLabel label_date;
	JLabel label_reference;
	JLabel label_invoice_number;
	
	JTextField text_company;
	JTextField text_date;
	JTextField text_reference;
	JTextField text_invoice_number;
	
	JButton button_insert;
	JButton button_cancel;
	
	public Panel_Invoice_Entry_Standard() {
		
		this.label_company = new JLabel("Company (Invoice):");
		this.label_invoice_number = new JLabel("Invoice Number:");
		this.label_date = new JLabel("Date (Invoice):");
		this.label_reference = new JLabel("Product Reference #:");
		
		this.text_company = new JTextField(20);
		this.text_invoice_number = new JTextField(20);
		this.text_date = new JTextField(20);
		this.text_reference = new JTextField(20);
		
		this.button_insert = new JButton("Insert");
		this.button_cancel = new JButton("Cancel");
		
		getInvoiceEntry_Standard();
	}
	
	public void getInvoiceEntry_Standard() {
		/*Custom_Functions my_frame = new Custom_Functions(frame);*/
		setLayout(new MigLayout());
		button_cancel.addActionListener(new EventHandler());
		
		/*add components to combo box containing charge types and JComboBox for selecting number of charges on invoice
		count_charges_options made as a String array instead of an integer array because JOptionPane
		can only take Object[] as argument for values*/
		String[] charge_types = {"Ocean Freight","Documentation","Clearance","Duty","GST","Warehouse",
				"Drayage","Delivery","Inland Europe","Miscellaneous"};
		String[] count_charges_options = {"1","2","3","4","5","6","7","8","9","10"}; 
		
		try {
			int number_of_charges = Integer.parseInt((String) JOptionPane.showInputDialog(null, 
					"How many charges are on this invoice?", 
					"Number of Charges", 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					count_charges_options, 
					"0"));
			
			//Create text fields for inputting charge $ amounts... # of text fields equals user specified number of charges
			JTextField [] charge_amount = new JTextField[number_of_charges];
			JLabel [] charge_label = new JLabel[number_of_charges];
			
			add(label_company);
			add(text_company, "wrap, pushx, growx");
			
			add(label_invoice_number);
			add(text_invoice_number, "wrap, pushx, growx");
			
			add(label_date);
			add(text_date, "wrap, pushx, growx");
			
			add(label_reference);
			add(text_reference, "wrap, pushx, growx");
			
			/*Create a JLabel("Charge Type"), combo box containing types of charges,
			JLabel("$"), and text box for entering dollar amount for each charge on invoice*/
			for(int i = 0; i < charge_amount.length; i++) {
				charge_amount[i] = new JTextField("");
				charge_label[i] = new JLabel("$");
				add(new JLabel("Charge Type"));
				add(new JComboBox<String>(charge_types), "split3");
				add(charge_label[i]);
				add(charge_amount[i], "wrap, pushx, growx"); //next row, grow horizontally if frame grows
			}
			
			//add insert and cancel buttons aligned on right side of frame
			add(button_insert, "skip, split2, base, r"); //skip first column, 
			add(button_cancel, "base, r");
			
			/*frame.setResizable(false);
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			my_frame.setWindowPosition_Center(); //Class centers window
			frame.setVisible(true);*/
		}catch(NumberFormatException e) { //user clicked cancel
			System.exit(0);
		}
	
	}
	
	private class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

}