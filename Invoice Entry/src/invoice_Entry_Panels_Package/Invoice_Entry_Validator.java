package invoice_Entry_Panels_Package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class Invoice_Entry_Validator {
	
	public static int CHEP = 0;
	public static int Delivery = 1;
	public static int Standard = 2;
	

	String[][] data;
	String err_msg;
	
	public boolean isValid(String[][] data, int ident) {
		
		boolean isValid = true;
		switch(ident) {
			case 0: // Chep
				double percent = 0;
				for(int i = 0; i < data.length; i++) {
					//Invoice Number 
					//invoice date - check valid date
					if(!isDate(data[i][2])) isValid = false; generateError(i,2);
					//region
					if(!isRegion(data[i][4])) isValid = false; generateError(i,4);
					//percent - iterate i = 0 to data.length data[i][5] = 100
					percent += Double.parseDouble(data[i][5]);
					//net total
					if(!isDouble(data[i][6])) isValid = false; generateError(i,6); 
				}
				if(!isPercent(percent)) isValid = false; generateError(0,5);
				
				if(isValid == true)
					return true;
				else
					return false;
				
			case 1: // Delivery 		
				for(int i = 0; i < data.length; i++) {
					//Invoice Number - yes
					//Invoice Date - yes
					if(!isDate(data[i][2])) isValid = false; generateError(i,2);
					//Charge Amount - one charge over 9000 dollars - vegeta
					if(!isValidAmount(data[i][4])) isValid = false; generateError(i,4);
					//Reference Number - yes similar to invoice || Holiday sheet 0 11 - 34, sheet 1 4 - 9
					if(!isValidReference(data[i][5])) isValid = false; generateError(i,5);
					//Delivery Date - check 
					if(!isDeliveryDate(data[i][6])) isValid = false; generateError(i,6);
				}
				if(isValid == true)
					return true;
				else
					return false;
				
			case 2:
				for(int i = 0; i < data.length; i++) {
					//invoice number
					//invoice data
					if(!isDate(data[i][2])) isValid = false; generateError(i,2);
					//amount
					if(!isValidAmount(data[i][4])) isValid = false; generateError(i,4);
					//Ref Number
					if(!isValidReference(data[i][5])) isValid = false; generateError(i,5);
					
				}
				
			default:
				return false;
		}
		
	}
	
	@SuppressWarnings("static-access")
	private boolean isDeliveryDate(String string) {
		String[] tmp = string.split("/");
		int[] date = new int[tmp.length];
		for(int i = 0; i < date.length; i++) {
			date[i] = Integer.parseInt(tmp[i]);
		}
		Calendar today = new GregorianCalendar();
		Calendar delivery_date = new GregorianCalendar(date[2], date[1], date[0]);
		Calendar limit_date;
		if(today.MONTH < 9) 
			limit_date = new GregorianCalendar(today.YEAR, today.MONTH+4, today.DAY_OF_MONTH);
		else
			limit_date = new GregorianCalendar(today.YEAR+1, today.MONTH+4%12, today.DAY_OF_MONTH);
		
		if(delivery_date.compareTo(today) <= 0) 
			return false;
		else if(delivery_date.compareTo(limit_date) >= 0)
			return false;
		else
			return true;
		
	}

	private boolean isValidReference(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isValidAmount(String string) {
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
		return false;
	}

	private boolean isInvoiceNumber(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isRegion(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isProduct(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isPercent(double percent) {
		if(percent < 100.1 && percent > 99.9) return true;
		return false;
	}

	private void generateError(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	private boolean isDouble(String str) {
		
		try {
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException err) {
			return false;
		}
		
	}
	private boolean isDate(String str) {
		
		if(str == null) {
			return false;
		}
		//set the format to use as a constructor argument
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		
		if(str.trim().length() != sdf.toPattern().length()) {
			return false;
		}
		
		sdf.setLenient(false);
		try {
			//attempt to parse the string passed into a date
			sdf.parse(str.trim());
		}catch(ParseException err) {
			return false;
		}
		return true;
		
	}
	
}

