import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class MainFrames implements WindowListener,ActionListener{

	private BufferedImage img = null;
	private JFrame fBefore,fAfter;
	
	public MainFrames(){
		try {
			img = ImageIO.read(new File("E:/Users/Draga/workspace/ProjectSteganography/bin/lena.tiff"));
		} catch (IOException e) {e.printStackTrace();}
		
		fBefore = new JFrame("Prima");
		fAfter = new JFrame("Dopo");
		
		Container containerBef = fBefore.getContentPane();
		Container containerAft = fAfter.getContentPane();
		
		Tavola tavolaBefore = new Tavola(img);
		Tavola tavolaAfter = new Tavola(tavolaBefore.getCopy());
		
		containerBef.add(tavolaBefore);
		containerAft.add(tavolaAfter);
		fBefore.addWindowListener(this);
		fAfter.addWindowListener(this);
		
		fBefore.setLocation(100, 100);
		fAfter.setLocation(650, 100);
		
		fBefore.setSize(520,520);
		fAfter.setSize(520,520);

		fBefore.setVisible(true);
		fAfter.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) {
		MainFrames mainFrames = new MainFrames();
	}
	
}
