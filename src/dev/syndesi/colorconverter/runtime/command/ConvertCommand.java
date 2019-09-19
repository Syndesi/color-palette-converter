package dev.syndesi.colorconverter.runtime.command;

import dev.syndesi.colorconverter.Color;
import dev.syndesi.colorconverter.parser.file.Parser;
import dev.syndesi.colorconverter.runtime.CommandGeneric;


/**
 * Command for printing all colors of a file.
 * @author Syndesi
 * @since 1.0
 */
public class ConvertCommand extends CommandGeneric {

	/**
	 * Initializes its own documentation
	 */
	public ConvertCommand() {
		super();
		this.command = "convert";
		this.help = "Converts a file into another fileformat. The required parsers are determined by the extensions of the paths.";
		this.help += "\nSupported file formats are .gpl (Gimp), .soc (Libre Office) and .ase (Adobe).";
		this.arguments.put("inputPath", "The path to the input file");
		this.arguments.put("outputPath", "The path to the output file");
		this.examples.add("program convert \"input.ase\" \"output.soc\"");
	}


	/**
	 * Executes this command
	 * @param args an array with two elements, the input and output paths to color files
	 * @throws Exception on errors (io, parameters etc.)
	 */
	public void run(String[] args) throws Exception {
		if (args.length < 2) {
			throw new Exception("Not enough parameters");
		}
		Parser parser = new Parser();
		String inputPath = args[0];
		String outputPath = args[1];
		Color[] colors = parser.importFile(inputPath);
		parser.exportFile(outputPath, colors);
		System.out.println("Export finished, converted " + colors.length + " colors.");
	}

}
