package steganography.encode;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

public class MainFramesEncode implements ActionListener,ChangeListener{

	private static BufferedImage img;
	private static JFrame frame;
	private static Tavola jTavola;
	private static JPanel options,top,bottom,center;
	private static JSlider slider;
	private static JFileChooser fileExplorer;
	private static JTextField pathExpl;
	private static JButton openFE,saveImg;
	private static JLabel avlbSpace;
	private static JTextArea msgArea;
	private static JScrollPane msgAreaScroll;
	
	public static void main(String[] args) throws IOException { new MainFramesEncode().create(); }
	
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
		
		
		//Slider(bottom)
		slider = new JSlider(SwingConstants.HORIZONTAL,0,8,0);
		slider.setMajorTickSpacing(4);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		avlbSpace = new JLabel("Spazio Disponibile: 0 Byte");
		saveImg = new JButton("Salva Immagine");
		saveImg.addActionListener(this);
		
		bottom = new JPanel(new BorderLayout(10,10));
		bottom.add(slider,BorderLayout.LINE_START);
		bottom.add(avlbSpace,BorderLayout.LINE_END);
		bottom.add(saveImg,BorderLayout.PAGE_END);
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
			avlbSpace.setText("Spazio Disponibile: "+((((jTavola.getNPixImg()*3*slValue))/4)/8)+" Byte");
			pathExpl.setText(fl.getPath());

			BitSet dataHide = BitSet.valueOf((msgArea.getText()).getBytes(StandardCharsets.UTF_8));
			
			jTavola.changeImage(fl,dataHide);
			jTavola.repaint();
		}
		if(obj==openFE){
			fileExplorer.showOpenDialog(null);
		}
		if(obj==saveImg){
			jTavola.saveImage();
		}

	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		Object obj = e.getSource();
		if(obj==slider){
			
			String txt = msgArea.getText();
			txt += "@@@";
			long dataLength = (txt.length())*8;
			BitSet dataHide = BitSet.valueOf((txt).getBytes());
			int slValue = slider.getValue();
			avlbSpace.setText("Spazio Disponibile: "+((((jTavola.getNPixImg()*3*slValue))/4)/8)+" Byte");
			
			if(slValue!=0){
				jTavola.setNLsb(slValue);
				jTavola.updateImage(dataHide,dataLength);
			}
		}
		
	}
	
}
