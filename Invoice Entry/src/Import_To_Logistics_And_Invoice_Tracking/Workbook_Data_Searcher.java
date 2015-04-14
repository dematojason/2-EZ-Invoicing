package Import_To_Logistics_And_Invoice_Tracking;

import java.util.ArrayList;

public class Workbook_Data_Searcher {
	
	String[][] data;
	
	public Workbook_Data_Searcher(String[][] data) {
		this.data = data;
		
	}
	
	public ArrayList<Integer> getMatchingColumnNumbers(String ref_num, int index) {
	/**********************************************************************************************************************
	* Returns any matching columns based on int index (row index) and String search_for (what will be searched for)
	*/
		
		ArrayList<Integer> matching = new ArrayList<Integer>();
		
		for(int i = 0; i < data[index].length; i++) {
			if(data[index][i] == ref_num) {
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
	
	public ArrayList<Integer> getMatchingRowNumbers(String ref_num, int index) {
	/**********************************************************************************************************************
	* Returns any matching rows based on int index (column) and String search_for (what will be searched for)
	*/
		
		ArrayList<Integer> matching = new ArrayList<Integer>();
		
		for(int i = 0; i < data.length; i++) {
			if(data[i][index] == ref_num) {
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
	
	public ArrayList<String> getColumnData(int index) {
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
	
	public ArrayList<String> getRowData(int index) {
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
	
	public boolean sheetIsMatch(int sheet_index, String ref_num) {
	/**********************************************************************************************************************
	* Returns true if reference number was found in passed String[][]
	*/
		
		if(sheet_index != 0 && sheet_index != 1) {
			System.out.println("Invalid sheet index passed into method sheetIsMatch");
			return false;	
		}
		
		int search_col[] = new int[2];
		
		if(sheet_index == 0) { //sheet index 0 = purchase orders tab
			search_col[0] = 11; //purchase orders column
			search_col[1] = 34; //container numbers column
		}else if(sheet_index == 1) { //sheet index 1 = bulk tab
			search_col[0] = 4; //purchase orders column
			search_col[1] = 9; //container numbers column
		}
		
		//loop through rows of string data[][] searching for reference number
		for(int i = 0; i < data.length; i++) {
			if(data[i][search_col[0]] != ref_num && data[i][search_col[1]] != ref_num) {
				continue; //no match
			}else{
				return true; //sheet found
			}
		}
		return false; //no match found
	}
	
}
