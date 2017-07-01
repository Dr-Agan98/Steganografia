package steganography.decode;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.CharBuffer;
import java.util.BitSet;

import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

public class MainFramesDecode implements ActionListener,ChangeListener{

	private static BufferedImage img;
	private static JFrame frame;
	private static Tavola jTavola;
	private static JPanel options,top,bottom,center;
	private static JSlider slider;
	private static JFileChooser fileExplorer,seedExplorer;
	private static JTextField pathExpl,pathSeed;
	private static JButton openFE,openSE;
	private static JTextArea msgArea;
	private static JScrollPane msgAreaScroll;
	private static long seed = 0;
	
	public static void main(String[] args) throws IOException { new MainFramesDecode().create(); }
	
	public void create() throws IOException{
			
		try {
			img = ImageIO.read(new File("ProjectSteganography/noimage.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		frame = new JFrame("Prima");
		frame.setLayout(new FlowLayout());
	
		//File Explorer Area(top)
		fileExplorer = new JFileChooser();
		fileExplorer.setCurrentDirectory(new File("ProjectSteganography"));
		fileExplorer.addActionListener(this);
		pathExpl = new JTextField(30);
		pathExpl.setEditable(false);
		openFE = new JButton("Apri Img");
		openFE.addActionListener(this);
		
		top = new JPanel();
		top.add(pathExpl);
		top.add(openFE);
		//
		
		//Message Insertion Area(center)
		msgArea = new JTextArea();
		msgArea.setText("");
		msgArea.setRows(7);
		msgArea.setColumns(40);
		msgArea.setLineWrap(true);
		msgAreaScroll = new JScrollPane(msgArea);
		
		center = new JPanel();
		center.add(msgAreaScroll,BorderLayout.CENTER);
		//
		
		
		//Seed explorer(bottom)
		seedExplorer = new JFileChooser();
		seedExplorer.setCurrentDirectory(new File("ProjectSteganography"));
		seedExplorer.addActionListener(this);
		
		pathSeed = new JTextField(30);
		pathSeed.setEditable(false);
		openSE = new JButton("Apri Seed");
		openSE.addActionListener(this);
		slider = new JSlider(SwingConstants.HORIZONTAL,0,8,0);
		slider.setMajorTickSpacing(4);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		
		bottom = new JPanel(new BorderLayout(10, 10));
		bottom.add(pathSeed,BorderLayout.LINE_START);
		bottom.add(openSE,BorderLayout.LINE_END);
		bottom.add(slider,BorderLayout.PAGE_END);
		//
			
		BorderLayout layout = new BorderLayout();
		layout.setHgap(10);
		layout.setVgap(30);
		options = new JPanel(layout);
		options.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		
		options.add(top,BorderLayout.PAGE_START);
		options.add(center,BorderLayout.CENTER);
		options.add(bottom,BorderLayout.PAGE_END);
	
		jTavola = new Tavola(img);
		jTavola.setBackground(Color.black);
		jTavola.setPreferredSize(new Dimension(1000, 600));
		
		frame.add(jTavola);
		frame.add(options);
		
		frame.setLocation(30, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,950);	
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==fileExplorer){
			File fl = fileExplorer.getSelectedFile();

			int slValue = slider.getValue();
			pathExpl.setText(fl.getPath());
			
			jTavola.changeImage(fl);
			jTavola.repaint();
		}
		if(obj == seedExplorer){
			File fl = seedExplorer.getSelectedFile();
			int slValue = slider.getValue();
			pathSeed.setText(fl.getPath());
			BufferedReader br = null;
			try {
				char[] cb = new char[20];
				br = new BufferedReader(new FileReader(fl));
				try {
					seed = Long.parseLong(br.readLine());	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
			msgArea.setText(jTavola.retrieveData(seed));

		}
		if(obj==openFE){
			fileExplorer.showOpenDialog(null);
		}
		if(obj == openSE){
			seedExplorer.showOpenDialog(null);
		}

	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		Object obj = e.getSource();
		if(obj==slider){
			
			int slValue = slider.getValue();

			if(slValue!=0){
				jTavola.setNLsb(slValue);
				String txt = jTavola.retrieveData(seed);
				txt = txt.split("@@@")[0];
				msgArea.setText(txt);
			}
		}
		
	}
	
}
