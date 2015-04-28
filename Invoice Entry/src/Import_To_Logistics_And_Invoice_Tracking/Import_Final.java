package Import_To_Logistics_And_Invoice_Tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import xlsx_Extractor_Package.XLSX_Extractor;



public class Import_Final {

	//C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs
	
	/*private static File[] impFile = {new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/lib/Standard Invoice Charge Import Sheet.xlsx"),
			new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/lib/Chep Invoice Charge Import Sheet.xlsx")};*/
	private static File[] impFile = {new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Standard Invoice Charge Import Sheet.xlsx"), 
			new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Chep Invoice Charge IMport Sheet.xlsx")};
	
	/*private static File[] poFile = {new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/server/Spring 2015 Purchase Orders.xlsx"), 
			new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/server/Holiday 2015 Purchase Orders.xlsx")};*/
	private static File[] poFile = {new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Spring 2015 Purchase Orders.xlsx"), 
			new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Holiday 2015 Purchase Orders.xlsx")};
	
	/*private static File invFile = new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/server/Invoice Tracking.xlsx");*/
	private static File invFile = new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Invoice Tracking.xlsx");
	
	XLSX_Extractor standImport = new XLSX_Extractor(impFile[0], 0);
	XLSX_Extractor chepImport = new XLSX_Extractor(impFile[1], 0);
	
	XLSX_Extractor poWsData;
	XLSX_Extractor bulkWsData;
	Object[][] opData;
	List<Integer> rowMatchIndex;
	String fileName;
	String sheetName;
	String wtf;
	
	public Import_Final() {
		wtf = "wtf";
	}
	
	public void Import_Final_Data() {
		impStandard();
		/*impChep();*/
	}
	
	private void impStandard() {
		
		if(isChargesEmpty()) { //check if there are any charges in Standard Invoice Charges Import Sheet.xlsx
			System.out.println("Check point 1 FAILUREEEEEE");
			return;
		}else{ //insert charges into Invoice Tracking Sheet + Purchase Orders Sheet
			System.out.println("Check point 1 = GREAT SUCCESS!");
			XLSX_Extractor extr = new XLSX_Extractor(impFile[0], 0);
			Object[][] impData = extr.getCellData();
			for(int i = 0; i < impData.length; i++) {
				List<String> curRowData = getCurImpRow(impData, i);
				try {
					insCurRow(curRowData, 0);
				}catch(ParseException err) {
					err.printStackTrace();
				}catch(FileNotFoundException err) {
					err.printStackTrace();
				}catch(IOException err) {
					err.printStackTrace();
				}
				
			}
		}
		
	}
	
	private void impChep() {
		
		if(isChargesEmpty()) { //check if there are any charges in Chep Invoice Charges Import Sheet.xlsx
			return;
		}else{ //insert charges into Invoice Tracking Sheet + Purchase Orders Sheet
			Object[][] impData = chepImport.getCellData();
			for(int i = 0; i < impData.length; i++) {
				List<String> curRowData = getCurImpRow(impData, i);
				try {
					insCurRow(curRowData, 1);
				}catch(ParseException err) {
					err.printStackTrace();
				}catch(FileNotFoundException err) {
					err.printStackTrace();
				}catch(IOException err) {
					err.printStackTrace();
				}
				
			}
		}
		
	}
	
	private ArrayList<Integer> getMatchIndexes(int wsNum, String searchStr) {
		
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		
		switch(wsNum) {
			case 1:
			case 2:
				for(int i = 0; i < opData.length; i++) {
					if(!opData[i][11].toString().matches(searchStr) && !opData[i][34].toString().matches(searchStr)) { //no match row i
						continue;
					}else{ //match found
						returnList.add(i);
					}
				}
			case 3:
			case 4:
				for(int i = 0; i < opData.length; i++) {
					if(!opData[i][4].toString().matches(searchStr) && !opData[i][9].toString().matches(searchStr)) { //no match in row i
						continue;
					}else{ //match found
						returnList.add(i);
					}
				}
		}
		return returnList;
		
	}
	
	private int getMatchCount(int wsNum, String searchStr) {
		
		int counter = 0;
		
		switch(wsNum) {
			case 1:
			case 2:
				for(int i = 0; i < opData.length; i++) {
					if(!opData[i][11].toString().matches(searchStr) && !opData[i][34].toString().matches(searchStr)) { //no match in row i
						continue;
					}else{ //match found
						//this.rowMatchIndex.add(i);
						counter++;
					}
				}
			case 3:
			case 4:
				for(int i = 0; i < opData.length; i++) {
					if(!opData[i][4].toString().matches(searchStr) && !opData[i][9].toString().matches(searchStr)) { //no match in row i
						continue;
					}else{ //match found
						//this.rowMatchIndex.add(i);
						counter++;
					}
				}
		}
		
		return counter;
		
	}
	
	private int findWsMatch(String searchStr) {
		
		//search through different output sheets to find matching worksheet for inserting current charge
		for(int i = 0; i < 2; i++) { //loop through purchase order tabs of Spring/Holiday files
			poWsData = new XLSX_Extractor(poFile[i], 0);
			Object[][] wsData = poWsData.getCellData();
			for(int j = 0; j < wsData.length; j++) {
				if(!wsData[j][11].toString().matches(searchStr) && !wsData[j][34].toString().matches(searchStr)) {
					continue;
				}else if(i == 0) { //Spring purchase orders file, purchase order tab
					fileName = "Spring 2015";
					sheetName = "PO";
					opData = wsData;
					return 1;
				}else if(i == 1) { //Holiday purchase orders file, purchase order tab
					fileName = "Holiday 2015";
					sheetName = "PO";
					opData = wsData;
					return 2;
				}
			}
		}
		for(int i = 0; i < 2; i++) { //loop through bulk tabs of Spring/Holiday files
			bulkWsData = new XLSX_Extractor(poFile[i], 1);
			Object[][] wsData = bulkWsData.getCellData();
			for(int j = 0; j < wsData.length; j++) {
				if(!wsData[j][4].toString().matches(searchStr) && !wsData[j][9].toString().matches(searchStr)) {
					continue;
				}else if(i == 0) { //Spring purchase orders file, bulk tab
					fileName = "Spring 2015";
					sheetName = "Bulk";
					opData = wsData;
					return 3;
				}else if(i == 1) { //Holiday purchase orders file, bulk tab
					fileName = "Holiday 2015";
					sheetName = "Bulk";
					opData = wsData;
					return 4;
				}
			}
		}
		return 0;
		
	}
	
	private void insCurRow(List<String> curRow, int rowType) throws FileNotFoundException, IOException, ParseException{
		
		System.out.println("Check point 2 = HELL YEA LET IT RIDE!");
		//insert list of Strings of current row of invoice charges import sheet.xlsx
		int wsIndex = findWsMatch(curRow.get(5)); //find macthing sheet
		if(wsIndex != 0) {
			int matchCount = getMatchCount(wsIndex, curRow.get(5));
			ArrayList<Integer> matchingRows = getMatchIndexes(wsIndex, curRow.get(5));
			if(matchCount > 0) {
				int column = 0;
				if(wsIndex == 1 || wsIndex == 2) {
					column = getChargeColumn(curRow.get(3), 0);
				}else{
					column = getChargeColumn(curRow.get(3), 1);
				}
				String[][] invTrackData = new String[matchCount][17];
				
				//store data into insStrInvoiceTracking array for inserting data into Invoice Tracking Sheet
				for(int i = 0; i < matchCount; i++) {
					for(int j = 0; j < 17; j++) {
						switch(j) {
							case 0: //invoice number
							case 1: //invoice company
							case 2: //invoice date
							case 3: //charge type
								invTrackData[i][j] = curRow.get(j);
								break;
							case 4: //existing charge amount
								invTrackData[i][j] = opData[matchingRows.get(i)][column].toString();
								break;
							case 5: //invoice charge amount
								invTrackData[i][j] = curRow.get(4);
								break;
							case 6: //add existing charge + invoice charge amount
								try {
									invTrackData[i][j] = String.valueOf(Double.parseDouble(curRow.get(4)) + 
										Double.parseDouble(opData[matchingRows.get(i)][column].toString()));
								}catch(NumberFormatException err) {
									invTrackData[i][j] = curRow.get(4);
								}
								break;
							case 7: //delivery date (if delivery charge)
								invTrackData[i][j] = curRow.get(6);
								break;
							case 8: //entry date
								invTrackData[i][j] = curRow.get(7);
								break;
							case 9: //notes.. will be added manually into excel sheet by user
							case 10: //blank column in excel sheet for separating invoice information and product information
								invTrackData[i][j] = "";
								break;
							case 11: //product reference #
								invTrackData[i][j] = curRow.get(5);
								break;
							case 12: //vendor name
								invTrackData[i][j] = opData[matchingRows.get(i)][0].toString();
								break;
							case 13: //region
								invTrackData[i][j] = opData[matchingRows.get(i)][4].toString();
								break;
							case 14: //delivery date/port ETA
								invTrackData[i][j] = opData[matchingRows.get(i)][6].toString();
								break;
							case 15: //workbook name
								invTrackData[i][j] = fileName;
								break;
							case 16: //sheet name
								invTrackData[i][j] = sheetName;
								break;
							default: //IMPOSSIBLEEEEE
								invTrackData[i][j] = curRow.get(j);
								break;
							}
					}
				}
				XLSX_Extractor extract = new XLSX_Extractor(invFile, 0);
				Object[][] invData = extract.getCellData();
				
				FileInputStream fis = new FileInputStream(invFile);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet ws = wb.getSheetAt(0);
				
				Cell cell = null;
				int rowCount = 0;
				int lastRow = invData.length;
				
				for(int i = lastRow; i < invTrackData.length + lastRow; i++) {
					System.out.println("Check Point 3 - Wow. Nice.");
					for(int j = 0; j < invTrackData[0].length; j++) {
						Row row;
						System.out.println("Check Point 4 - Ok. I see you.");
						if(j != 0) {
							row = ws.getRow(i);
							cell = row.createCell(j);
							cell.setCellType(Cell.CELL_TYPE_STRING);
						}else if(ws.getRow(i) != null){
							row = ws.getRow(i);
							cell = row.createCell((short)j);
							cell.setCellType(Cell.CELL_TYPE_STRING);
						}else{
							if(ws.getRow(i) == null) {
								ws.createRow(i);
							}
							row = ws.getRow(i);
							cell = row.createCell((short)j);
							if(cell.getCellType() != Cell.CELL_TYPE_STRING) {
								cell.setCellType(Cell.CELL_TYPE_STRING);
							}
						}
						//add value to cell
						System.out.println("Check Point 5 - It's actually working?");
						cell.setCellValue(invTrackData[rowCount][j]);
					}
					rowCount++;
				}
				fis.close();
				FileOutputStream fos = new FileOutputStream(invFile);
				wb.write(fos);
				fos.close();
				wb.close();
				
			}else{
				System.out.println("Could not find a match for reference (p1): " + curRow.get(5) + ".");
				return;
			}
		}else{
			System.out.println("Could not find a match for reference (p2): " + curRow.get(5) + ".");
			return;
		}
		
	}
	
	private int getChargeColumn(String title, int tab) {
		
		if(tab != 0 && tab != 1) {
			System.out.println("Invalid tab index entered into method getChargeColumnNum.");
			System.exit(0);
			return 50;
		}else{
			System.out.println(title);
			switch(title) {
				case "Ocean Freight":
					if(tab == 0) { //purchase order tab
						return 14;
					}else{ //bulk tab
						return 10;
					}
				case "Documentation":
					if(tab == 0) { //purchase order tab
						return 15;
					}else{ //bulk tab
						return 11;
					}
				case "Clearance":
					if(tab == 0) { //purchase order tab
						return 16;
					}else{ //bulk tab
						return 12;
					}
				case "Duty":
					if(tab == 0) { //purchase order tab
						return 17;
					}else{ //bulk tab
						return 13;
					}
				case "GST":
					if(tab == 0) { //purchase order tab
						return 18;
					}else{ //bulk tab
						return 14;
					}
				case "Warehouse":
					if(tab == 0) { //purchase order tab
						return 19;
					}else{ //bulk tab
						return 15;
					}
				case "Drayage":
					if(tab == 0) { //purchase order tab
						return 20;
					}else{ //bulk tab
						return 16;
					}
				case "Delivery":
					if(tab == 0) { //purchase order tab
						return 21;
					}else{ //bulk tab
						return 17;
					}
				case "Miscellaneous":
					if(tab == 0) { //purchase order tab
						return 22;
					}else{ //bulk tab
						return 18;
					}
				case "Inland Europe":
					if(tab == 0) { //purchase order tab
						return 23;
					}else{ //bulk tab
						return 19;
					}
				default:
					System.out.println("No bueno... could not read charge type");
					System.exit(0);
					return 0;
			}
		}
		
	}
	
	private List<String> getCurImpRow(Object[][] data, int index) {
		
		//return list of strings made up of current row in Object[][] data
		List<String> dataList = new ArrayList<String>();
		for(int i = 0; i < data[index].length; i++) {
			System.out.println("index: " + index + ",  i: " + i);
			dataList.add(data[index][i].toString());
		}
		
		return dataList;
	}
	
	private static boolean isChargesEmpty() {
		
		
		return false; //no charges found in import Sheet
	}
	
}
