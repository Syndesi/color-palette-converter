package dev.syndesi.colorconverter.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dev.syndesi.colorconverter.runtime.command.ConvertColorCommand;
import dev.syndesi.colorconverter.runtime.command.ConvertFileCommand;
import dev.syndesi.colorconverter.runtime.command.HelpCommand;
import dev.syndesi.colorconverter.runtime.command.ListCommand;

public class CommandChooser {

	private List<Command> commands;
	private String command;
	private String[] arguments;
	
	public CommandChooser () {
		this.commands = new ArrayList<Command>();
		// add help command which requires access to all other commands
		// the help command should be by default the first command in the list
		HelpCommand hc = new HelpCommand();
		hc.setCommands(this.commands);
		this.commands.add(hc);
		// add normal commands
		this.commands.add(new ListCommand());
		this.commands.add(new ConvertFileCommand());
		this.commands.add(new ConvertColorCommand());
	}
	
	@SuppressWarnings("resource")
	public void run (String[] args) throws Exception {
		if (args.length == 0) {
			// getting the required arguments interactively
			Scanner scan = new Scanner(System.in);
			System.out.println("No arguments specified.");
			System.out.print("Please enter command manually: ");
		    this.arguments = scan.nextLine().split("\\s+");
	    	// if nothing was entered then assume that the help-command was wanted
		    if (this.arguments.length == 1 && this.arguments[0].isEmpty()) {
		    	this.arguments = new String[] {"help"};
		    }
		    // split input-string into command & arguments
		    this.command = this.arguments[0];
		    this.arguments = Arrays.copyOfRange(this.arguments, 1, this.arguments.length);
		} else {
			// arguments specified in args
		    this.command = args[0];
		    this.arguments = Arrays.copyOfRange(args, 1, args.length);
		}
		// find required command or use the help-command
		Command commandToBeRun = this.commands.get(0);
		for (int i = 0; i < this.commands.size(); i++) {
			Command c = this.commands.get(i);
			if (c.getCommand().equals(this.command)) {
				commandToBeRun = c;
				break;
			}
		}
		// run the command
		commandToBeRun.run(this.arguments);
	}
	
}
