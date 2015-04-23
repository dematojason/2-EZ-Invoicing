package invoice_Entry_Panels_Package;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Panel_Invoice_Entry_Chep extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	Dimension iec_dimension = new Dimension(650,350);
	
	JLabel label_main_invoice_info;
	JLabel label_main_product_breakup;
	
	JLabel label_account;
	JLabel label_invoice_number;
	JLabel label_invoice_date;
	JLabel label_invoice_date_example;
	JLabel label_reference;
	JLabel label_sub_total;
	JLabel label_sub_total_dollars;
	JLabel label_tax;
	JLabel label_tax_dollars;
	JLabel label_net_total;
	JLabel label_net_total_dollars;
	
	JTextField text_account;
	JTextField text_invoice_number;
	JTextField text_invoice_date;
	JTextField text_reference;
	JTextField text_sub_total;
	JTextField text_tax;
	JTextField text_net_total;
	
	JButton button_submit;
	JButton button_cancel;
	
	String answer_buffer;
	int answer;
	
	//label and text box field for product description
	JLabel[] label_product;
	JTextField[] text_product;
	//label and text box field for product region
	JLabel[] label_region;
	JTextField[] text_region;
	//label and text box field for percentage of invoice charge
	JLabel[] label_percent;
	JTextField[] text_percent;
	
	String[] string_text_product;
	String[] string_text_region;
	String[] string_text_percentage;
	
	Invoice_Entry_toImportSheet insertDataToImportSheet;
	
	public Panel_Invoice_Entry_Chep() {
		
		this.label_main_invoice_info = new JLabel("Invoice Information");
		this.label_main_product_breakup = new JLabel("Product Breakup");
		
		this.label_account = new JLabel("Account Name:");
		this.label_invoice_number = new JLabel("Invoice Number:");
		this.label_invoice_date = new JLabel("Invoice Date:");
		this.label_invoice_date_example = new JLabel("ex. 11/24/15");
		this.label_reference = new JLabel("Product Reference #:");
		this.label_sub_total = new JLabel("Sub-Total:");
		this.label_sub_total_dollars = new JLabel("$");
		this.label_tax = new JLabel("Tax:");
		this.label_tax_dollars = new JLabel("$");
		this.label_net_total = new JLabel("Net Total: $");
		this.label_net_total_dollars = new JLabel("$");
		
		this.text_account = new JTextField(15);
		this.text_invoice_number = new JTextField(15);
		this.text_invoice_date = new JTextField(15);
		this.text_reference = new JTextField(15);
		this.text_sub_total = new JTextField(5);
		this.text_tax = new JTextField(5);
		this.text_net_total = new JTextField(5);
		
		this.button_submit = new JButton("Submit");
		this.button_cancel = new JButton("Cancel");
		
		getInvoiceEntry_Chep();
	}
	
	public void getInvoiceEntry_Chep() {
		/*Custom_Functions my_frame = new Custom_Functions();*/
		setLayout(new MigLayout());
		setPreferredSize(iec_dimension);
		
		//add action listeners to JButtons
		button_cancel.addActionListener(this);
		button_submit.addActionListener(this);
		
		/*change font info for certain labels
		default JLabel font =  "Sans Serif" , Font.PLAIN , 12
		made example label for date formatting font size smaller (from 12 to 9).
		made title labels font bold and bigger (12 to 14).*/
		label_invoice_date_example.setFont(new Font("Sans Serif", Font.PLAIN, 10));
		label_main_invoice_info.setFont(new Font("Sans Serif", Font.BOLD, 14));
		label_main_product_breakup.setFont(new Font("Sans Serif", Font.BOLD, 14));
		
		//title label
		add(label_main_invoice_info, "span, center, gapbottom 10");
		
		//add normal labels & text boxes to the panel
		add(label_account, "r");
		add(text_account, "wrap, wmax 100");
		
		add(label_invoice_number, "r");
		add(text_invoice_number, "pushx, growx, wrap");
		
		add(label_invoice_date, "r");
		add(text_invoice_date, "split 2, pushx, growx, wmax 60");
		add(label_invoice_date_example, "wrap");
		
		add(label_net_total, "r");
		/*add(label_net_total_dollars, "split 3, pushx, growx, wmax 60");*/
		add(text_net_total, "l, wrap, wmax 50, gapbottom 15");
		
		add(label_main_product_breakup, "span, center, gapbottom 5");
		
		try {
			String[] count_charges_options = {"1","2","3","4","5","6","7","8","9","10"}; 
			this.answer_buffer = (String) JOptionPane.showInputDialog(null, 
					"How many product/region combinations are needed for this invoice?", 
					"Product/Region Combinations", 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					count_charges_options, 
					"1");
			
			if(answer_buffer != null){
				answer = Integer.parseInt(answer_buffer); //Parse String to int
			}else{ //User clicked cancel on input dialog box
				System.exit(0);
			}
			
			/*create labels and text box fields (size based on user input) for product description, 
			region, and percentage of invoice charge*/
			
			//label and text box field for product description
			this.label_product = new JLabel[answer];
			this.text_product = new JTextField[answer];
			//label and text box field for product region
			this.label_region = new JLabel[answer];
			this.text_region = new JTextField[answer];
			//label and text box field for percentage of invoice charge
			this.label_percent = new JLabel[answer];
			this.text_percent = new JTextField[answer];
			
			for(int i = 0; i < answer; i++) {
				label_product[i] = new JLabel("Product Description:");
				text_product[i] = new JTextField("");
				label_region[i] = new JLabel("Region:");
				text_region[i] = new JTextField("");
				label_percent[i] = new JLabel(" %");
				text_percent[i] = new JTextField("");
				if(i != answer - 1) {
					add(label_product[i], "r");
					add(text_product[i], "split 4, pushx, growx, wmax 200, gapright 10px");
					add(label_region[i], "r");
					add(text_region[i], "pushx, growx, wmin 15");
					add(label_percent[i], "r");
					add(text_percent[i], "wrap, pushx, growx, wmin 7");
				}else{
					add(label_product[i], "r");
					add(text_product[i], "split 4, pushx, growx, wmax 200, gapright 10px");
					add(label_region[i], "r");
					add(text_region[i], "pushx, growx, wmin 15");
					add(label_percent[i], "r");
					add(text_percent[i], "wrap, pushx, growx, wmin 7, gapbottom 15");
				}
			}
			
			add(button_submit, "tag ok, span, split 2, sizegroup bttn");
			add(button_cancel, "tag cancel, sizegroup bttn");
			
			/*frame.setResizable(true);
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			my_frame.setWindowPosition_Center();
			frame.setVisible(true);*/
			
		}catch(NumberFormatException err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand(); //sets String action equal to the string identifying the command for this event
		if(action.equals("Reset")) {
			System.exit(0);
		}else if(action.equals("Submit")) {
			String[][] data = new String[answer][8];
			Date date = new Date();
			String dateStr= new SimpleDateFormat("MM/dd/yyyy").format(date);
			for(int i = 0; i < answer; i++) {
				data[i][0] = text_account.getText();
				data[i][1] = text_invoice_number.getText();
				data[i][2] = text_invoice_date.getText();
				data[i][3] = text_product[i].getText();
				data[i][4] = text_region[i].getText();
				data[i][5] = text_percent[i].getText();
				data[i][6] = text_net_total.getText();
				data[i][7] = dateStr;
			}
			insertDataToImportSheet = new Invoice_Entry_toImportSheet(data, 1);
			insertDataToImportSheet.insertImportSheetData();
			
			clearTextFields();
		}
	}
	
	private void clearTextFields() {
		
		text_account.setText("");
		text_invoice_number.setText("");
		text_invoice_date.setText("");
		text_net_total.setText("");
		for(int i = 0; i < answer; i++) {
			text_product[i].setText("");
			text_region[i].setText("");
			text_percent[i].setText("");
		}
		
	}
	
	public Dimension getDimension() {
		return iec_dimension;
	}
	
}