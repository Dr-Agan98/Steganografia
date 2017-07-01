package steganography.encode;
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
	private static Random rand = new Random();
	private static Random randSeed = new Random();
	
	public static BufferedImage convert(BufferedImage drawableImg,int nLsb,BitSet hiddenData,long dataLength){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		long seed = randSeed.nextLong();
		rand.setSeed(seed);

		File seedKey = new File("ProjectSteganography/keys/seed.key");
		try {
			
			seedKey.createNewFile();
			BufferedWriter buffW = new BufferedWriter(new FileWriter(seedKey));
			buffW.write(Long.toString(seed));
			buffW.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int nJumps = rand.nextInt(4);

		for(int iBit=0,seqBit=1,hDataBit=0;bits.size()>=iBit;iBit++,seqBit++){
			if(nLsb!=0){
					if(seqBit>0&&seqBit<=nLsb){
						if(nJumps==0){
							bits.set(iBit,hiddenData.get(hDataBit));
							hDataBit++;
							if(hDataBit>=dataLength){
								hDataBit=0;
							} else {
								nJumps=rand.nextInt(4);
							}
	 					}else{
							nJumps--;
						}
					}
			}
			if(seqBit==PIXEL_BITS)seqBit=0;
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
		
		return (BitSet.valueOf(text.getBytes()));
	}

	
}
