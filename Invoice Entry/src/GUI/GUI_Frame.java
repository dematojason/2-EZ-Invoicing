package GUI;

import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Chep;
import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Standard;
import invoice_Entry_Panels_Package.Panel_Invoice_Entry_Warehouse;

import java.io.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class GUI_Frame {
		
		JFrame frame;
		JPanel content_pane;
		JFileChooser file_chooser;
		
		public GUI_Frame() {
				frame_setup();
				menu_bar_setup();
		}
		
		public void frame_setup()	{
				frame = new JFrame("ASM");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				content_pane = new JPanel();
				content_pane.setLayout(new BorderLayout());	
				content_pane.setPreferredSize(new Dimension(1200,800));
				frame.setContentPane(content_pane);
				frame.setVisible(true);
				refresh_frame();
		}
		
		public void menu_bar_setup()	{
				
				//Menubar
				JMenuBar menu_bar = new JMenuBar();
				
				/**********************************************************************************************************************
				* Create file menu
				*/
						JMenu file_menu = new JMenu("File");
						
						JMenuItem open_file = new JMenuItem("Open");
						open_file.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								try{
									file_chooser = new JFileChooser();
									int returnValue = file_chooser.showOpenDialog(null);
									File selectedFile = null;
									if (returnValue == JFileChooser.APPROVE_OPTION) {
										  selectedFile = file_chooser.getSelectedFile();
									}
									Spreadsheet spreadsheet = new Spreadsheet(selectedFile);
									JScrollPane scroll_pane = new JScrollPane(spreadsheet.getTable());
									content_pane.add(scroll_pane, BorderLayout.CENTER);
									refresh_frame();
								} catch(NullPointerException e1) {
									e1.printStackTrace();
								}
							}
						});
						
						JMenuItem close_file = new JMenuItem("Close");
						close_file.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								content_pane.removeAll();
								refresh_frame();
							}
						});
						
						file_menu.add(open_file);
						file_menu.add(close_file);
						
				
				
				/**********************************************************************************************************************
				* Create an invoice menu
				*/
						JMenu invoice_menu = new JMenu("Create an Invoice");
					
						JMenuItem CHEP_invoice_entry = new JMenuItem("CHEP Invoice Entry");
						CHEP_invoice_entry.addActionListener(new ActionListener() {	
								public void actionPerformed(ActionEvent e)	{
									Panel_Invoice_Entry_Chep iec_panel = new Panel_Invoice_Entry_Chep();
									content_pane.removeAll();
									content_pane.add(iec_panel, BorderLayout.CENTER);
									refresh_frame();
								}
						});
						
						JMenuItem standard_invoice_entry = new JMenuItem("IBC / Nature's Intent Invoice Entry");
						standard_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Panel_Invoice_Entry_Standard ies_panel = new Panel_Invoice_Entry_Standard();
								content_pane.removeAll();
								content_pane.add(ies_panel, BorderLayout.CENTER);
								refresh_frame();
							}
						});
						JMenuItem warehouse_invoice_entry = new JMenuItem("Warehouse Invoice Entry");
						warehouse_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Panel_Invoice_Entry_Warehouse iew_panel = new Panel_Invoice_Entry_Warehouse();
								content_pane.removeAll();
								content_pane.add(iew_panel, BorderLayout.CENTER);
								refresh_frame();
							}
						});
						
						/*
						JMenuItem nonapproved_invoice_entry = new JMenuItem("Non-approved Invoice Entry");
						nonapproved_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						JMenuItem approve_nonapproved_invoice_entry = new JMenuItem("Approve an Invoice Entry on Hold");
						approve_nonapproved_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});		
						*/
						//  Add menu items invoice menu
						invoice_menu.add(CHEP_invoice_entry);
						invoice_menu.add(standard_invoice_entry);
						invoice_menu.add(warehouse_invoice_entry);
						// invoice_menu.add(nonapproved_invoice_entry);
						// invoice_menu.add(approve_nonapproved_invoice_entry);
				/**********************************************************************************************************************
				* Create an invoice menu
				*/
				
				/**********************************************************************************************************************
				* Search an invoice menu
				*/
		
						JMenu search_invoice_menu = new JMenu("Search an Invoice");
						
						JMenuItem invoice_number = new JMenuItem("Invoice Number");
						invoice_number.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						
						JMenuItem invoice_date = new JMenuItem("Invoice Date");
						invoice_date.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						JMenuItem purchase_order_number = new JMenuItem("Purchase Order Number");
						purchase_order_number.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						JMenuItem container_number = new JMenuItem("Container  Number");
						container_number.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						JMenuItem company_name = new JMenuItem("Company Name");
						company_name.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 //Do something
							}
						});
						//Add menu items to search invoice menu
						search_invoice_menu.add(invoice_number);
						search_invoice_menu.add(invoice_date);
						search_invoice_menu.add(purchase_order_number);
						search_invoice_menu.add(container_number);
						search_invoice_menu.add(company_name);
						
				/**********************************************************************************************************************
				* Search an invoice menu
				*/
				
				JMenu search_logisitics = new JMenu("Search Logistics");
				
				//Add menus to menu bar
				menu_bar.add(file_menu);
				menu_bar.add(invoice_menu);
				menu_bar.add(search_invoice_menu);
				
				// set menubar to frame		
				frame.setJMenuBar(menu_bar);
				refresh_frame();
				
		}
		
		public void refresh_frame()	{
			frame.pack();
			frame.revalidate();
			content_pane.revalidate();
		}
		
		public static void main(String[] args) {
			
			GUI_Frame gui = new GUI_Frame();
			
		}
	
}