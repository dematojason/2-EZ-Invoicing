package GUI;

import invoice_Entry_Panels_Package.Custom_Functions;
import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Chep;
import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Standard;
import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Warehouse;

import javax.swing.*;

import java.awt.*;
import java.util.*;

public class GUI_Main {

	JFrame frame;
	JPanel cp;
	
	GUI_Main() {
		cp = new JPanel();
		cp.setLayout(new BorderLayout());
		/*cp.setPreferredSize(new Dimension(1200,800));*/
		
		frame = new JFrame("Test Invoice Entry");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(cp);
		frame.pack();
		frame.setVisible(true);
	}
	
	void refresh_frame() {
		frame.revalidate();
		frame.pack();
		Custom_Functions cf = new Custom_Functions(frame);
		cf.setWindowPosition_Center();
	}
	
	 void remove_panel() {
		cp.removeAll();
		refresh_frame();
	}
	
	 void add_panel(JPanel panel ) {
		cp.add(panel, BorderLayout.CENTER);
		refresh_frame();
	}
	
	public static void main(String[] args) {
		
		GUI_Main GUI = new GUI_Main();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a selection: \n1-Chep\n2-Standard\n3-Warehouse");
		int i = sc.nextInt();
		
		switch(i) {
			case 1: {
				Panel_Invoice_Entry_Chep iep = new Panel_Invoice_Entry_Chep();
				GUI.remove_panel();
				GUI.add_panel(iep);
				break;
			}
			case 2: {
				Panel_Invoice_Entry_Standard ies = new Panel_Invoice_Entry_Standard();
				GUI.remove_panel();
				GUI.add_panel(ies);
				break;
			}
			case 3: {
				Panel_Invoice_Entry_Warehouse iew = new Panel_Invoice_Entry_Warehouse();
				GUI.remove_panel();
				GUI.add_panel(iew);
				break;
			}
		}
		sc.close();
	}

}
