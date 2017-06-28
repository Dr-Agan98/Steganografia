import java.awt.image.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.BitSet;

public class SteganographyTools {
	
	private static int PIXEL_BITS=8;
	private static SecureRandom rand = new SecureRandom();

	/*public static BufferedImage convert(BufferedImage drawableImg,int nLsb){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		byte[] seed = rand.generateSeed(12);
		rand.setSeed(seed);

		File seedKey = new File("ProjectSteganography/keys/seed.key");
		try {
			
			seedKey.createNewFile();
			BufferedWriter buffW = new BufferedWriter(new FileWriter(seedKey));
			buffW.write(seed.toString());
			buffW.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int nJumps = rand.nextInt(5);
		
		for(int iBit=0,seqBit=1;iBit<bits.size();iBit++,seqBit++){
				
			if(seqBit>0&&seqBit<=nLsb){
				if(nJumps==0){
					bits.set(iBit,true);
					nJumps=rand.nextInt(5);
				}else{
					nJumps--;
				}
			}
						
			if(seqBit==PIXEL_BITS)seqBit=0;
			
		}
		
		pixels = bits.toByteArray();
		DataBufferByte dataBuff = new DataBufferByte(pixels,pixels.length);
		WritableRaster writ = Raster.createWritableRaster(drawableImg.getRaster().getSampleModel(),dataBuff, null);
		return new BufferedImage(drawableImg.getColorModel(),writ, false, null);
	}*/
	
	
	
	public static BufferedImage convert(BufferedImage drawableImg,int nLsb,BitSet hiddenData){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		byte[] seed = rand.generateSeed(12);
		rand.setSeed(seed);

		File seedKey = new File("ProjectSteganography/keys/seed.key");
		try {
			
			seedKey.createNewFile();
			BufferedWriter buffW = new BufferedWriter(new FileWriter(seedKey));
			buffW.write(seed.toString());
			buffW.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int nJumps = rand.nextInt(4);
		boolean endData=false;
		int dataSize = hiddenData.size();
		
		for(int iBit=0,seqBit=1,hDataBit=0;endData==false;iBit++,seqBit++){
			
			if(nLsb!=0){
			
				if(seqBit>0&&seqBit<=nLsb){
					if(nJumps==0){
						bits.set(iBit,hiddenData.get(hDataBit));
						hDataBit++;
						if(hDataBit>=dataSize) endData = true;
							else nJumps=rand.nextInt(4);
 					}else{
						nJumps--;
					}
				}
				if(seqBit==PIXEL_BITS)seqBit=0;
			}
			
		}
		
		pixels = bits.toByteArray();
		DataBufferByte dataBuff = new DataBufferByte(pixels,pixels.length);
		WritableRaster writ = Raster.createWritableRaster(drawableImg.getRaster().getSampleModel(),dataBuff, null);
		return new BufferedImage(drawableImg.getColorModel(),writ, false, null);
	}
	
	
	
	public static BitSet readFile(String path) throws IOException{
		BufferedReader br = null;
		String text="",line="";
		
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato!");
		}
		
		while((line=br.readLine())!=null){
			text+=line;
		}		
		
	//	System.out.println(text);
		return (BitSet.valueOf(text.getBytes()));
	}

	
}
