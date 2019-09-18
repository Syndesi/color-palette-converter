package dev.syndesi.colorconverter.parser.file;

import dev.syndesi.colorconverter.Color;


/**
 * Generic class for converting between internally used color-arrays and standardized file-formats.
 * @author Syndesi
 * @since 1.0
 */
public class FileParser {

	/**
	 * Parses a file and returns its colors.
	 * @param path the path to the file
	 * @return all found colors
	 * @throws Exception on errors
	 */
	public Color[] importFile (String path) throws Exception {
		return null;
	}

	/**
	 * Creates a file from an color-array.
	 * @param path the path to the new file, existing files will be overwritten
	 * @param palette the array of colors which should be included in the final file
	 * @throws Exception on errors
	 */
	public void exportFile (String path, Color[] palette) throws Exception {
		return;
	}
	
}
