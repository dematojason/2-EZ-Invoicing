package invoice_Entry_Panels_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Invoice_Entry_toImportSheet {

	/*@Override
    public void actionPerformed(ActionEvent e) {
        char[] pass = enterPassword.getPassword();
        passString = new String(pass);
        char[] passConfirm = enterConfirmPassword.getPassword();
        String passStringConfirm = new String(passConfirm);
        userName = enterUsername.getText();
        if (e.getSource() == okButton) {*/

	String sheet_type;
	
	public Invoice_Entry_toImportSheet(String import_string) {
		this.sheet_type = import_string;
	}
	
	public void importDataStandard() {
		
	}
	
	//Imports data from CHEP style panel into CHEP Invoice section of Invoice Charge Import Sheet.xslx
	public void importDataChep(String account_name, String invoice_number, String invoice_date,
			String reference, JTextField[] descriptions, JTextField[] regions, JTextField[] percentages,
			String sub_total, String tax, String net_total) {
		
		try {
			//store passed data into 2 dimensional string array for output
			String[][] chep_invoiceEntryData_toImportSheet = new String[descriptions.length][10];
			for(int z = 0; z < descriptions.length; z++) {
				chep_invoiceEntryData_toImportSheet[z][0] = account_name;
				chep_invoiceEntryData_toImportSheet[z][1] = invoice_number;
				chep_invoiceEntryData_toImportSheet[z][2] = invoice_date;
				chep_invoiceEntryData_toImportSheet[z][3] = reference;
				//skip indexes [z][4] to [z][6] as they will be given value individually in the switch(x) statement later
				chep_invoiceEntryData_toImportSheet[z][7] = sub_total;
				chep_invoiceEntryData_toImportSheet[z][8] = tax;
				chep_invoiceEntryData_toImportSheet[z][9] = net_total;
			}
			FileInputStream fis = new FileInputStream(new File("C:\\Users\\Jdemato\\Documents\\Invoice Charge Import Sheet.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			
			checkSheetFormat(ws); //Make sure format of Invoices Import Sheet is correct
			
			int last_row_with_value = ws.getLastRowNum() + 1;
			
			//create cell variable of type Cell for inserting new values to output sheet
			Cell cell = null;
			int row_count = 0;
			int column = 0;
			
			if(last_row_with_value > 0) {
				//loop beginning at last row with value + 1; for however many entries are necessary
				//section of invoice Charge Import Sheet.xlsx
				for(int next_row = last_row_with_value; next_row < descriptions.length + last_row_with_value; next_row++) {
					Row row = ws.createRow((short)next_row);
					for(int x = 0; x < 10; x++) {
						column = x + 18;
						//set cell = the next empty row (row_next), the matching output column...
						//matching output columns for CHEP beginning @ column 18 ("S"), go until column 27 ("AB")
						//See Excel file "Invoice Charge Import Sheet.xlsx" for reference
						if(ws.getRow(next_row) != null) {
							cell = row.createCell((short)column);
							switch(x) {
								case 4: //Product Description
									cell.setCellType(Cell.CELL_TYPE_STRING); //set current cell's type to string for inputting string data
									cell.setCellValue(descriptions[row_count].getText().toString()); //input data as string into cell
									break;
								case 5: //Region
									cell.setCellType(Cell.CELL_TYPE_STRING);
									cell.setCellValue(regions[row_count].getText().toString());
									break;
								case 6: //Percentages for charge break-up
									cell.setCellType(Cell.CELL_TYPE_STRING);
									/*divide by 100 for formatting cell to percentage
									excel's percentage formatter multiplies the cell value by 100
									and displays the result with the percent symbol*/
									cell.setCellValue(percentages[row_count].getText().toString());
									break;
								case 7: //Sub Total ($)
								case 8: //Tax ($)
								case 9: //Net Total ($)
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if(chep_invoiceEntryData_toImportSheet[row_count][x] != "0") {
										double dec = (Double.parseDouble(percentages[row_count].getText().toString()) / 100);
										String dollar_amount = String.valueOf(dec * Double.parseDouble(chep_invoiceEntryData_toImportSheet[row_count][x]));
										cell.setCellValue(dollar_amount);
									}else{
										cell.setCellValue(chep_invoiceEntryData_toImportSheet[row_count][x]);
									}
									break;
								default:
									cell.setCellType(Cell.CELL_TYPE_STRING);
									cell.setCellValue(chep_invoiceEntryData_toImportSheet[row_count][x]);
									break;
							}
						}else{
							System.out.println(next_row + " == null");
							continue;
						}
					}
					row_count++;
				}
			}else{
				System.out.println("The format of this excel file is invalid.");
				System.exit(0);
			}
			
			//format all cells to their respective formats (currency, date, percentage, etc.)
			/*formatAllChepCells(ws, wb);*/
			
			fis.close();
			FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Jdemato\\Documents\\Invoice Charge Import Sheet.xlsx"));
			wb.write(fos);
			fos.close();
			wb.close();
			
		}catch(NullPointerException err) {
			System.out.println("Null Pointer Exception Error:   " + err);
			err.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException err) {
			System.out.println("Array Index Out Of Bounds Exception Error:  " + err);
			err.printStackTrace();
		}catch(FileNotFoundException err) {
			System.out.println("File Not Found Exception Error:  " + err);
			err.printStackTrace();
		}catch(IOException err) {
			System.out.println("IO Exception Error:  " + err);
			err.printStackTrace();
		}
		
	}
	
	private void checkSheetFormat(XSSFSheet ws) {
		//Check random page titles to make sure format is correct
		int err_num = 0;
		if(ws.getRow(1).getCell(2).toString().equalsIgnoreCase("Invoice Date") == false) { //Cell C2
			err_num = 1;
		}else if(ws.getRow(1).getCell(11).toString().equalsIgnoreCase("Product Reference #") == false) { //Cell L2
			err_num = 2;
		}else if(ws.getRow(1).getCell(14).toString().equalsIgnoreCase("Charge Type") == false) { //Cell O2
			err_num = 3;
		}else if(ws.getRow(1).getCell(19).toString().equalsIgnoreCase("Invoice Number") == false ) { //Cell T2
			err_num = 4;
		}else if(ws.getRow(1).getCell(23).toString().equalsIgnoreCase("Region") == false ) { //Cell X2
			err_num = 5;
		}else if(ws.getRow(1).getCell(27).toString().equalsIgnoreCase("Net Total ($)") == false ) { //Cell AB2
			err_num = 6;
		}
		
		if(err_num > 0) {
			switch(err_num) {
				case 1:
					System.out.println("The title name in cell 'C2' is incorrect!");
					break;
				case 2:
					System.out.println("The title name in cell 'L2' is incorrect!");
					break;
				case 3:
					System.out.println("The title name in cell 'O2' is incorrect!");
					break;
				case 4:
					System.out.println("The title name in cell 'T2' is incorrect!");
					break;
				case 5:
					System.out.println("The title name in cell 'X2' is incorrect!");
					break;
				case 6:
					System.out.println("The title name in cell 'AB2' is incorrect!");
					break;
				default:
					System.out.println("Unknown Error while checking excel file format!");
					break;
			}
			System.exit(0);
		}
	}
	
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
	}
	
}