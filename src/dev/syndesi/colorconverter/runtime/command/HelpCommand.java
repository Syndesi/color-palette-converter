package dev.syndesi.colorconverter.runtime.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import dev.syndesi.colorconverter.runtime.Command;

public class HelpCommand extends Command {
	
	protected List<Command> commands;

	public HelpCommand () {
		super();
		this.command = "help";
		this.help = "Lists all available commands, their arguments and help messages.\nNote: In the later examples the name of the program file will be shortened to cc (color converter).";
		this.commands = null;
	}
	
	public void setCommands (List<Command> commands) {
		this.commands = commands;
	}

	public void run (String[] args) throws Exception {
		if (this.commands == null) {
			throw new Exception("No commands found");
		}
		for (int i = 0; i < this.commands.size(); i++) {
			Command c = this.commands.get(i);
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
	
	@SuppressWarnings("rawtypes")
	public String[] getCommandArg (Command command) {
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
					50 // length of the help-column
				}));
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return out;
	}
	
	@SuppressWarnings("rawtypes")
	public String getCommandExamples (Command command) {
		String out = "";
		List<String> examples = command.getExamples();
		for (int i = 0; i < examples.size(); i++) {
			out += "\n" + String.join("\n", this.wrap(examples.get(i), 65));
		}
	    return out;
	}
	
	/**
	 * Wraps lines after `lenght`-characters and after newline.
	 * @param s The input string
	 * @param length The maximum allowed length for the lines
	 * @return String[] Array of wrapped lines
	 */
	public String[] wrap (String s, int length) {
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
	 * Creates something similar to a table. Example:
	 * combineWrap(String[]{"a", "b\nc"}, int[]{10, 5}) would return:
	 * a         b    
	 *           c    
	 * @param s Array of strings, each one will get its own "column"
	 * @param l Length of the column
	 * @return
	 */
	public String[] combineWrap (String[] s, int[] l) {
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
