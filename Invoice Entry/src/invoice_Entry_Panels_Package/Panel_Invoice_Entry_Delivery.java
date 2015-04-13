package invoice_Entry_Panels_Package;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Panel_Invoice_Entry_Delivery extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JTextField[] text_fields = new JTextField[7];
	String[] label_strings= {"Invoice Number",  "Invoice Company", "Invoice Date", 
								"Reference Number", "Charge Type", "Charge Amount",
								"Delivery Date"};
	JLabel[] labels;
	JButton[] buttons = new JButton[2];
	
	
	public Panel_Invoice_Entry_Delivery () {
		
		setLayout(new MigLayout());
		init_and_add_components();		
	}
	
	public void init_and_add_components() {
		labels = new JLabel[label_strings.length];
		for(int i = 0; i < label_strings.length; i++) {
			labels[i] = new JLabel(label_strings[i]);
			labels[i].setFont(new Font("Sans Serif", Font.PLAIN, 10));
			if(i != 4)	{
				text_fields[i] = new JTextField(15);
			}
			else {
				text_fields[i] = new JTextField("Delivery", 15);
				text_fields[i].setEditable(false);
			}
			add(labels[i], "r");
			add(text_fields[i], "wrap, wmax 100");
		}
		for(int i = 0; i < buttons.length; i++) {
			
			if(i == 0) {
				buttons[i] = new JButton("Submit");
				buttons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//do something
					}
				});
			}
			else {
				buttons[i] = new JButton("Submit");
				buttons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//do something
					}
				});
			}
			add(buttons[i]);
		}
	}
}