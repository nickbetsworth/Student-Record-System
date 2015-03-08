package studentrecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/*
 * JDatePicker
 * 	Component used to let the user choose a date from 3 drop down menus
 */
@SuppressWarnings("serial")
public class JDatePicker extends JPanel {
	// Declare the JComboBoxes for the day, month and year
	private JComboBox<Integer> day;
	private JComboBox<Integer> month;
	private JComboBox<Integer> year;

	public JDatePicker() {
		// Call the super classes constructor (JPanel)
		super();

		// Initialise the JComboBoxes
		day = new JComboBox<Integer>();
		month = new JComboBox<Integer>();
		year = new JComboBox<Integer>();

		// Populate the day combo box
		for (int i = 1; i <= 31; i++) {
			day.addItem(i);
		}
		// Populate the month combo box
		for (int i = 1; i <= 12; i++) {
			month.addItem(i);
		}

		// Populate the year combo box
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = currentYear; i >= 1900; i--) {
			year.addItem(i);
		}

		// Add all of the JComboBoxes to the JPanel
		add(day);
		add(month);
		add(year);
	}

	// Returns the date selected
	// Returns null if an invalid date is specified
	// (Still allows incorrect dates for some reason e.g. 31st February)
	public Date getDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			// Attempt to create a Date from the data provided
			Date date = df.parse(day.getSelectedItem().toString() + "/"
					+ month.getSelectedItem().toString() + "/"
					+ year.getSelectedItem().toString());
			return date;
		} catch (ParseException e) {
			return null;
		}
	}
}
