package dev.syndesi.colorconverter.runtime;


/**
 * Entry point for the whole program.
 * @author Syndesi
 * @since 1.0
 */
public class Main {
	
	/**
	 * Initializes a new CommandChooser and executes it. This function is more or less a wrapper.
	 * @param args the arguments given from the outside
	 */
	public static void main(String[] args) {
    	try {
    		CommandChooser c = new CommandChooser();
    		c.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
