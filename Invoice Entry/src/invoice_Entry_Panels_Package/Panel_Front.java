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

public class Panel_Front extends JPanel{

	private static final long serialVersionUID = 1L;
	
	BufferedImage ibc_logo;
	
	public Panel_Front() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1200,800));
		
		try {
			ibc_logo = ImageIO.read(new File("C:/Users/jason.demato/git/2-EZ-Invoicing/Invoice Entry/src/IBC_logo.jpg"));
			Image scaled_image = ibc_logo.getScaledInstance(1200, 800, Image.SCALE_SMOOTH);
			JLabel pic_label = new JLabel(new ImageIcon(scaled_image));
			add(pic_label);
		}catch(IOException err) {
			err.printStackTrace();
		}
	}
	
}
