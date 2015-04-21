package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import xlsx_Extractor_Package.XLSX_Extractor;

public class Invoice_Entry_toImportSheet {

	/*@Override
    public void actionPerformed(ActionEvent e) {
        char[] pass = enterPassword.getPassword();
        passString = new String(pass);
        char[] passConfirm = enterConfirmPassword.getPassword();
        String passStringConfirm = new String(passConfirm);
        userName = enterUsername.getText();
        if (e.getSource() == okButton) {*/

	File chep_import_file = new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Chep Invoice Charge Import Sheet.xlsx");
	File standard_import_file = new File("C:/Users/jason.demato/Documents/Programming/Invoice Entry/Standard Invoice Charge Import Sheet.xlsx");
	String[][] panel_data;
	
	public Invoice_Entry_toImportSheet(String[][] panel_data) {
		this.panel_data = panel_data;
	}
	
	public void importDataStandard() {
		
	}
	
	//Imports data from CHEP style panel into CHEP Invoice section of Invoice Charge Import Sheet.xslx
	public void importDataChep() {
			
		XLSX_Extractor extract_output_bulk = new XLSX_Extractor(chep_import_file, 0);
		Object[][] import_file_data = extract_output_bulk.getCellData();
		
		try {
			
			//Open FileInputStream for wb
			FileInputStream fis = new FileInputStream(chep_import_file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			
			//create cell variable of type Cell for inserting new values to output sheet
			Cell cell = null;
			int row_count = 0;
			
		/**********************************************************************************************************************
		* Import Data to Invoice Charges Import Sheet.xlsx
		*/
			
			int last_row = import_file_data.length + 1; //add 1 to account for headers not being included
			
			if(last_row > 0) {
				//loop beginning at last row with value + 1; for however many entries are necessary
				//section of invoice Charge Import Sheet.xlsx
				for(int i = last_row; i < panel_data.length + last_row; i++) {
					Row row = ws.createRow((short)i);
					for(int j = 0; j < 7; j++) {
						//set cell = the next empty row (row_next), the matching output column
						if(ws.getRow(i) != null) {
							cell = row.createCell((short)j);
							cell.setCellType(Cell.CELL_TYPE_STRING); //set current cell's type to string for inputting string data
							switch(j) {
								case 6: //Net Total ($)
									if(panel_data[row_count][j] != "0" && panel_data[row_count][j] != "") {
										//parse % as string to % as double, divide by 100 to get percent as decimal, multiply percent as decimal
										//by the dollar amount, parse double back to string for inputting
										double dec = (Double.parseDouble(panel_data[row_count][j - 1]) / 100);
										String dollar_amount = String.valueOf(dec * Double.parseDouble(panel_data[row_count][j]));
										cell.setCellValue(dollar_amount);
									}else{
										cell.setCellValue(panel_data[row_count][j]);
									}
									break;
								default:
									cell.setCellValue(panel_data[row_count][j]);
									break;
							}
						}else{
							System.out.println(i + " == null");
							continue;
						}
					}
					row_count++;
				}
				
			}else{
				System.out.println("The format of this excel file is invalid.");
				System.exit(0);
			}
			
		/*
		* Import Data to Invoice Charges Import Sheet.xlsx
		**********************************************************************************************************************/
						
			fis.close();
			FileOutputStream fos = new FileOutputStream(chep_import_file);
			wb.write(fos);
			fos.close();
			wb.close();
			
		}catch(NullPointerException err) {
			System.out.println("Null Pointer Exception Error:   " + err);
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