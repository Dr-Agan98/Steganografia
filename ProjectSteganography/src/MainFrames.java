import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import sun.font.CreatedFontTracker;
import javax.imageio.ImageIO;

public class MainFrames implements ActionListener,ChangeListener{

	private static BufferedImage img;
	private static JFrame frame;
	private static Tavola jTavola;
	private static JPanel options;
	private static JSlider slider;
	private static JFileChooser fileExplorer;
	
	public static void main(String[] args) throws IOException {

			new MainFrames().create();
	
	}
	
	
	public void create() throws IOException{
			
		try {
			img = ImageIO.read(new File("ProjectSteganography/lena.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		frame = new JFrame("Prima");
		
		slider = new JSlider(SwingConstants.HORIZONTAL,0,8,0);
		slider.setMajorTickSpacing(4);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		
		fileExplorer = new JFileChooser();
		fileExplorer.setCurrentDirectory(new File("ProjectSteganography"));
		fileExplorer.addActionListener(this);
		
		options = new JPanel();
		options.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		options.add(slider,BorderLayout.LINE_START);
		options.add(fileExplorer,BorderLayout.LINE_END);
		
		jTavola = new Tavola(img);
		jTavola.setBackground(Color.black);
		
		frame.add(jTavola,BorderLayout.CENTER);
		frame.add(options,BorderLayout.PAGE_END);
		
		frame.setLocation(30, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,910);	
		frame.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.getClass()==JFileChooser.class){
			JFileChooser fc = (JFileChooser)obj;
			File fl = fc.getSelectedFile();

			jTavola.changeImage(fl);
			jTavola.repaint();
		}
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		Object obj = e.getSource();
		if(obj.getClass()==JSlider.class){
			JSlider sl = (JSlider)obj;
			
			jTavola.setNLsb(sl.getValue());
			jTavola.updateImage();
			
			System.out.println("slider"+sl.getValue());
		}
		
	}
	


	
}
