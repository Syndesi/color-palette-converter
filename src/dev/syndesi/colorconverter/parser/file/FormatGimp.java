package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import dev.syndesi.colorconverter.Color;

/**
 * Class for converting between the internally used color-array and the .gpl-fileformat used by Gimp and many other applications.
 * @author Syndesi
 * @since 1.0
 */
public class FormatGimp extends FileParser {

	/**
	 * Imports a single .gpl file and tries to extract its colors.
	 * @param path The path to the file
	 * @return Returns a list of colors
	 * @throws IOException on IO errors
	 */
	public Color[] importFile (String path) throws IOException {
		List<String> s = Files.readAllLines(Paths.get(path));
		List<Color> colors = new ArrayList<Color>();
		for (int i = 0; i < s.size(); i++) {
			String line = s.get(i);
			// don't parse empty lines
			if (line.length() == 0) {
				continue;
			}
			// just parse lines which contains a color, checked via regex
			if (line.matches("^[\\s\\d]{3}\\s+[\\s\\d]{3}\\s+[\\s\\d]{3}\\s+.*$")) {
				try {
					Color c = this.parseLine(line);
					colors.add(c);
				} catch (Exception e) {
					// don't handle faulty colors at all
					e.printStackTrace();
					continue;
				}
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}

	/**
	 * Parses a single line of a .gpl file.
	 * @param line the line of the file
	 * @return Returns a single color
	 * @throws Exception on malformed lines
	 */
	protected Color parseLine (String line) throws Exception {
		String[] parts = line.trim().split("\\s+", 4);
		if (parts.length < 4) {
			throw new Exception("invalid formatted color line");
		}
		Color c = new Color();
		c.setRed(Integer.parseInt(parts[0]));
		c.setGreen(Integer.parseInt(parts[1]));
		c.setBlue(Integer.parseInt(parts[2]));
		c.setTitle(parts[3]);
		return c;
	}

	/**
	 * Exports an array of colors into a .gpl file.
	 * @param pathString the path to the new file, existing files will be overwritten
	 * @param palette the array of colors which should be included in the final file
	 * @throws IOException on IO errors
	 */
	public void exportFile (String pathString, Color[] palette) throws IOException {
		Path path = Paths.get(pathString);
		String filename = path.getFileName().toString();
		int columns = 10;
		// insert header informations
		String out = "GIMP Palette\nName: " + filename + "\nColumns: " + columns + "\n#";
		// insert colors
		for (int i = 0; i < palette.length; i++) {
			out += "\n" + this.createLine(palette[i]);
		}
		Files.write(path, out.getBytes());
	}

	/**
	 * Creates a single line of a .gpl file.
	 * @param color the color which should be formatted into a .gpl-line
	 * @return Returns a color-string of a .gpl file
	 */
	protected String createLine (Color color) {
		String out = "";
		out += StringUtils.leftPad(Integer.toString(color.getRed() & 0xff), 3, " ");
		out += " " + StringUtils.leftPad(Integer.toString(color.getGreen() & 0xff), 3, " ");
		out += " " + StringUtils.leftPad(Integer.toString(color.getBlue() & 0xff), 3, " ");
		out += "\t\t" + color.getTitle();
		return out;
	}
	
}
