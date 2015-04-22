package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background_Panel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	File img_file;
	BufferedImage image;

	public Background_Panel()
	{
		try {
			image = ImageIO.read(this.getClass().getResource("/res/img/logo_1280x720.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout());	
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(null), image.getHeight(null));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(image, 0, 0, d.width, d.height, null);
	}
	
	
}
