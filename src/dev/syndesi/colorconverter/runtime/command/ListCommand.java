package dev.syndesi.colorconverter.runtime.command;

import java.nio.file.Path;
import java.nio.file.Paths;

import dev.syndesi.colorconverter.Color;
import dev.syndesi.colorconverter.parser.file.FileParser;
import dev.syndesi.colorconverter.parser.file.FormatGimp;
import dev.syndesi.colorconverter.runtime.Command;


public class ListCommand extends Command {

	public ListCommand() {
		super();
		this.command = "list";
		this.help = "Displays all colors of a file";
		this.arguments.put("filepath", "The path to the file which should be read");
		this.examples.add("cc list \"path/to/palette.ext\"");
	}

	public void run(String[] args) {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);
		System.out.println("path to file: " + args[0]);
		FileParser fg = new FormatGimp();
		Color[] c = new Color[0];
		try {
			c = fg.importFile(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < c.length; i++) {
			System.out.println("rgb(" + (c[i].getRed() & 0xff) + ", " + (c[i].getGreen() & 0xff) + ", " + (c[i].getBlue() & 0xff) + "): " + c[i].getTitle());
		}
		try {
			fg.exportFile("./src/assets/Material-Design.copy.gpl", c);
			System.out.println("palette successfully exported");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
