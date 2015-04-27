package GUI;

import invoice_Entry_Panels_Package.*;

import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Import_To_Logistics_And_Invoice_Tracking.Do_It;
import Import_To_Logistics_And_Invoice_Tracking.Import_Invoice_Charges;

import java.awt.*;
import java.awt.event.*;

public class GUI_Frame {
		
		JFrame frame;
		Background_Panel content_pane;
		JFileChooser file_chooser;
		
		public GUI_Frame(App app) {
				frame_setup();
				menu_bar_setup();
				app.start();
		}
		
		public void frame_setup() {
			
			frame = new JFrame("2EZ Invoicing");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ArrayList<Image> icons = new ArrayList<Image>(3);
			for(int i = 4; i < 7; i++) {
				String tmp = "/res/icon/squareLogo_" + (2 << i-1) + "x" + (2 << i-1) + ".png";
				try {
					icons.add(ImageIO.read(getClass().getResource(tmp)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			frame.setIconImages(icons);
			content_pane = new Background_Panel();
			frame.setContentPane(content_pane);
			JFrame.setDefaultLookAndFeelDecorated(true);
			frame.pack();
			frame.setLocationRelativeTo(null); //center frame
			frame.setResizable(false);
			frame.setVisible(true);
			
		}
		
		public void menu_bar_setup() {
				
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
									scroll_pane.getHorizontalScrollBar();
									scroll_pane.setHorizontalScrollBarPolicy(
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
											scroll_pane.setVerticalScrollBarPolicy(
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
								JOptionPane.showOptionDialog(null, 
										iec_panel,
										"CHEP Invoice Entry",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.INFORMATION_MESSAGE, 
									    null,
									    new Object[]{},
									    null);
							}
						});
						
						JMenuItem standard_invoice_entry = new JMenuItem("IBC / Nature's Intent Invoice Entry");
						standard_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Panel_Invoice_Entry_Standard ies_panel = new Panel_Invoice_Entry_Standard();
								JOptionPane.showOptionDialog(null, 
										ies_panel,"IBC / Nature's Intent Invoice Entry", 
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.INFORMATION_MESSAGE, 
										null,
										new Object[]{}, 
										null);
								Do_It now = new Do_It(0);
								now.importDataNow();
							}
						});
						JMenuItem delivery_invoice_entry = new JMenuItem("Delivery Invoice Entry");
						delivery_invoice_entry.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Panel_Invoice_Entry_Delivery ied_panel = new Panel_Invoice_Entry_Delivery();
								JOptionPane.showOptionDialog(null, 
										ied_panel,
										"Chep Panel", 
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.INFORMATION_MESSAGE, 
										null, 
										new Object[]{}, 
										null);
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
						invoice_menu.add(delivery_invoice_entry);
						// invoice_menu.add(nonapproved_invoice_entry);
						// invoice_menu.add(approve_nonapproved_invoice_entry);
						
				/*
				* Create an invoice menu
				**********************************************************************************************************************/
				
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
						
				/*
				* Search an invoice menu
				**********************************************************************************************************************/
				
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
			frame.repaint();
			content_pane.revalidate();
		}
	
}