package steganography.decode;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class SteganographyTools {
	
	private static int PIXEL_BITS=8;
	private static SecureRandom rand = new SecureRandom();
	private static Random randD = new Random();

	public static BufferedImage convert(BufferedImage drawableImg,int nLsb,BitSet hiddenData){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		byte[] seed = rand.generateSeed(12);
		rand.setSeed("turd".getBytes());

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
			}else{
				endData = true;
			}
			
		}
		
		pixels = bits.toByteArray();
		DataBufferByte dataBuff = new DataBufferByte(pixels,pixels.length);
		WritableRaster writ = Raster.createWritableRaster(drawableImg.getRaster().getSampleModel(),dataBuff, null);
		return new BufferedImage(drawableImg.getColorModel(),writ, false, null);
	}
	
	
	public static String deconvert(BufferedImage drawableImg,int nLsb,long seed){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		randD.setSeed(seed);
		
		int nJumps = randD.nextInt(4);
		BitSet hiddenBits = new BitSet();
		
		for(int iBit=0,seqBit=1,hDataBit=0;bits.size()>=iBit;iBit++,seqBit++){
			if(nLsb!=0){
					if(seqBit>0&&seqBit<=nLsb){
						if(nJumps==0){
							hiddenBits.set(hDataBit,bits.get(iBit));
							hDataBit++;
							nJumps=randD.nextInt(4);
	 					}else{
							nJumps--;
						}
					}
					
					if(seqBit==PIXEL_BITS)seqBit=0;
			}
		}
		
		/*
		for(int i=0;i<48;i++){
			if(i==0){
				System.out.println("primo");
			}
			if(i==16){
				System.out.println("secondo");
			}
			if(i==32){
				System.out.println("terzo");
			}
			
			System.out.println(hiddenBits.get(i));
		}
		*/
		
		String text = new String(hiddenBits.toByteArray());
		return text;
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
		
		return (BitSet.valueOf(text.getBytes()));
	}

	
}
