package dev.syndesi.colorconverter.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

	protected String command;
	protected Map<String, String> arguments;
	protected List<String> examples;
	protected String help;
	
	public Command () {
		this.command = "undefined_command";
		this.arguments = new HashMap<>();
		this.examples = new ArrayList<String>();
		this.help = null;
	}
	
	/**
	 * @return String The unique command name
	 */
	public String getCommand () {
		return this.command;
	}
	
	/**
	 * @return Map<String, String> Returns a map of all arguments and their explanation
	 */
	public Map<String, String> getArguments () {
		return this.arguments;
	}
	
	/**
	 * @return List<String> Returns a list of all examples
	 */
	public List<String> getExamples () {
		return this.examples;
	}
	
	/**
	 * @return String Returns an array of strings which should be no longer than 80-120 chars, can include examples etc.
	 */
	public String getHelp () {
		return this.help;
	}
	
	/**
	 * Starts the command
	 * @param args Parameters from the command line
	 * @throws Exception 
	 */
	public void run(String[] args) throws Exception {
		return;
	}
	
}
