package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.syndesi.colorconverter.Color;

/**
 * Helper class for providing an easy interface to all FileParser-classes.
 * @author Syndesi
 * @since 1.0
 */
public class FileReader {

	protected Map<String, FileParser> readers;
	
	/**
	 * Initializes this object.
	 */
	public FileReader () {
		this.readers = new HashMap<>();
		this.readers.put("gpl", new FormatGimp());
		this.readers.put("soc", new FormatLibreOffice());
		this.readers.put("ase", new FormatAdobeASE());
	}
	
	/**
	 * Imports a file just by its path. The right FileParser is automatically chosen.
	 * @param path the path to the file which should be read
	 * @return colors extracted from the file
	 * @throws Exception on errors (e.g. when the file extension isn't recognized)
	 */
	public Color[] importFile (String path) throws Exception {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		if (!this.readers.containsKey(extension)) {
			throw new IOException("The extension " + extension + " is unknown.");
		}
		return this.readers.get(extension).importFile(path);
	}
	
}
