import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;
import java.util.BitSet;


public class Tavola extends Canvas{

	private BufferedImage img,copy = null;
	
	public Tavola(BufferedImage img){
		this.img = img;
		copy = Converter.convert(img);
	}
	
	public BufferedImage getCopy(){
		return copy;
	}
	
	public void paint(Graphics g){
		g.drawImage(img,0,0,null);
	}
	
}
