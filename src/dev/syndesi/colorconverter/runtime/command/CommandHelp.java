package dev.syndesi.colorconverter.runtime.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import dev.syndesi.colorconverter.runtime.CommandGeneric;

/**
 * Command for formating the other commands documentation and printing them.
 * @author Syndesi
 * @since 1.0
 */
public class CommandHelp extends CommandGeneric {
	
	protected List<CommandGeneric> commands;

	/**
	 * Initializes its own documentation
	 */
	public CommandHelp () {
		super();
		this.command = "help";
		this.help = "Lists all available commands, their arguments and help messages.\nNote: In the later examples the name of the program file will be replaced by just 'program'.";
		this.commands = null;
	}
	
	/**
	 * Sets the commands for which the documentation should be built.
	 * @param commands the commands which should be analyzed
	 */
	public void setCommands (List<CommandGeneric> commands) {
		this.commands = commands;
	}

	/**
	 * Executes this command
	 * @param args required by its parent, usually empty/ignored
	 */
	public void run (String[] args) throws Exception {
		if (this.commands == null) {
			throw new Exception("No commands found");
		}
		for (int i = 0; i < this.commands.size(); i++) {
			CommandGeneric c = this.commands.get(i);
			String helpColumn = c.getHelp();
			if (c.getArguments().size() > 0) {
				helpColumn += "\n\nArguments:\n" + String.join("\n", this.getCommandArg(c));
			} else {
				helpColumn += "\n\nNo arguments required";
			}
			if (c.getExamples().size() > 0) {
				helpColumn += "\n\nExamples:" + this.getCommandExamples(c);
			} else {
				helpColumn += "\n\nNo examples found";
			}
			// print current command
			String out = String.join("\n", this.combineWrap(
				new String[] {
					c.getCommand(),
					helpColumn
				}, new int[] {
					15, // length of the command-column
					65 // length of the help-column
				}
			));
			System.out.println(out + "\n");
		}
	}
	
	/**
	 * Formats the arguments of a command into a pretty string.
	 * @param command the command which should be formated
	 * @return pretty formated string
	 */
	@SuppressWarnings("rawtypes")
	protected String[] getCommandArg (CommandGeneric command) {
	    Iterator<Entry<String, String>> it = command.getArguments().entrySet().iterator();
	    String[] out = new String[0];
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        out = ArrayUtils.addAll(out, this.combineWrap(
				new String[] {
					(String) pair.getKey(),
					(String) pair.getValue()
				}, new int[] {
					15, // length of the argument-column
					50  // length of the help-column
				}));
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return out;
	}
	
	/**
	 * Formats the examples of a command into a pretty string.
	 * @param command the command which should be formated
	 * @return pretty formated string
	 */
	protected String getCommandExamples (CommandGeneric command) {
		String out = "";
		List<String> examples = command.getExamples();
		for (int i = 0; i < examples.size(); i++) {
			out += "\n" + String.join("\n", this.wrap(examples.get(i), 65));
		}
	    return out;
	}
	
	/**
	 * Helper function. Wraps lines after `length`-characters and after newline.
	 * @param s The input string
	 * @param length The maximum allowed length for the lines
	 * @return String[] Array of wrapped lines
	 */
	protected String[] wrap (String s, int length) {
		String[] tmp = s.split("\\r?\\n");
		String[] out = new String[0];
		for (int i = 0; i < tmp.length; i++) {
			out = ArrayUtils.addAll(out, WordUtils.wrap(tmp[i], length, "\n", true).split("\\r?\\n"));
		}
		for (int i = 0; i < out.length; i++) {
			out[i] = StringUtils.rightPad(out[i], length, " ");
		}
		return out;
	}
	
	/**
	 * Helper function. Creates something similar to a table. Example:
	 * combineWrap(String[]{"a", "b\nc"}, int[]{10, 5}) would return:
	 * a         b    
	 *           c    
	 * @param s Array of strings, each one will get its own "column"
	 * @param l Length of the column
	 * @return formated string
	 */
	protected String[] combineWrap (String[] s, int[] l) {
		String[][] tmp = new String[s.length][];
		int maxLines = 0;
		for (int i = 0; i < s.length; i++) {
			tmp[i] = this.wrap(s[i], l[i]);
			if (maxLines < tmp[i].length) {
				maxLines = tmp[i].length;
			}
		}
		String[] out = new String[maxLines];
		for (int i = 0; i < maxLines; i++) {
			for (int j = 0; j < l.length; j++) {
				if (out[i] == null) {
					out[i] = "";
				}
				if (i < tmp[j].length) {
					out[i] += tmp[j][i];
				} else {
					out[i] += StringUtils.rightPad("", l[j], " ");
				}
			}
		}
		return out;
	}

}
