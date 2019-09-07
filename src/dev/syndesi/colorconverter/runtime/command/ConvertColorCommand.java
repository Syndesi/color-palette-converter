package dev.syndesi.colorconverter.runtime.command;

import dev.syndesi.colorconverter.runtime.Command;

public class ConvertColorCommand extends Command {

	public ConvertColorCommand() {
		super();
		this.command = "convertColor";
		this.help = "Converts a single color into the specified format.\n" +
			"Available formats are: hex, rgb, cmyk.";
		this.arguments.put("inputFormat", "The format of the specified color");
		this.arguments.put("color", "The color which should be converted");
		this.arguments.put("outputFormat", "The format of the converted color");
		this.examples.add("cc convertColor hex #123456 rgb");
		this.examples.add("cc convertColor hex #123456 cmyk");
	}

	public void run(String[] args) {
		System.out.println("ConvertColorCommand is running :D");
	}

}
