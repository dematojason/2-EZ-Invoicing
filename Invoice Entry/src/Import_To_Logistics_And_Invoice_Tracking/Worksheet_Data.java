package Import_To_Logistics_And_Invoice_Tracking;

import java.io.File;
import java.util.ArrayList;

import xlsx_Extractor_Package.XLSX_Extractor;

public class Worksheet_Data {
	
	File file;
	int ws_pos;
	String[][] data;
	
	public Worksheet_Data(File passed_file, int sheet_pos) {
		
		this.file = passed_file;
		this.ws_pos = sheet_pos;
		this.data = getAllData();
		
	}
	
	private String[][] getMatchingWorkSheetData(int search_col, String ref) {
		/**********************************************************************************************************************
		* Returns String[][] of worksheet containing reference number
		*/
			
			for(int i = 0; i < dest_po_file.length; i++) { //loop through 2 destination files
				for(int j = 0; j < 2; j++) { //loop through first 2 sheets of each destination file
					String[][] data = getOutputWsData(j); //get import file data
					Worksheet_Data wb_search = new Worksheet_Data(data);
					if(wb_search.sheetIsMatch(j, ref) == true) {
						return data;
					}
				}
			}
			return null; //no sheet found
			
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
	
	public boolean sheetIsEmpty() {
	/**********************************************************************************************************************
	* Returns true if certain import sheet (based on int invoice_type) is empty
	*/
		
		if(getAllData().length <= 0) {
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean matchFound(int column_index, String ref_num) {
	/**********************************************************************************************************************
	* Returns true if reference number was found in passed String[][]
	*/
		
		//loop through rows of string data[][] searching for reference number
		for(int i = 0; i < data.length; i++) {
			if(data[i][column_index] != ref_num && data[i][column_index] != ref_num) {
				continue; //no match... YET
			}else{
				return true; //match found
			}
		}
		return false; //no match found
	}
	
	private String[][] getAllData() {
		
		if(ws_pos != 0 && ws_pos != 1) {
			System.out.println("Invalid integer invoice type passed into Worksheet_Data().");
			return null;
		}
		XLSX_Extractor extract = new XLSX_Extractor(file, ws_pos);
		return extract.convertObjToStringArray(extract.getCellData());
		
	}
	
}
