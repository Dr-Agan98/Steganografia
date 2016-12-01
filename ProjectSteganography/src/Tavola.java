import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;
import java.util.BitSet;

import javax.swing.event.ChangeListener;


public class Tavola extends Canvas{

	private BufferedImage img;
	private BufferedImage copy;
	
	public Tavola(BufferedImage img){
		this.img = img;
	}
	
	public BufferedImage getCopy(){
		copy = new Converter(img).convert(4,4,4);
		return copy;
	}
	
	public void paint(Graphics g){
		g.drawImage(img,0,0,null);
	}
	
}
