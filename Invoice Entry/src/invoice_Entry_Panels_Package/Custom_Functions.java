package invoice_Entry_Panels_Package;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Custom_Functions {
	JFrame frame;
	
	static XSSFSheet ws;
	
	public Custom_Functions(JFrame import_frame) {
		this.frame = import_frame;
	}
	
	public void setWindowPosition_Center() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
	
	public static void setLastRowFinder(XSSFSheet import_ws) {
		ws = import_ws;
	}
	
	//getLastRow_Chep() will return the last row number containing value for Chep
	public static int getLastRow_Chep() {
		
		try {
		//Iterate through first 200 rows of sheet
			for(int i = 0; i < 200; i++) {
				//Iterate through columns 18-27 (CHEP Invoice section of Charge Import Sheet
				for(int x = 18; x < 28; x++) {
					//If current cell type is not blank (CELL_TYPE_BLANK = 3)
					Row row = ws.getRow(i);
					if(row == null) {
						break;
					}else{
						Cell cell = row.getCell(x);
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
							if(x == 27) {
								return i - 1;
							}else{
								continue;
							}
						}else{
							break;
						}
					}
				}
			}
		}catch(NullPointerException err) {
			err.printStackTrace();
		}
		System.out.println("There has been an error searching for the last row containing value in the invoices list sheet.");
		System.exit(0);
		return 0;
	}
	
	public static String getCellValue(Cell cell) {
	    if (cell == null) {
	        return null;
	    }
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	        return cell.getStringCellValue();
	    }else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        return cell.getNumericCellValue() + "";
	    }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	        return cell.getBooleanCellValue() + "";
	    }else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
	        return cell.getStringCellValue();
	    }else if(cell.getCellType() == Cell.CELL_TYPE_ERROR){
	        return cell.getErrorCellValue() + "";
	    }else{
	        return null;
	    }
	}
	
}