import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;


public class Tavola extends JPanel{

	private BufferedImage img;
	private BufferedImage copy;
	private int nLsb;
	
	public Tavola(BufferedImage img){
		Image original = img.getScaledInstance(-1,500, Image.SCALE_SMOOTH);
		this.img = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		this.img.getGraphics().drawImage(img,0,0, null);
		this.nLsb = 0;
		this.copy = SteganographyTools.convert(img,nLsb);
	}
	
	public void updateImage(){
		this.copy = SteganographyTools.convert(img,nLsb);
		repaint();
	}
	
	public void changeImage(File fl){
		try {
			Image original = (ImageIO.read(fl)).getScaledInstance(-1, 500, Image.SCALE_SMOOTH);
			this.img = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			this.img.getGraphics().drawImage(original,0,0, null);
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}
		this.copy = SteganographyTools.convert(img,nLsb);
	}
	
	public void setNLsb(int n){
		this.nLsb = n;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(copy, (450-((copy.getWidth(null))/2)),0, null);
	}
	
}
