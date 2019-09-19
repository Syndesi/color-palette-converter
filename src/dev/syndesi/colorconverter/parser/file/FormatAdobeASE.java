package dev.syndesi.colorconverter.parser.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import Catalano.Imaging.Tools.ColorConverter;
import Catalano.Imaging.Tools.Illuminant;

import dev.syndesi.colorconverter.Color;


/**
 * Class for converting between the internally used color-array and the .ase-fileformat used by Adobe.
 * @see <a href="https://www.cyotek.com/blog/reading-adobe-swatch-exchange-ase-files-using-csharp">Reading Adobe Swatch Exchange (ase) files using C#</a>
 * @see <a href="http://www.nomodes.com/aco.html">Adobe Photoshop Color File Format</a>
 * @author Syndesi
 * @since 1.0
 */
public class FormatAdobeASE extends FormatGeneric {

	/**
	 * Imports a single .ase file and tries to extract its colors.
	 * @param path The path to the file
	 * @return Returns a list of colors
	 */
	public Color[] importFile (String path) {
		List<Color> colors = new ArrayList<Color>();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(path, "r");
			String signature = "";
			for (int j = 0; j < 4; j++) {
				// character is stored inside one byte
				signature += (char) raf.readByte();
			}
			if (!signature.equals("ASEF")) {
				throw new IOException("The file does not have the required signature.");
			}
			@SuppressWarnings("unused")
			int versionMajor = raf.readShort();
			@SuppressWarnings("unused")
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
					// character is stored inside two bytes
					colorName += raf.readChar();
				}
				// null byte
				raf.skipBytes(2);
				String colorspace = "";
				for (int j = 0; j < 4; j++) {
					// character is stored inside one byte
					colorspace += (char) raf.readByte();
				}
				@SuppressWarnings("unused")
				int colortype = 0;
				Color c = new Color();
				switch (colorspace) {
					case "LAB ":
						float lab_l = raf.readFloat() * 100.0f;
						float lab_a = raf.readFloat();
						float lab_b = raf.readFloat();
						colortype = raf.readShort();
						// while the LAB-colors don't have a standard-whitepoint, Adobe usually uses D65
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
						// don't let the byte overflow
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
						// don't let the byte overflow
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
				// jump to the next color because after the current position can be another x bytes with metadata
				raf.seek(nextColorPosition);
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

	/**
	 * Exports an array of colors into a .ase file.
	 * @param path the path to the new file, existing files will be overwritten
	 * @param palette the array of colors which should be included in the final file
	 * @throws IOException on io errors
	 * @deprecated While this method can generate valid ase-files, its colors aren't identical (usually 50% darker)
	 */
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
