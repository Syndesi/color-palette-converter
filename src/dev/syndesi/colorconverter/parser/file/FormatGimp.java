package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import dev.syndesi.colorconverter.Color;


public class FormatGimp extends FileParser {

	public Color[] importFile (String path) throws IOException {
		List<String> s = Files.readAllLines(Paths.get(path));
		List<Color> colors = new ArrayList<Color>();
		boolean isHeader = true;
		for (int i = 0; i < s.size(); i++) {
			String line = s.get(i);
			if (line.length() == 0) {
				// don't parse empty lines
				continue;
			}
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
	
	public Color parseLine (String line) {
		String[] parts = line.trim().split("\\s+", 4);
		Color c = new Color();
		c.setRed(Integer.parseInt(parts[0]));
		c.setGreen(Integer.parseInt(parts[1]));
		c.setBlue(Integer.parseInt(parts[2]));
		c.setTitle(parts[3]);
		return c;
	}
	
	public void exportFile (String pathString, Color[] palette) throws IOException {
		Path path = Paths.get(pathString);
		String filename = path.getFileName().toString();
		int columns = 10;
		String out = "GIMP Palette\nName: " + filename + "\nColumns: " + columns + "\n#"; // insert header informations
		for (int i = 0; i < palette.length; i++) {
			out += "\n" + this.createLine(palette[i]);
		}
		Files.write(path, out.getBytes());
	}
	
	public String createLine (Color c) {
		String out = "";
		out += StringUtils.leftPad(Integer.toString(c.getRed() & 0xff), 3, " ");
		out += " " + StringUtils.leftPad(Integer.toString(c.getGreen() & 0xff), 3, " ");
		out += " " + StringUtils.leftPad(Integer.toString(c.getBlue() & 0xff), 3, " ");
		out += "\t\t" + c.getTitle();
		return out;
	}
	
}
