package check_user_input;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Check_Chep {

	String[][] data;
	String err_msg;
	
	public Check_Chep(String[][] data) {
		this.data = data;
	}
	
	public boolean hasPassedPanelDataTests() {
		
		for(int i = 0; i < data.length; i++) {
			if(isDouble(data[i][6]) == false) {
				this.err_msg = "The value '" + data[i][6] + "' entered into the Net Total field is invalid. Please enter a number value.";
				return false;
			}else if(isDate(data[i][2]) == false) {
				this.err_msg = "The value '" + data[i][2] + "' entered into the Invoice Date field is invalid. Please enter a valid date into the field (e.g. 11/23/15.";
				return false;
			}
		}
		return true;
		
	}
	
	public String getErrorMessage() {
		
		return err_msg;
		
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
