package dev.syndesi.colorconverter.runtime.command;

import dev.syndesi.colorconverter.Color;
import dev.syndesi.colorconverter.parser.file.FileParser;
import dev.syndesi.colorconverter.parser.file.FormatGimp;
import dev.syndesi.colorconverter.parser.file.FormatLibreOffice;
import dev.syndesi.colorconverter.runtime.Command;

public class ConvertFileCommand extends Command {

	public ConvertFileCommand() {
		super();
		this.command = "convertFile";
		this.help = "Converts a file into another fileformat.\n" +
			"Both input- and export-fileformats must be specified because some extensions " +
			"can store different formats, e.g. .xml can be used by MS Office, Libre Office etc.";
		this.arguments.put("inputFormat", "The fileformat of the input file");
		this.arguments.put("inputPath", "The path to the input file");
		this.arguments.put("outputFormat", "The fileformat of the output file");
		this.arguments.put("outputPath", "The path to the output file");
		this.examples.add("cc convertFile gimp \"path/to/gimpPalette.xml\" libreoffice \"path/to/output.xml\"");
	}

	public void run(String[] args) throws Exception {
		FileParser flo = new FormatLibreOffice();
		Color[] c = flo.importFile("./src/assets/standard.soc");
		@SuppressWarnings("unused")
		FileParser fg = new FormatGimp();
		for (int i = 0; i < c.length; i++) {
			System.out.println("rgb(" + (c[i].getRed() & 0xff) + ", " + (c[i].getGreen() & 0xff) + ", " + (c[i].getBlue() & 0xff) + "): " + c[i].getTitle());
		}
//		try {
//			fg.exportFile("./src/assets/standard.gpl", c);
//			System.out.println("palette successfully exported");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		flo.exportFile("./src/assets/standard.copy.soc", c);
	}

}
