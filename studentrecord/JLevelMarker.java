package studentrecord;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/*
 * JLevelMarker
 * 	Used as a way for the user to input a mark (between 0-100)
 */
@SuppressWarnings("serial")
public class JLevelMarker extends JSpinner {
	// Define the minimum and maximum marks
	public static final int MIN_MARK = 0;
	public static final int MAX_MARK = 100;

	public JLevelMarker() {
		// If the user does not give the constructor any parameters
		// Disable the component by default
		this(false);
	}

	public JLevelMarker(boolean enabled) {
		// Call the super constructor
		super(new SpinnerNumberModel(MIN_MARK, MIN_MARK, MAX_MARK, 1));
		// Enable/Disable the component based on the boolean provided in the parameters
		setEnabled(enabled);
	}

	// Returns the mark input to the component
	public int getMark() {
		// Only return the mark in the component if it is enabled
		if (isEnabled()) {
			return (Integer) getValue();
		// Return -1 if the component is disabled
		} else {
			return -1;
		}
	}
}
