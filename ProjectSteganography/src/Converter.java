import java.awt.image.*;
import java.util.BitSet;

public class Converter {
	
	private final int MAX_RED=8,MAX_GREEN=16,MAX_BLUE=24,MAX_BITS=24;
	private BufferedImage drawableImg;
	private byte[] pixels;
	private BitSet bits;
	private WritableRaster writ;
	private DataBufferByte dataBuff;
	
	public Converter(BufferedImage drawableImg){
		this.drawableImg = drawableImg;
		pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		bits = BitSet.valueOf(pixels);
	}

	public BufferedImage convert(int redBits,int greenBits,int blueBits){
		pixels = (((DataBufferByte)drawableImg.getRaster().getDataBuffer()).getData());
		bits = BitSet.valueOf(pixels);
		greenBits=MAX_RED+greenBits;
		blueBits=MAX_GREEN+blueBits;
		
		for(int iBit=0,seqBit=1;iBit<bits.size()-2;iBit++,seqBit++){
			
			//Red
			if(seqBit>0&&seqBit<=redBits)bits.set(iBit,true);
			
			//Green
			if(seqBit>8&&seqBit<=greenBits)bits.set(iBit,true);
			
			//Blue
			if(seqBit>16&&seqBit<=blueBits)bits.set(iBit,true);
			
			//End 24 bits
			if(seqBit==24)seqBit=0;
			
		}
		
		pixels = bits.toByteArray();
		dataBuff = new DataBufferByte(pixels,pixels.length);
		writ = Raster.createWritableRaster(drawableImg.getRaster().getSampleModel(),dataBuff, null);
		return new BufferedImage(drawableImg.getColorModel(),writ, false, null);
	}
	
}
