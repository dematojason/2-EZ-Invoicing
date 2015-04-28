package Import_To_Logistics_And_Invoice_Tracking;

import invoice_Entry_Panels_Package.Format_Cell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
		impChep();
	}
	
	private void impStandard() {
		
		if(isChargesEmpty()) { //check if there are any charges in Standard Invoice Charges Import Sheet.xlsx
			return;
		}else{ //insert charges into Invoice Tracking Sheet + Purchase Orders Sheet
			XLSX_Extractor extr = new XLSX_Extractor(impFile[0], 0);
			Object[][] impData = extr.getCellData();
			for(int i = 1; i < impData.length; i++) {
				List<String> curRowData = getCurImpRow(impData, i);
				try {
					if(curRowData.get(3) != "Delivery") {
						insCurRow(curRowData, 0);
					}else{
						insCurRow(curRowData, 2);
					}
					
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
			XLSX_Extractor extractChep = new XLSX_Extractor(impFile[1], 0);
			Object[][] impData = extractChep.getCellData();
			for(int i = 1; i < impData.length; i++) {
				List<String> curRowData = getCurImpRow(impData, i);
				try {
					
					insCurChepRow(curRowData);
					
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
	
	private void insCurChepRow(List<String> curRow) throws FileNotFoundException, IOException {
		
		//ask user if holiday or spring
		//will assume spring for testing purposes
		String[] opChepData = new String[8];
		
		for(int i = 0; i < 8; i++) {
			opChepData[i] = curRow.get(i);
		}
		
		FileInputStream fis = new FileInputStream(poFile[0]);
		XSSFWorkbook opWb = new XSSFWorkbook(fis);
		XSSFSheet opWs = opWb.getSheetAt(2);
		
		XLSX_Extractor ex = new XLSX_Extractor(poFile[0], 2);
		Object[][] opChepShtData = ex.getCellData();
		int nextRow = opChepShtData.length;
		
		Cell cell = null;
		
		//ONWARDS!!! TO THE PO SHEET!
		for(int i = 0; i < 6; i++) {
			Row row;
			if(i != 0) {
				row = opWs.getRow(nextRow);
				cell = row.createCell(i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}else if(opWs.getRow(nextRow) != null) {
				row = opWs.getRow(nextRow);
				cell = row.createCell((short)i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}else{
				opWs.createRow(nextRow);
				row = opWs.getRow(nextRow);
				cell = row.createCell((short)i);
				if(cell.getCellType() != Cell.CELL_TYPE_STRING) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
			}
			//give cells value + format
			switch(i) {
				case 2: //Invoice Date
					cell.setCellValue(opChepData[i]);
					Format_Cell fmtDate = new Format_Cell(opWb, cell);
					fmtDate.date();
					break;
				case 5: //Net total ($)
					cell.setCellValue(opChepData[6]);
					Format_Cell fmtDolla = new Format_Cell(opWb, cell);
					fmtDolla.accounting();
					break;
				default: //Account Name, Invoice Number, Product Decription, Region
					cell.setCellValue(opChepData[i]);
					break;
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(poFile[0]);
		opWb.write(fos);
		fos.close();
		opWb.close();
		
		
		
		//ONWARDS!! TO THE INVOICE TRACKING SHEET!
		FileInputStream fis2 = new FileInputStream(invFile);
		XSSFWorkbook opWb2 = new XSSFWorkbook(fis2);
		XSSFSheet opWs2 = opWb2.getSheetAt(1);
		
		XLSX_Extractor ex2 = new XLSX_Extractor(invFile, 1);
		Object[][] opChepShtData2 = ex2.getCellData();
		int nextRow2 = opChepShtData2.length + 1;
		
		Cell cell2 = null;
		
		//ONWARDS!!! TO THE PO SHEET!
		for(int i = 0; i < 8; i++) {
			Row row2;
			if(i != 0) {
				row2 = opWs2.getRow(nextRow2);
				cell2 = row2.createCell(i);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
			}else if(opWs2.getRow(nextRow2) != null) {
				row2 = opWs2.getRow(nextRow2);
				cell2 = row2.createCell((short)i);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
			}else{
				opWs2.createRow(nextRow2);
				row2 = opWs2.getRow(nextRow2);
				cell2 = row2.createCell((short)i);
				if(cell2.getCellType() != Cell.CELL_TYPE_STRING) {
					cell2.setCellType(Cell.CELL_TYPE_STRING);
				}
			}
			//give cells value + format
			
			switch(i) {
				case 2: //Invoice Date
				case 7: //Entry Date
					cell2.setCellValue(opChepData[i]);
					Format_Cell fmtDate2 = new Format_Cell(opWb2, cell2);
					fmtDate2.date();
					break;
				case 6: //Net total ($)
					cell2.setCellValue(opChepData[i]);
					Format_Cell fmtDolla2 = new Format_Cell(opWb2, cell2);
					fmtDolla2.accounting();
					break;
				default: //Account Name, Invoice Number, Product Decription, Region
					cell2.setCellValue(opChepData[i]);
					break;
			}
		}
		
		fis2.close();
		FileOutputStream fos2 = new FileOutputStream(invFile);
		opWb2.write(fos2);
		fos2.close();
		opWb2.close();
		
	}
	
	private void insCurRow(List<String> curRow, int rowType) throws FileNotFoundException, IOException, ParseException{
		
		//insert list of Strings of current row of invoice charges import sheet.xlsx
		int wsIndex = findWsMatch(curRow.get(5)); //find matching sheet
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
				String[] insPoCharges = new String[matchCount];
				
				//store data into insStrInvoiceTracking array for inserting data into Invoice Tracking Sheet
				for(int i = 0; i < matchCount; i++) {
					for(int j = 0; j < 17; j++) {
						switch(j) {
							case 0: //invoice number
							case 1: //invoice company
							case 2: //invoice date
								invTrackData[i][j] = curRow.get(j);
								break;
							case 3: //charge type
								invTrackData[i][j] = curRow.get(j);
								break;
							case 4: //existing charge amount
								invTrackData[i][j] = opData[matchingRows.get(i)][column].toString();
								break;
							case 5: //invoice charge amount
								invTrackData[i][j] = String.valueOf(Double.parseDouble(curRow.get(4)) / matchCount);
								break;
							case 6: //add existing charge + invoice charge amount
								try {
									invTrackData[i][j] = String.valueOf(Double.parseDouble(invTrackData[i][4]) + 
											Double.parseDouble(invTrackData[i][5]));
								}catch(NumberFormatException err) { //one value was blank string, so could not parse to double
									invTrackData[i][j] = invTrackData[i][5];
								}
								insPoCharges[i] = invTrackData[i][j];
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
								if(wsIndex == 1 || wsIndex == 2) {
									invTrackData[i][j] = opData[matchingRows.get(i)][0].toString();
									break;
								}else{
									invTrackData[i][j] = opData[matchingRows.get(i)][1].toString();
									break;
								}
							case 13: //region
								if(wsIndex == 1 || wsIndex == 2) {
									invTrackData[i][j] = opData[matchingRows.get(i)][4].toString();
									break;
								}else{
									invTrackData[i][j] = "";
									break;
								}
							case 14: //delivery date/port ETA
								if(wsIndex == 1 || wsIndex == 2) {
									invTrackData[i][j] = opData[matchingRows.get(i)][6].toString();
									break;
								}else{
									invTrackData[i][j] = opData[matchingRows.get(i)][5].toString();
									break;
								}
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
				
				FileInputStream fis2;
				XSSFWorkbook opWb;
				XSSFSheet opWs;
				File outFile = null;
				int sheetAt = 0;
				
				switch(wsIndex) {
					case 0: //Spring workbook, PO tab
						outFile = poFile[1];
						sheetAt = 0;
						break;
					case 1: //Holiday workbook, PO tab
						outFile = poFile[0];
						sheetAt = 0;
						break;
					case 2: //Spring workbook, bulk tab
						outFile = poFile[1];
						sheetAt = 1;
						break;
					case 3: //Holiday workbook, bulk tab
						outFile = poFile[0];
						sheetAt = 1;
						break;
					default: //IMPOSSIBLEEEEE
						System.out.println("Da fuckkkkkk.... Import_Final.java, part where you set output Purchase Order file.");
						break;
				}
				fis2 = new FileInputStream(outFile);
				opWb = new XSSFWorkbook(fis2);
				opWs = opWb.getSheetAt(sheetAt);
				
				Cell cell2 = null;
				for(int i = 0; i < matchingRows.size(); i++) {
					Row row2;
					int curRowIndex = matchingRows.get(i);
					if(opWs.getRow(curRowIndex) != null) {
						row2 = opWs.getRow(curRowIndex);
						cell2 = row2.getCell(column);
						cell2.setCellType(Cell.CELL_TYPE_STRING);
					}else {
						//this is not good.
						System.out.println("(Russian Accent) This very bad. Row should not be null here.");
						
						row2 = opWs.getRow(curRowIndex);
						cell2 = row2.createCell((short)curRowIndex);
						if(cell2.getCellType() != Cell.CELL_TYPE_STRING) {
							cell2.setCellType(Cell.CELL_TYPE_STRING);
						}
					}
					//add value to cell
					cell2.setCellValue(Double.parseDouble(insPoCharges[i]));
					//format cell as accounting format (type of excel formatting for currency)
					Format_Cell fmtPoChg = new Format_Cell(opWb, cell2);
					fmtPoChg.accounting();
				}
				fis2.close();
				FileOutputStream fos2 = new FileOutputStream(outFile);
				opWb.write(fos2);
				fos2.close();
				opWb.close();
				
				XLSX_Extractor extract = new XLSX_Extractor(invFile, 0);
				Object[][] invData = extract.getCellData();
				
				FileInputStream fis = new FileInputStream(invFile);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet ws = wb.getSheetAt(0);
				
				Cell cell = null;
				int rowCount = 0;
				int lastRow = invData.length;
				
				for(int i = lastRow; i < invTrackData.length + lastRow; i++) {
					for(int j = 0; j < invTrackData[0].length; j++) {
						Row row;
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
						if(j==4||j==5||j==6) { //format any dollar value cells to accounting format
							cell.setCellValue(invTrackData[rowCount][j]);
							Format_Cell fmtCellAcct = new Format_Cell(wb, cell);
							fmtCellAcct.accounting();
						}else if(j==2||j==7||j==8||j==14){ //format any date value cells to date format
							Format_Cell fmtCellDate = new Format_Cell(wb, cell);
							fmtCellDate.date();
							cell.setCellValue(invTrackData[rowCount][j]);
						}else{
							cell.setCellValue(invTrackData[rowCount][j]);
						}
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
	
	public static void removeRow(XSSFSheet sheet, int rowIndex) {
	    int lastRowNum=sheet.getLastRowNum();
	    if(rowIndex >= 0 && rowIndex < lastRowNum){
	        sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
	    }
	    if(rowIndex == lastRowNum){
	        XSSFRow removingRow = sheet.getRow(rowIndex);
	        if(removingRow != null){
	            sheet.removeRow(removingRow);
	        }
	    }
	}
	
	private List<String> getCurImpRow(Object[][] data, int index) {
		
		//return list of strings made up of current row in Object[][] data
		List<String> dataList = new ArrayList<String>();
		for(int i = 0; i < data[0].length; i++) {
			dataList.add(data[0][i].toString());
		}
		
		return dataList;
	}
	
	private static boolean isChargesEmpty() {
	
		
		return false; //no charges found in import Sheet
	}
	
}
