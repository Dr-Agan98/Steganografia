package steganography.decode;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Tavola extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private BufferedImage copy;
	private int nLsb;
	private int nPixImg;
	
	public Tavola(BufferedImage img){
		Image original = img;
		this.img = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		this.img.getGraphics().drawImage(img,0,0, null);
		this.copy = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		this.copy.getGraphics().drawImage(img, 0, 0, null);
		this.nLsb = 0;
	}
	
	public void updateImage(BitSet dataHide){
		this.copy = SteganographyTools.convert(img,nLsb,dataHide);
		repaint();
	}
	
	public String retrieveData(long seed){
		return SteganographyTools.deconvert(img, nLsb, seed);
	}
	
	public void changeImage(File fl,BitSet dataHide){
		try {
			Image original = ImageIO.read(fl);
			this.img = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			this.img.getGraphics().drawImage(original,0,0, null);
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}
		this.copy = SteganographyTools.convert(img,nLsb,dataHide);
	}

	public void changeImage(File fl){
		try {
			Image original = ImageIO.read(fl);
			this.img = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			this.img.getGraphics().drawImage(original,0,0, null);
			this.copy = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			this.copy.getGraphics().drawImage(original, 0, 0, null);
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		nPixImg = img.getHeight()*img.getWidth();
		g.drawImage(copy, ((1000-copy.getWidth(null))/2),((600-copy.getHeight(null))/2), null);
	}
	
	//Setter
	public void setNLsb(int n){this.nLsb = n;}
	
	//Getter
	public int getNPixImg(){return nPixImg;}
	
}
