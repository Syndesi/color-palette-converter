package dev.syndesi.colorconverter.parser.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Catalano.Imaging.Tools.ColorConverter;
import Catalano.Imaging.Tools.Illuminant;
import dev.syndesi.colorconverter.Color;


public class FormatAdobeASE extends FileParser {

	/**
	 * @see https://www.cyotek.com/blog/reading-adobe-swatch-exchange-ase-files-using-csharp
	 * @see http://www.nomodes.com/aco.html
	 */
	public Color[] importFile (String path) {
		List<Color> colors = new ArrayList<Color>();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(path, "r");
			String signature = "";
			for (int j = 0; j < 4; j++) {
				signature += (char) raf.readByte(); // character is stored inside one byte
			}
			if (!signature.equals("ASEF")) {
				throw new IOException("The file does not have the required signature.");
			}
			int versionMajor = raf.readShort();
			int versionMinor = raf.readShort();
			int colorsInFile = raf.readInt();
			for (int i = 0; i < colorsInFile; i++) {
				// iterate over all color objects in the binary file
				int type = raf.readShort();
				int colorLength = raf.readInt();
				long nextColorPosition = raf.getFilePointer() + colorLength;
				if (type != 1) {
					// go to the next color if the current block is not a color
					continue;
				}
				int nameLength = raf.readShort();
				String colorName = "";
				for (int j = 0; j < nameLength -1; j++) {
					colorName += raf.readChar(); // character is stored inside two bytes
				}
				raf.skipBytes(2); // null byte
				String colorspace = "";
				for (int j = 0; j < 4; j++) {
					colorspace += (char) raf.readByte(); // character is stored inside one byte
				}
				int colortype = 0;
				Color c = new Color();
				switch (colorspace) {
					case "LAB ":
						float lab_l = raf.readFloat() * 100.0f;
						float lab_a = raf.readFloat();
						float lab_b = raf.readFloat();
						colortype = raf.readShort();
						int[] rgb = ColorConverter.LABtoRGB(lab_l, lab_a, lab_b, Illuminant.CIE2.D65);
						c.setRed(rgb[0]);
						c.setGreen(rgb[1]);
						c.setBlue(rgb[2]);
						c.setTitle(colorName);
						colors.add(c);
						break;
					case "RGB ":
						int rgb_r = (int) (raf.readFloat() * 256.0f);
						int rgb_g = (int) (raf.readFloat() * 256.0f);
						int rgb_b = (int) (raf.readFloat() * 256.0f);
						if (rgb_r > 255) {
							rgb_r = 255;
						}
						if (rgb_g > 255) {
							rgb_g = 255;
						}
						if (rgb_b > 255) {
							rgb_b = 255;
						}
						colortype = raf.readShort();
						c.setRed(rgb_r);
						c.setGreen(rgb_g);
						c.setBlue(rgb_b);
						c.setTitle(colorName);
						colors.add(c);
						break;
					case "CMYK":
						float cmyk_c = raf.readFloat() * 100.0f;
						float cmyk_m = raf.readFloat() * 100.0f;
						float cmyk_y = raf.readFloat() * 100.0f;
						float cmyk_k = raf.readFloat() * 100.0f;
						int[] cmyk = ColorConverter.CMYKtoRGB(cmyk_c, cmyk_m, cmyk_y, cmyk_k);
						c.setRed(cmyk[0]);
						c.setGreen(cmyk[1]);
						c.setBlue(cmyk[2]);
						c.setTitle(colorName);
						colors.add(c);
						break;
					case "Gray":
						int gray = (int) (raf.readFloat() * 256.0f);
						if (gray > 255) {
							gray = 255;
						}
						colortype = raf.readShort();
						c.setRed(gray);
						c.setGreen(gray);
						c.setBlue(gray);
						c.setTitle(colorName);
						colors.add(c);
						break;
					default:
						break;
				}
				raf.seek(nextColorPosition); // jump to the next color
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}
	
	public void exportFile (String path, Color[] palette) throws IOException {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(path, "rw");
			raf.writeBytes("ASEF");            // signature
			raf.writeShort(1);                 // major version
			raf.writeShort(0);                 // minor version
			raf.writeInt(palette.length);      // number of colors
			for (int i = 0; i < palette.length; i++) {
				Color c = palette[i];
				raf.writeShort(1);             // type, 1 = color
				raf.writeInt(c.getTitle().length() * 2 + 22);   // remaining length of the color block, first part is the dynamic-length title and rest is static-length color
				raf.writeShort(c.getTitle().length() + 1);  // length of the title
				raf.writeChars(c.getTitle());
				raf.writeShort(0);             // NULL byte
				raf.writeBytes("RGB ");        // set color mode to RGB
				raf.writeFloat((float) c.getRed() / 256.0f);    // write red
				raf.writeFloat((float) c.getGreen() / 256.0f);  // write green
				raf.writeFloat((float) c.getBlue() / 256.0f);   // write blue
				raf.writeShort(0);             // color mode, 0 = "global", 1 = "spot", 2 = "normal"
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
