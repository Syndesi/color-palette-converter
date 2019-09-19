package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.syndesi.colorconverter.Color;

/**
 * Helper class for providing an easy interface to all Format-classes.
 * @author Syndesi
 * @since 1.0
 */
public class Parser {

	protected Map<String, FormatGeneric> formats;
	
	/**
	 * Initializes this object.
	 */
	public Parser () {
		this.formats = new HashMap<>();
		this.formats.put("gpl", new FormatGimp());
		this.formats.put("soc", new FormatLibreOffice());
		this.formats.put("ase", new FormatAdobeASE());
	}
	
	/**
	 * Imports a file just by its path. The right FileParser is automatically chosen.
	 * @param path the path to the file which should be read
	 * @return colors extracted from the file
	 * @throws Exception on errors (e.g. when the file extension isn't recognized)
	 */
	public Color[] importFile (String path) throws Exception {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		if (!this.formats.containsKey(extension)) {
			throw new IOException("The extension " + extension + " is unknown.");
		}
		return this.formats.get(extension).importFile(path);
	}
	
	/**
	 * Exports an array of colors into a file. The right FileParser is automatically chosen.
	 * @param path the path to the file which should be read
	 * @param palette the colors which should be saved inside the file
	 * @throws Exception on errors (e.g. when the file extension isn't recognized)
	 */
	public void exportFile (String path, Color[] palette) throws Exception {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		if (!this.formats.containsKey(extension)) {
			throw new IOException("The extension " + extension + " is unknown.");
		}
		this.formats.get(extension).exportFile(path,  palette);
	}
	
}
