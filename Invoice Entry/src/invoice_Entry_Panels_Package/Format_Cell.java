package invoice_Entry_Panels_Package;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Format_Cell {
	Cell cell;
	
	CellStyle style;
	DataFormat format;
	public Format_Cell(XSSFWorkbook wb, Cell import_cell) {
		this.cell = import_cell;
		this.format = wb.createDataFormat();
		this.style = wb.createCellStyle();
	}
	
	public void accounting() { //Accounting format --> more complex/detailed format for currency cells
	    style.setDataFormat(format.getFormat("_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);_(@_)"));
	    cell.setCellStyle(style);
	}
	
	public void date() { //Date format ordered month/day/year ... example outputs "9/23/2015", "11/4/2014", or "11/24/2015"
		style.setDataFormat(format.getFormat("m/d/yyyy"));
		cell.setCellStyle(style);
	}
	
	public void date_withDay() { //Day format with day of the week included ... example output "Wednesday, March 14, 2012"
		style.setDataFormat(format.getFormat("[$-F800]dddd, mmmm dd, yyyy"));
		cell.setCellStyle(style);
	}
	
	public void percentage(int dec_count) { //Changes decimal to percent ... example --> if int dec_count = 2: changes (double)"0.2371" to 23.71%
		switch(dec_count) { //number of integers caller wishes to have after decimal
			case 0:
				style.setDataFormat(format.getFormat("0%"));
			case 1:
				style.setDataFormat(format.getFormat("0.0%"));
			case 2:
				style.setDataFormat(format.getFormat("0.00%"));
			case 3:
				style.setDataFormat(format.getFormat("0.000%"));
			case 4:
				style.setDataFormat(format.getFormat("0.0000%"));
			default:
				style.setDataFormat(format.getFormat("0.00%")); //defaults to 2 decimal places
		}
		cell.setCellStyle(style);
	}
	
	public void timeAmPm() { //Format cell as time ... example --> "1:30 PM"
		style.setDataFormat(format.getFormat("[$-409]h:mm AM/PM;@"));
		cell.setCellStyle(style);
	}
	
	public void setBorder(String side, String color) {
		try {
			switch(side) {
				case "LEFT":
				case "Left":
				case "left":
					style.setBorderLeft(CellStyle.BORDER_THIN);
					switch(color) {
						case "BLACK":
						case "Black":
						case "black":
							style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
							break;
						case "BLUE":
						case "Blue":
						case "blue":
							style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
							break;
						case "GREEN":
						case "Green":
						case "green":
							style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
							break;
						default:
							style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
							break;
					}
				case "RIGHT":
				case "Right":
				case "right":
					style.setBorderRight(CellStyle.BORDER_THIN);
					switch(color) {
						case "BLACK":
						case "Black":
						case "black":
							style.setRightBorderColor(IndexedColors.BLACK.getIndex());
							break;
						case "BLUE":
						case "Blue":
						case "blue":
							style.setRightBorderColor(IndexedColors.BLUE.getIndex());
							break;
						case "GREEN":
						case "Green":
						case "green":
							style.setRightBorderColor(IndexedColors.GREEN.getIndex());
							break;
						default:
							style.setRightBorderColor(IndexedColors.BLACK.getIndex());
							break;
					}
				case "BOTTOM":
				case "Bottom":
				case "bottom":
					style.setBorderBottom(CellStyle.BORDER_THIN);
					switch(color) {
						case "BLACK":
						case "Black":
						case "black":
							style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
							break;
						case "BLUE":
						case "Blue":
						case "blue":
							style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
							break;
						case "GREEN":
						case "Green":
						case "green":
							style.setBottomBorderColor(IndexedColors.GREEN.getIndex());
							break;
						default:
							style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
							break;
					}
				case "TOP":
				case "Top":
				case "top":
					style.setBorderRight(CellStyle.BORDER_THIN);
					switch(color) {
						case "BLACK":
						case "Black":
						case "black":
							style.setTopBorderColor(IndexedColors.BLACK.getIndex());
							break;
						case "BLUE":
						case "Blue":
						case "blue":
							style.setTopBorderColor(IndexedColors.BLUE.getIndex());
							break;
						case "GREEN":
						case "Green":
						case "green":
							style.setTopBorderColor(IndexedColors.GREEN.getIndex());
							break;
						default:
							style.setTopBorderColor(IndexedColors.BLACK.getIndex());
							break;
					}
					
					break;
				default:
					System.out.println("First String arg entered must be 'left', 'right', 'top', or 'bottom'.");
					System.exit(0);
					break;
			}
		}catch(IllegalArgumentException err) {
			err.printStackTrace();
		}
	}

}