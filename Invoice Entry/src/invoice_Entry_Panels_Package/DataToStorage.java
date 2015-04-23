package invoice_Entry_Panels_Package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataToStorage {

	private static String filePath = "C:/Users/jason.demato/Documents/ref/2EZ Invoicing/lib/data.txt";
	
	public void exportData(String[][] data, int dataType) {
		
		try {
			
			File file = new File(filePath);
			
			if(dataType == 0) {
				FileReader fileRead = new FileReader(file);
				BufferedReader readFile = new BufferedReader(fileRead);
				List<String> exportList = getExportDataList(data);
				exportData();
			}else if(dataType == 1) {
				FileWriter fileWrite = new FileWriter(file, false); //'false' statement = overwrite existing file... 'true' would append
				BufferedWriter writeFile = new BufferedWriter(fileWrite);
				exportChepData(writeFile, data);
			}else{
				System.out.println("Invalid integer data type.");
				System.exit(0);
			}
			
		}catch(FileNotFoundException err) {
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
	}
	
	private List<String> getExportDataList(String[][] standardData, String[][] chepData) throws IOException {
		
		List<String> dataList = new ArrayList<String>();
		
		dataList.add("#standard");
		for(int i = 0; i < standardData.length; i++) {
			
			dataList.add("#newStandardCharge");
			String chg = "";
			boolean firstLine = true;
			for(String str : standardData[i]) {
				
				if(!firstLine) {
					chg += "," + str;
				}else{
					chg = str;
					firstLine = false;
				}
			}
			dataList.add(chg);
		}
		
		dataList.add("#chep");
		for(int i = 0; i < standardData.length; i++) {
			
			dataList.add("#newChepCharge");
			String chg = "";
			boolean firstLine = true;
			for(String str : chepData[i]) {
				
				if(!firstLine) {
					chg += "," + str;
				}else{
					chg = str;
					firstLine = false;
				}
			}
			dataList.add(chg);
		}
		
		/*for(String strLn; (strLn = reader.readLine()) != null; ) {
			
		}*/
		
		return dataList;
		
	}
	
	private void exportStandardData(BufferedWriter writeFile, String[][] data) throws IOException {
		
		writeFile.write("#standard\r\n");
		for(int i = 0; i < data.length; i++) { //for each charge
			writeFile.write("#newStandardCharge\r\n");
			for(String str : data[i]) { //for each string element in row 'i' of data
				writeFile.write(str + " , ");
			}
			writeFile.write("\r\n"); //next line
		}
		
	}
	
	private void exportChepData(BufferedWriter writer, String[][] data) throws IOException {
		
		writeFile.write("#chep\r\n");
		for(int i = 0; i < data.length; i++) { //for each charge
			writeFile.write("#newChepCharge\r\n");
			for(String str : data[i]) { //for each string element in row 'r' of data
				writeFile.write(str + " , ");
			}
			writeFile.write("\r\n"); //next line
		}
		
	}
	
	public static ImportData importData() {
		ArrayList<String> sData = new ArrayList<String>();
		ArrayList<String> cData = new ArrayList<String>();
		
		try {
		
			File file = new File(filePath);
			
		}catch(FileNotFoundException err) {
			err.printStackTrace();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
		return new ImportData(sData, cData);
	}
	
	private static void importChepData() {
		
		
		
	}
	
	private static void importStandardData() {
		
		
		
	}
	
}
