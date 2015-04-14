package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Workbook_Data {
	
	String[][] data;
	int index;
	XSSFWorkbook wb;
	
	public Workbook_Data(String[][] data, int index) {
		this.data = data;
		this.index = index;
		
	}
	
	public ArrayList<Integer> getMatchingColumnNumbers(String search_for) {
	/**********************************************************************************************************************
	* Returns any matching columns based on int index (row index) and String search_for (what will be searched for)
	*/
		
		ArrayList<Integer> matching = new ArrayList<Integer>();
		
		for(int i = 0; i < data[index].length; i++) {
			if(data[index][i] == search_for) {
				matching.add(i + 1);
			}
		}
		//return null if there were no items added to ArrayList
		if(matching.size() > 0) {
			return matching;
		}else{
			return null;
		}
		
	}
	
	public ArrayList<Integer> getMatchingRowNumbers(String search_for) {
	/**********************************************************************************************************************
	* Returns any matching rows based on int index (column) and String search_for (what will be searched for)
	*/
		
		ArrayList<Integer> matching = new ArrayList<Integer>();
		
		for(int i = 0; i < data.length; i++) {
			if(data[i][index] == search_for) {
				matching.add(i + 1);
			}
		}
		//return null if there were no items added to ArrayList
		if(matching.size() > 0) {
			return matching;
		}else{
			return null;
		}
		
	}
	
	public ArrayList<String> getColumnData() {
	/**********************************************************************************************************************
	* Returns all string values in the passed column index as an ArrayList<String>
	*/
			
		ArrayList<String> str_list = new ArrayList<String>();
		
		try {
		    for(int i = 0; i < data.length; i++) {
		    	str_list.add(data[i][index]);
		    }
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		//return null if there were no items added to ArrayList
		if(str_list.size() > 0) {
			return str_list;
		}else{
			return null;
		}
		
	}
	
	public ArrayList<String> getRowData() {
	/**********************************************************************************************************************
	* Returns all string values in the passed row index as an ArrayList<String>
	*/
		
		ArrayList<String> str_list = new ArrayList<String>();
		
		try {
			for(int i = 0; i < data[index].length; i++) {
				str_list.add(data[index][i]);
			}
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		//return null if there were no items added to ArrayList
		if(str_list.size() > 0) {
			return str_list;
		}else{
			return null;
		}
		
	}
	
}
