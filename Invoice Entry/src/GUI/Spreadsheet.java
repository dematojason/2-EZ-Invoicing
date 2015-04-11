package GUI;

	
import invoice_Entry_Panels_Package.XLSX_String_Extractor;

import java.io.*;
import java.util.*;

import javax.swing.*;


public class Spreadsheet  {
	
	JTable table;
	XLSX_String_Extractor excel_extractor;
		
		public Spreadsheet (File file) {
			excel_extractor = new XLSX_String_Extractor(file);
			table = new JTable(excel_extractor.getDataStringArray(), excel_extractor.getColumnHeaders());
		}
		
		public int getRowCount()	{
			return excel_extractor.last_column;
		}
		
		public int getColumnCount() {
			return excel_extractor.last_row;
		}
		
		public JTable getTable() {
			return table;
		}
		
		public Object getValueAt(int row, int column)	{	
			if(row > excel_extractor.last_row || column > excel_extractor.last_column)
				return null;
			else
				return table.getValueAt(row, column);
		}
}
	

