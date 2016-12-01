import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

public class MainFrames implements WindowListener,ActionListener,ChangeListener{

	private BufferedImage img = null;
	private JFrame frameBefore,frameAfter;
	private Tavola tavolaBefore,tavolaAfter;
	private JPanel jPanelBefore;
	private JPanel options;
	private JSlider slider;
	
	public MainFrames(){
		try {
			img = ImageIO.read(new File("E:/Users/Draga/workspace/ProjectSteganography/bin/lena.tiff"));
		} catch (IOException e) {e.printStackTrace();}
		
		frameBefore = new JFrame("Prima");
		frameAfter = new JFrame("Dopo");
		
		slider = new JSlider(SwingConstants.HORIZONTAL,0,8,0);
		slider.setMajorTickSpacing(4);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		
		tavolaBefore = new Tavola(img);
		tavolaAfter = new Tavola(tavolaBefore.getCopy());
		
		jPanelBefore = new JPanel(new BorderLayout());
		options = new JPanel(new BorderLayout());
		
		options.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));

		options.add(slider,BorderLayout.LINE_START);
		
		jPanelBefore.add(options,BorderLayout.PAGE_END);
		jPanelBefore.add(tavolaBefore,BorderLayout.CENTER);
		
		frameAfter.add(tavolaAfter);
		
		frameBefore.setContentPane(jPanelBefore);
		
		frameBefore.addWindowListener(this);
		frameAfter.addWindowListener(this);
		
		frameBefore.setLocation(150, 100);
		frameAfter.setLocation(700, 100);
		
		frameBefore.setSize(img.getWidth(),img.getHeight()+50);
		frameAfter.setSize(img.getWidth(),img.getHeight());
		
		frameBefore.setVisible(true);
		frameAfter.setVisible(true);
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
	
	@Override
	public void stateChanged(ChangeEvent e){
		JSlider sl = (JSlider)e.getSource();
		//sl.getValue()
	}
	
	
	public static void main(String[] args) {
		MainFrames mainFrames = new MainFrames();
	}
	
}
