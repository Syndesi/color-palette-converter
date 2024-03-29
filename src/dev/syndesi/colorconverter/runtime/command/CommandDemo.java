package dev.syndesi.colorconverter.runtime.command;

import java.io.IOException;
import dev.syndesi.colorconverter.Color;
import dev.syndesi.colorconverter.parser.file.FormatAdobeASE;
import dev.syndesi.colorconverter.runtime.CommandGeneric;

/**
 * Internal class used for testing, not used inside production.
 * @deprecated
 * @author Syndesi
 *
 */
public class CommandDemo extends CommandGeneric {

	public CommandDemo() {
		super();
		this.command = "demo";
		this.help = "demo command, for testing stuff while developing";
	}

	public void run(String[] args) throws IOException {
		// CIE2_D65
		
		FormatAdobeASE ase = new FormatAdobeASE();
		Color[] c = ase.importFile("./src/assets/all color spaces.ase");
		for (int i = 0; i < c.length; i++) {
			System.out.println("rgb(" + (c[i].getRed() & 0xff) + ", " + (c[i].getGreen() & 0xff) + ", " + (c[i].getBlue() & 0xff) + "): " + c[i].getTitle());
		}
		
		ase.exportFile("./src/assets/all color spaces.export.ase", c);
		
		//int[] rgb = ColorConverter.LABtoRGB(97.2f, -5.0f, -5.0f, Illuminant.CIE2.D65);
		//System.out.println("rgb(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")");
		
	}

}
