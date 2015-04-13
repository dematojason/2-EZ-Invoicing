package GUI;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.sun.prism.paint.Color;
import com.sun.xml.internal.ws.api.Component;

import xlsx_Extractor_Package.XLSX_Extractor;

	


public class Spreadsheet  {
	
	JTable table;
	XLSX_Extractor excel_extractor;
		
		public Spreadsheet (File file) {
			excel_extractor = new XLSX_Extractor(file, 0); //second value passed = the sheet index of the file being passed.. index 0 = first tab of worksheet
			table = new JTable(excel_extractor.getCellData(), excel_extractor.getColumnHeaders());
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			if (defaults.get("Table.alternateRowColor") == null)
			    defaults.put("Table.alternateRowColor", new Color(240, 240, 240, 0));
		}
		   
		
		/*
		public int getRowCount()	{
			return excel_extractor.last_column;
		}
		
		public int getColumnCount() {
			//return excel_extractor.last_row;
		}
		*/
		public JTable getTable() {
			return table;
		}
		
		/*public Object getValueAt(int row, int column)	{	
			if(row > excel_extractor.last_row || column > excel_extractor.last_column)
				return null;
			else
				return table.getValueAt(row, column);
		}*/
}
	

