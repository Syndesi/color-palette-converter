package dev.syndesi.colorconverter.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import dev.syndesi.colorconverter.runtime.command.CommandConvert;
import dev.syndesi.colorconverter.runtime.command.CommandHelp;
import dev.syndesi.colorconverter.runtime.command.CommandList;


/**
 * Class for interpreting the input (arguments) and deciding which command to run.
 * @author Syndesi
 * @since 1.0
 */
public class CommandChooser {

	private List<CommandGeneric> commands;
	private String command;
	private String[] arguments;
	
	/**
	 * Initializes its variables
	 */
	public CommandChooser () {
		this.commands = new ArrayList<CommandGeneric>();
		// add help command which requires access to all other commands
		// the help command should be by default the first command in the list
		CommandHelp ch = new CommandHelp();
		ch.setCommands(this.commands);
		this.commands.add(ch);
		// add normal commands
		this.commands.add(new CommandList());
		this.commands.add(new CommandConvert());
	}
	
	/**
	 * Interprets the input and executes the command. If invalid or no input is given, the help-command is run by default.
	 * @param args array of arguments, can be empty
	 * @throws Exception on errors
	 */
	@SuppressWarnings("resource")
	public void run (String[] args) throws Exception {
		if (args.length == 0) {
			// getting the required arguments interactively
			Scanner scan = new Scanner(System.in);
			System.out.println("No arguments specified.");
			System.out.print("Please enter command manually: ");
			// argument extractor based on https://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
			List<String> argumentList = new ArrayList<String>();
			Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(scan.nextLine());
			while (m.find()) {
				argumentList.add(m.group(1).replace("\"", ""));
			}
			this.arguments = argumentList.toArray(new String[argumentList.size()]);
	    	// if nothing was entered then assume that the help-command was wanted
		    if (this.arguments.length == 0) {
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
		CommandGeneric commandToBeRun = this.commands.get(0);
		for (int i = 0; i < this.commands.size(); i++) {
			CommandGeneric command = this.commands.get(i);
			if (command.getCommand().equals(this.command)) {
				commandToBeRun = command;
				break;
			}
		}
		// print header
		System.out.println(StringUtils.rightPad("", 80, "-"));
		System.out.println(" " + commandToBeRun.getCommand());
		System.out.println(StringUtils.rightPad("", 80, "-"));
		// run the command
		try {		
			commandToBeRun.run(this.arguments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// print end message
		System.out.println(StringUtils.rightPad("", 80, "-"));
	}
	
}
