package dev.syndesi.colorconverter.runtime.command;

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

	public void run(String[] args) {
		System.out.println("ConvertFileCommand is running :D");
	}

}
