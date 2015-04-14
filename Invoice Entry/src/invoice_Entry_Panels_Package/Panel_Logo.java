package invoice_Entry_Panels_Package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Logo{
	
	BufferedImage ibc_logo;
	JPanel panel;
	JLabel pic_label;
	
	public Panel_Logo(JPanel passed_panel) {
		
		this.panel = passed_panel;
		
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(1200,800));
		try {
			ibc_logo = ImageIO.read(new File("C:/Users/jason.demato/git/2-EZ-Invoicing/Invoice Entry/src/IBC_logo.jpg"));
			Image scaled_image = ibc_logo.getScaledInstance(1200, 800, Image.SCALE_SMOOTH);
			this.pic_label = new JLabel(new ImageIcon(scaled_image));
			
		}catch(IOException err) {
			err.printStackTrace();
		}
		
	}
	
	public void addImageLogo() {
		
		if(pic_label.getParent() == null) {
			panel.add(pic_label);
		}
		
	}
	
	
	public void removeImageLogo() {
		
		if(pic_label.getParent() != null) {
			panel.remove(pic_label);
		}
		
	}
	
}
