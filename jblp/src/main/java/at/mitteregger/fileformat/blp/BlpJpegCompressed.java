package at.mitteregger.fileformat.blp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class BlpJpegCompressed {

	private BlpHeader header;
	private InputStream is;

	public BlpJpegCompressed(BlpHeader blpHeader, InputStream inputStream) {
		this.header = blpHeader;
		this.is = inputStream;
	}

	public BufferedImage getBufferedImage() {
		try {
			
			int headerSize = 0x00000270; // TODO
			
			byte[] bytes = isToBytes(is);
			boolean lastFF = false;
			List<Integer> offsets = new ArrayList<Integer>();
			{ 
				int offset = 0;
				int i=0;
				for (byte b : bytes) {
					if (b == -1) {
						lastFF = true;
					} else {
						if (b == -40 && lastFF) {
							offset = i-1; 
							offsets.add(offset);
							System.out.println("found offset at "+ offset);
						}
						lastFF = false;
					}
					i++;
				}
				System.out.println("byte array size: " + bytes.length);
				System.out.println("offset = " + offset);
			}
			
			System.out.println("a = " + Arrays.toString(header.getMipMapOffset()));
			System.out.println("b = " + Arrays.toString(header.getMipMapSize()));
			
			List<BufferedImage> images = new ArrayList<BufferedImage>();
			for (int offsetIndex = 0; offsetIndex<offsets.size(); offsetIndex++) {
				try {
					int offset2 = offsets.get(offsetIndex);
					int len = bytes.length-offset2-1;
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes, offset2, len));
					if (img == null) {
						throw new IOException("Could not decode image");
					}
					images.add(img);
				} catch (IOException e) {
					System.out.println("failed offset index " + offsetIndex);
					e.printStackTrace();
				}
			}
			BufferedImage biggestimage = images.get(0);
			for (BufferedImage im : images) {
				if (im.getWidth() > biggestimage.getWidth()) {
					biggestimage = im;
				}
			}
			fixColors(biggestimage);
			return biggestimage;
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	private void fixColors(BufferedImage img) {
		for (int x=0; x<img.getWidth(); x++) {
			for (int y=0; y<img.getHeight(); y++) {
				int rgb = img.getRGB(x, y);
				Color color1 = new Color(rgb);
				Color color2 = new Color(color1.getBlue(), color1.getGreen(), color1.getRed());
				img.setRGB(x, y, color2.hashCode());
			}
		}
	}

	private byte[] isToBytes(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}

}
