package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import xlsx_Extractor_Package.XLSX_Extractor;

public class Invoice_Entry_toImportSheet {

	File impFile;
	String[][] panel_data;
	int sdChp;
	
	public Invoice_Entry_toImportSheet(String[][] panel_data, int sdChp) { //int sdChp is for determining import type; Standard (0) or Chep (1)
		this.panel_data = panel_data;
		this.sdChp = sdChp;
		if(sdChp == 0) {
			/*this.impFile = new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/lib/Standard Invoice Charge Import Sheet.xlsx");*/
			this.impFile = new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Standard Invoice Charge Import Sheet.xlsx");
		}else if(sdChp == 1) {
			/*this.impFile = new File("C:/Users/jason.demato/Documents/ref/2EZ Invoicing/lib/Chep Invoice Charge Import Sheet.xlsx");*/
			this.impFile = new File("C:/Users/Jdemato/Documents/2EZ Invoicing Ref Docs/Chep Invoice Charge Import Sheet.xlsx");
		}else{
			System.out.println("Invoice was not specified as either Standard (0) nor Chep (1).");
			throw new IllegalArgumentException();
		}
	}
	//Imports data from invoice panel into Invoice file
	public void insertImportSheetData() {
		
		XLSX_Extractor extract_output = new XLSX_Extractor(impFile, 0);
		Object[][] impData = extract_output.getCellData();
		
		try {
			
			//Open FileInputStream for wb and the first (and only) sheet in wb
			FileInputStream fis = new FileInputStream(impFile);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			
			//create cell variable of type Cell for inserting new values to output sheet
			Cell cell = null;
			int row_count = 0;
			
		/**********************************************************************************************************************
		* Import Data to Invoice Charges Import Sheet
		*/	
			int lastRow = 0;
			if(sdChp == 1) {
				lastRow = impData.length + 1;
			}else{
				lastRow = impData.length;
			}
			
			
			//loop begins at last row with value + 1; ends at however many entries are specified prior (impData.length)
			for(int i = lastRow; i < panel_data.length + lastRow; i++) {
				
				for(int j = 0; j < panel_data[0].length; j++) {
					
					Row row;
					if(j != 0) {
						row = ws.getRow(i);
						cell = row.createCell((int)j);
						cell.setCellType(Cell.CELL_TYPE_STRING);
					}else{
						//set cell = the next empty row (row_next), the matching output column
						if(ws.getRow(i) != null) {
							row = ws.getRow(i);
							cell = row.createCell((short)j);
							cell.setCellType(Cell.CELL_TYPE_STRING); //set current cell's type to string for inputting string data
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
					}
					
					if(sdChp != 1 || j != 6) {
						cell.setCellValue(panel_data[row_count][j]);
					}else if(panel_data[row_count][j] != "0" && panel_data[row_count][j] != "") { //Net Total ($) column in Chep Invoice Charge Import Sheet
						/* 
						 * parse % as string to % as double, divide by 100 to get percent as decimal,
						 * multiply percent as decimal by the dollar amount, parse double back to string for inputting
						 */
						double dec = (Double.parseDouble(panel_data[row_count][j - 1]) / 100);
						String amt = String.valueOf(dec * Double.parseDouble(panel_data[row_count][j]));
						cell.setCellValue(amt);
					}else{
						cell.setCellValue("0"); //should probably be throwing new exception here... cannot insert blank charge nor charge value of 0.
					}
						
						/*System.out.println(i + " == null");
						continue;*/
				}
				row_count++;
			}
			
			
			fis.close();
			FileOutputStream fos = new FileOutputStream(impFile);
			wb.write(fos);
			fos.close();
			wb.close();
		/*
		 * Import Data to Invoice Charges Import Sheet.xlsx
		 **********************************************************************************************************************/
			
		}catch(NullPointerException err) {
			/*System.out.println("Null Pointer Exception Error:  " + err);*/
			err.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException err) {
			System.out.println("Array Index Out Of Bounds Exception Error:  " + err);
			err.printStackTrace();
		}catch(FileNotFoundException err) { //Most likely caused by file being open on desktop at runtime
			System.out.println("File Not Found Exception Error:  " + err);
			err.printStackTrace();
		}catch(IOException err) {
			System.out.println("IO Exception Error:  " + err);
			err.printStackTrace();
		}
	}
	
	/*@SuppressWarnings("unused")
	//Method not necessary as sheet won't be seen by user. Will be usefule elsewhere
	private void formatAllChepCells(XSSFSheet ws, XSSFWorkbook wb) {
		//Changes all cells in CHEP Invoice section of sheet to their respective formats (i.e. date)
		for(Row row : ws) {
			for(Cell cell : row) {
				switch(cell.getColumnIndex()) {
					case 18:
						new Format_Cell(wb, cell).setBorder("LEFT", "BLACK"); //create black border on left side of cell
						break;
					case 20:
					case 28:
						new Format_Cell(wb, cell).date(); //format as date "m/d/yyyy"
						break;
					case 24:
						new Format_Cell(wb, cell).percentage(0); //format as percentage (zero decimal places)
						break;
					case 25:
					case 26:
					case 27:
						new Format_Cell(wb, cell).accounting(); //format as accounting format (more complex/detailed format for cells needing currency format)
						new Format_Cell(wb, cell).setBorder("RIGHT", "BLACK"); //create black border on right side of cell
						break;
				}
			}
		}
	}*/
	
}
