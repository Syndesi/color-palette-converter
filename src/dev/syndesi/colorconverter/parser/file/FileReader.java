package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.syndesi.colorconverter.Color;


public class FileReader {

	protected Map<String, FileParser> readers;
	
	public FileReader () {
		this.readers = new HashMap<>();
		this.readers.put("gpl", new FormatGimp());
		this.readers.put("soc", new FormatLibreOffice());
		this.readers.put("ase", new FormatAdobeASE());
	}
	
	public Color[] importFile (String path) throws Exception {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		if (!this.readers.containsKey(extension)) {
			throw new IOException("The extension " + extension + " is unknown.");
		}
		return this.readers.get(extension).importFile(path);
	}
	
}
