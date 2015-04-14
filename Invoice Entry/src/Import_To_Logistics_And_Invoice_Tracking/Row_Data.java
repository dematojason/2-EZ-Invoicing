package Import_To_Logistics_And_Invoice_Tracking;

import java.io.File;
import java.util.ArrayList;

public class Row_Data {

	ArrayList<String> row;
	
	public Row_Data(ArrayList<String> passed_row) {
		
		this.row = passed_row;
		
	}
	
	private void importRow(File import_to_file, int sheet_index, ArrayList<String> passed_row) {
	/**********************************************************************************************************************
	* 
	*/
		
		
		
	}
	
	public ArrayList<String> convertToOuputFormat() {
	/**********************************************************************************************************************
	* 
	*/
		
		
		return null;
		
	}
	
	/*public boolean matchFound(String[] data, int column_index) {
		
		ArrayList<Integer> matching_rows = new ArrayList<Integer>();
		
		if(data[i][index] == ref_num) {
				matching.add(i + 1);
		}
		//return null if there were no items added to ArrayList
		if(matching.size() > 0) {
			return matching;
		}else{
			return null;
		}
		
		return false;
	}*/
	
	public boolean searchRow(ArrayList<String> data_list, String ref) {
		
		for(String str : data_list) {
			if(str == ref) {
				return true;
			}
		}
		
		return false;
	}
	
}
