import java.awt.image.*;
import java.util.BitSet;

public class Converter {

	public static BufferedImage convert(BufferedImage img){
		byte[] pixels = (((DataBufferByte) img.getRaster().getDataBuffer()).getData());
		BitSet bits = BitSet.valueOf(pixels);
		
		for(int i=0,n=0;i<bits.size()-2;i++,n++){
			
			if(n>=0&&n<16)bits.set(i,true);
			if(n>=16&&n<23)bits.set(i,false);
			if(n==24)n=0;
			
		}
		
		byte[] after = bits.toByteArray();
		DataBufferByte dataBuff = new DataBufferByte(after, after.length);
		
		WritableRaster writ = Raster.createWritableRaster(img.getRaster().getSampleModel(),dataBuff, null);
		return new BufferedImage(img.getColorModel(),writ, false, null);
	}
	
}
