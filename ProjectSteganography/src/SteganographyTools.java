import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

public class SteganographyTools {
	
	private static int PIXEL_BITS=8,MAX_RED=8,MAX_GREEN=16,MAX_BLUE=24,MAX_BITS=24;

	public static BufferedImage convert(BufferedImage drawableImg,int nLsb){
		byte[] pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		
		for(int iBit=0,seqBit=1;iBit<bits.size();iBit++,seqBit++){
			
			if(seqBit>0&&seqBit<=nLsb)bits.set(iBit,true);

			//End 8bits
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
		
		System.out.println(text);
		
		return (new BitSet());
	}
	
	public static void setPixels(int n){
		MAX_RED = n;
	}
	
}
