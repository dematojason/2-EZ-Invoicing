package invoice_Entry_Panels_Package;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import xlsx_Extractor_Package.XLSX_Extractor;

public class Invoice_Entry_Validator {
	
	public static int CHEP = 0;
	public static int Delivery = 1;
	public static int Standard = 2;
	
	public static String err_msg;
	public static String[][] data;
	
	public static boolean isValid(String[][] data, int ident) {
		Invoice_Entry_Validator.data = data;
		boolean isValid = true;
		switch(ident) {
			case 0: // Chep
				double percent = 0;
				for(int i = 0; i < data.length; i++) {
					if(!isValidInvoice(0))
					{
						isValid = false;
						generateError(0,i,1);
					}
					if(!isDate(data[i][2])) {
						isValid = false; 
						generateError(0,i,2);
					}
					//percent - iterate i = 0 to data.length data[i][5] = 100
					percent += Double.parseDouble(data[i][5]);
					//net total
					if(!isDouble(data[i][6])){
						isValid = false; 
						generateError(0,i,6); 
					}
				}
				if(!isPercent(percent)) {
					isValid = false; 
					generateError(0,0,5);
				}
				
				break;
				
			case 1: // Delivery 		
				for(int i = 0; i < data.length; i++) {
					if(!isValidInvoice(1))
					{
						isValid = false;
						generateError(1,i,1);
					}
					if(!isDate(data[i][2])) { 
						isValid = false; 
						generateError(1,i,2); 
						}
					//Charge Amount - one charge over 9000 dollars - vegeta
					if(!isValidAmount(data[i][4])) {
						isValid = false;  
						generateError(1,i,4);
					}
					// Reference check
					/*if(!isValidReference(data[i][5])) { 
						isValid = false; 
						generateError(1,i,5);
					}*/
					//Delivery Date - check 
					if(!isDeliveryDate(data[i][6])) {
						isValid = false;
						generateError(1,i,6);
					}
				}
				break;
				
			case 2: // Standard
				for(int i = 0; i < data.length; i++) {
					if(!isValidInvoice(1))
					{
						isValid = false;
						generateError(2,i,1);
					}
					if(!isDate(data[i][2])) {
						isValid = false;
						generateError(2,i,2);
					}
					//amount
					if(!isValidAmount(data[i][4])){
						isValid = false; 
						generateError(2,i,4);
					}
					//Ref Number
					if(!isValidReference(data[i][5])) {
						isValid = false; 
						generateError(2,i,5);
					}
					
				}
				break;
				
			default:
				break;
				
		}
		return isValid;
		
	}
	
	private static boolean isValidInvoice(int ident) {
		File file = new File("Some path Holiday File");
		
		if(ident == 0) {
			XLSX_Extractor sheet_one = new XLSX_Extractor(file, 0);
			String[][] tmp = XLSX_Extractor.convertObjToStringArray(sheet_one.getCellData());
			for(int i = 0; i < data.length; i++) {
				for(int j = 0; j < tmp.length; j++) {
					if((data[i][1].compareTo(tmp[j][0]) == 0) &&
							(data[i][3].compareTo(tmp[j][3]) == 0) &&
								(data[i][5].compareTo(tmp[j][11]) == 0)) {
						return false;
					}
				}
			}
			return true;
		}
		else if (ident == 1) {
			XLSX_Extractor sheet_one = new XLSX_Extractor(file, 1);
			String[][] tmp = XLSX_Extractor.convertObjToStringArray(sheet_one.getCellData());
			for(int i = 0; i < data.length; i++) {
				for(int j = 0; j < tmp.length; j++) {
					if((data[i][1].compareTo(tmp[j][1]) == 0) &&
							(data[i][3].compareTo(tmp[j][3]) == 0) &&
								(data[i][6].compareTo(tmp[j][6]) == 0)) {
						return false;
					}
				}
			}
			return true;
		}
		else
			return false;
	} 

	private static boolean isDeliveryDate(String string) { // needs jasons attention for limitations
		String[] tmp = string.split("/");
		int[] date = new int[tmp.length];
		
		if(date.length != 3) {
			return false;
		}
		for(int i = 0; i < date.length; i++) {
			date[i] = Integer.parseInt(tmp[i]);
		}
		
		Calendar cal = new GregorianCalendar(date[2], date[0]-1, date[1]);
		
		//check valid date
		boolean is_valid_date = true;
		if(cal.get(Calendar.YEAR) != date[2]) is_valid_date = false;
		if(cal.get(Calendar.MONTH)+1 != date[0]) is_valid_date = false;
		if(cal.get(Calendar.DAY_OF_MONTH) != date[1]) is_valid_date = false;
		return is_valid_date;
		
		/****** THIS IS USED FOR SETTING A LIMIT ON HOW EARLY OR LATE A INVOICE CAN BE ENTERED
		
		Calendar today = new GregorianCalendar();
		Calendar limit_date;
		if(today.MONTH < 8) 
		limit_date = new GregorianCalendar( today.get(Calendar.YEAR), 
											today.get(Calendar.MONTH)+4, 
											today.get(Calendar.DAY_OF_MONTH) ); // limit date is 4 months ahead of current time. 
		else																	
		limit_date = new GregorianCalendar(	today.get(Calendar.YEAR)+1, 
											today.get(Calendar.MONTH)+4%11, 
											today.get(Calendar.DAY_OF_MONTH) );
			

		if(delivery_date.compareTo(today) <= 0) 
		return false;
		else if(delivery_date.compareTo(limit_date) >= 0)
		return false;
		else*/
		
	}
	private static boolean isValidReference(String string) { // done
		//Reference Number - yes similar to invoice || Holiday sheet 0 11 - 34, sheet 1 4 - 9
		// if ref exists -> which row it is
		// else ref couldnt be found
		// 		would you like to create
		File file = new File("Some path Holiday File");
		XLSX_Extractor sheet_one = new XLSX_Extractor(file, 0);
		XLSX_Extractor sheet_two = new XLSX_Extractor(file, 1);
		String[][] tmp = XLSX_Extractor.convertObjToStringArray(sheet_one.getCellData());
		// check sheet 1
		for(int i = 0; i < tmp.length; i++) {
			if(tmp[i][11].compareTo(string) == 0 || tmp[i][34].compareTo(string) == 0) {
				return true;
			}
		}
		// check sheet 2
		tmp = XLSX_Extractor.convertObjToStringArray(sheet_two.getCellData());
		for(int i = 0; i < tmp.length; i++) {
			if(tmp[i][4].compareTo(string) == 0 || tmp[i][9].compareTo(string) == 0) {
				return true;
			}
		}
		//default to false - no match
		return false;
	}
	private static boolean isValidAmount(String string) { // done
		double tmp = Double.parseDouble(string);
		if(tmp < 0) {
			return false;
		}
		if(tmp > 9000) {
			int decision = JOptionPane.showConfirmDialog (null, "Amount is over 9000! Are you sure this is correct?","Warning!", JOptionPane.YES_NO_OPTION);
			if(decision == JOptionPane.YES_OPTION)
				return true;
			else 
				return false;
		}
		return true;
	}

	private static boolean isPercent(double percent) { // done
		if(percent < 100.1 && percent > 99.9) return true;
		return false;
	}
	private static boolean isDouble(String str) { // done
		
		try {
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException err) {
			return false;
		}
		
	}
	private static boolean isDate(String string) { // done

		String[] tmp = string.split("/");
		int[] date = new int[tmp.length];
		
		if(date.length != 3) {
			return false;
		}
		for(int i = 0; i < date.length; i++) {
			date[i] = Integer.parseInt(tmp[i]);
		}
		
		Calendar cal = new GregorianCalendar(date[2], date[0]-1, date[1]);
		
		//check valid date
		boolean is_valid_date = true;
		if(cal.get(Calendar.YEAR) != date[2]) is_valid_date = false;
		if(cal.get(Calendar.MONTH)+1 != date[0]) is_valid_date = false;
		if(cal.get(Calendar.DAY_OF_MONTH) != date[1]) is_valid_date = false;
		return is_valid_date;
	}
	private static void generateError(int i, int j, int k) { // somewhat done depending if you want to check more shit.
		
		switch(i) {
		
			case 0: // CHEP
				switch(k) {
					case 1: // date
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid invoice number. [Value Entered: '" + data[j][k] +"']";
						return;;
					case 2: // date
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid date. [Value Entered: '" + data[j][k] +"']";
						return;;
					case 4:
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid region. [Value Entered: '" + data[j][k] +"']";
						return;
					case 5:
						err_msg = "Error: The percentages of total invoices are invalid. The sum of all percentages should be 100%";
						return;
					case 6:
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid net total. [Value Entered: '" + data[j][k] +"']";
						return;
					default:
						break;;
				}
				
			case 1: // Delivery
				switch(k) {
				case 2: 
					err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid date. [Value Entered: '" + data[j][k] +"']";
					return;
				case 4:
					err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid amount. [Value Entered: '" + data[j][k] +"']";
					return;
				case 5: 
					err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid reference number. [Value Entered: '" + data[j][k] +"']";
					return;
				case 6:
					err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid delivery date. [Value Entered: '" + data[j][k] +"']";
					return;
				default:
					break;
				}
				
				
			case 2:
				switch(k) {
					case 2: 
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid date. [Value Entered: '" + data[j][k] +"']";
						return;
					case 4:
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid amount. [Value Entered: '" + data[j][k] +"']";
						return;
					case 5: 
						err_msg = "Error: Invoice Entry Number " + j+1 + " has an invalid reference number. [Value Entered: '" + data[j][k] +"']";
						return;
					default:
						break;
				}
				
			default:
				break;
		}
	}
}

