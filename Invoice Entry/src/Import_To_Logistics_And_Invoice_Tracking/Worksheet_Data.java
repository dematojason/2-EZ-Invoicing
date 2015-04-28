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
	
	public String[][] getSheetDataIfMatchFound(String ref, int search_col[]) {
	/**********************************************************************************************************************
	* Returns String[][] of worksheet containing reference number
	*/
			
			for(int i = 0; i < data.length; i++) {
				for(int j = 0; j < search_col.length; j++) {
					if(ref == data[i][j]) {
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
	
	public ArrayList<Integer> getMatchingRowNumbers(String ref_num, int index[]) {
	/**********************************************************************************************************************
	* Returns row indexes where match is found based on int index (column) and String search_for (what will be searched for)
	*/
		
		ArrayList<Integer> matching = new ArrayList<Integer>();
		
		for(int j = 0; j < data.length; j++) { //loop through rows of data being searched
			for(int i = 0; i < index.length; i++) { //loop through specified columns data current row
				if(data[j][index[i]] == ref_num) { //if match is found
					matching.add(j + 1); //add row index number to ArrayList being returned 
				}
			}
		}
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
	
	public boolean sheetIsEmpty() {
	/**********************************************************************************************************************
	* Returns true if certain import sheet (based on int invoice_type) is empty
	*/
		
		if(data.length <= 0) {
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean matchFound(int column_index, String ref_num) {
	/**********************************************************************************************************************
	* Returns true if reference number was found
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
	
	public String getImportRowRefNum(ArrayList<String> row_data) {
	/**********************************************************************************************************************
	* Returns reference number (Column "F") of specified row in sheet using passed ArrayList of row data
	*/	
		return row_data.get(5);
	}
	
	public String getImportRowRefNum(int row_index) {
	/**********************************************************************************************************************
	* Returns reference number (Column "F") of specified row in sheet using passed row_index
	*/		
		return data[row_index][5];
	}
	
	public int getNumberOfRows() {
	/**********************************************************************************************************************
	* Returns length of first index of data[][]
	*/
		return data.length;
	}
	
	public int getNumberOfColumns() {
	/**********************************************************************************************************************
	* Returns length of second index of data[][]
	*/
		return data[0].length;
	}
	
	public int getNumberOfColumns(int index) {
	/**********************************************************************************************************************
	* Returns length of second index of data[][] for the given row (int index passed to function)
	*/	
		return data[index].length;
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
	
	public String[][] getAllData() {
	/**********************************************************************************************************************
	* Returns all data from passed file's sheet as String[][]
	*/
		
		if(ws_pos != 0 && ws_pos != 1 && ws_pos != 2) {
			System.out.println("Invalid integer invoice type passed into Worksheet_Data().");
			return null;
		}
		XLSX_Extractor extract = new XLSX_Extractor(file, ws_pos);
		return XLSX_Extractor.convertObjToStringArray(extract.getCellData());
		
	}
	
	public File getFile() {
		return file;
	}
	
}
