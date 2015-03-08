package studentrecord;

/*
 * Main
 * 	Contains the main method for our student record system
 */
public class Main {
	public static void main(String[] args) {
		// Create an instance of the RecordManager which will store all of our
		// Student Records
		RecordManager manager = new RecordManager();
		
		// Create the GUI, no need to assign to a variable as it is never used
		new GUI(manager);
	}

}
