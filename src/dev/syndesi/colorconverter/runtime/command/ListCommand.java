package dev.syndesi.colorconverter.runtime.command;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.apache.commons.lang3.StringUtils;
import Catalano.Imaging.Tools.ColorConverter;

import dev.syndesi.colorconverter.Color;
import dev.syndesi.colorconverter.parser.file.FileReader;
import dev.syndesi.colorconverter.runtime.Command;


/**
 * Command for printing all colors of a file.
 * @author Syndesi
 * @since 1.0
 */
public class ListCommand extends Command {

	/**
	 * Initializes its own documentation
	 */
	public ListCommand() {
		super();
		this.command = "list";
		this.help = "Displays all colors of a file";
		this.arguments.put("filepath", "The path to the file which should be read");
		this.examples.add("cc list \"path/to/palette.ext\"");
	}

	/**
	 * Executes this command
	 * @param args an array with a single element, the path to the file
	 * @throws Exception on errors (io, parameters etc.)
	 */
	public void run(String[] args) throws Exception {
		// check if at least the first parameter (path) is given
		if (args.length < 1) {
			throw new Exception("Not enough parameters");
		}
		// initialize the fileReader and import the colors of the file
		String path = args[0];
		FileReader fr = new FileReader();
		Color[] colors = fr.importFile(path);
		// print all colors
		for (int i = 0; i < colors.length; i++) {
			System.out.println(this.printColorLine(colors[i]));
		}
	}
	
	/**
	 * Converts a color into a string with different color encodings (rgb, rgb-hex, cmyk).
	 * @param color the color which should be converted
	 * @return the resulting color string
	 */
	protected String printColorLine (Color color) {
		String out = "";
		// add title
		out += (StringUtils.rightPad(color.getTitle(), 20, " ")).substring(0, 20);
		// add rgb
		out += StringUtils.rightPad("rgb(" + (color.getRed() & 0xff) + ", " + (color.getGreen() & 0xff) + ", " + (color.getBlue() & 0xff) + ")", 19, "  ");
		// add rgb-hex
		out += "#" + (String.format("%02x", color.getRed() & 0xff) + String.format("%02x", color.getGreen() & 0xff) + String.format("%02x", color.getBlue() & 0xff)).toUpperCase() + "  ";
		// add cmyk (more lines required due to conversions etc.)
		double[] cmyk = ColorConverter.RGBtoCMYK(color.getRed() & 0xff, color.getGreen() & 0xff, color.getBlue() & 0xff);
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#0.000", symbol);
		out += "cmyk(" + df.format(cmyk[0]) + ", " + df.format(cmyk[1]) + ", " + df.format(cmyk[2]) + ", " + df.format(cmyk[3]) + ")";
		return out;
	}

}
