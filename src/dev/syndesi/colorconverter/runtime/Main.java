package dev.syndesi.colorconverter.runtime;

public class Main {
	
	public static void main(String[] args) {
    	try {
    		CommandChooser c = new CommandChooser();
    		c.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
