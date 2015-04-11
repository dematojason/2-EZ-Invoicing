package xlsx_Extractor_Package;

import java.util.ArrayList;

public class INVOICE_SEARCH_FUNCTIONS {
	
	static String[][] data_array;
	static String invoice_number;
	static int search_index;
	
	static void set_data_array(String[][] data) {
		data_array = data;
	}
	
	static void set_string_to_be_searched(String invoice) {
		invoice_number = invoice;
	}
	
	static void set_search_array_index(String search_category) {
		
		String[] search_params_array = {"P.O. Files", "Tab", "P.O. #", "Container #", "Date Delivered/Port ETA", "Region",
				"Company of Invoice", "Invoice Number", "Invoice Date", "Charge Type", "Date Input"};
		
		if(RANDOM_FUNCTIONS.isValueInArray(search_params_array, search_category) == false) { //function to check if string is one of the possible search categories
			System.out.println("The search category entered is invalid");
			System.exit(0);
		}
		
		switch(String.valueOf(search_category)) {
			case "P.O. Files":
				search_index = 0;
				break;
			case "Tab":
				search_index = 1;
				break;
			case "P.O. #":
				search_index = 3;
				break;
			case "Container #":
				search_index = 4;
				break;
			case "Date Delivered/Port ETA":
				search_index = 5;
				break;
			case "Region":
				search_index = 6;
				break;
			case "Company of Invoice":
				search_index = 7;
				break;
			case "Invoice Number":
				search_index = 8;
				break;
			case "Invoice Date":
				search_index = 9;
				break;
			case "Charge Type":
				search_index = 10;
				break;
			case "Date Input":
				search_index = 14;
				break;
			default:
				System.out.println("The search parameter " + search_category + " is not an option");
				System.exit(0);
			
		}
	}
	
	static ArrayList<ArrayList<String>> get_matching_rows() {
		
		/*
		 * this function uses the index taken from the method set_search_array_index
		 * which is based on the user's chosen search parameters
		 */
		
		ArrayList<ArrayList<String>> return_list = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < data_array.length; i++) {
			//System.out.println(data_array[i][8] + " =? " + invoice_number);
			if(data_array[i][search_index].contains(invoice_number)) {
				return_list.add(row_to_arraylist(i));
			}
		}
		return return_list;
	}
	
	static ArrayList<String> row_to_arraylist(int row_index) {
		
		ArrayList<String> row_list = new ArrayList<String>();
		
			for(int i = 0; i < data_array[0].length; i++) {
				row_list.add(data_array[row_index][i]);
			}
		return row_list;		
	}
	
}