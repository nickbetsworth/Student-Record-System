package studentrecord;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * AddStudentGUI
 * 	Used to create an interface where users can add a new user to the record system
 */
@SuppressWarnings("serial")
public class AddStudentGUI extends JFrame implements ActionListener {
	// Define the size of the JFrame
	private final int FRAME_WIDTH = 300;
	private final int FRAME_HEIGHT = 400;

	// The amount of columns JTextFields have
	private final int TEXT_FIELD_COLS = 12;

	// Stores an instance of the main recordManager
	private RecordManager recordManager;
	// Stores a reference back to the main GUI
	private GUI parentGUI;

	// Declare all of the swing components
	private JTextField txtName;
	private JDatePicker dateStudentDOB;
	private JTextField txtStudentNo;
	private JTextField txtSubject;
	private JComboBox<Integer> levelSelector;
	private JLevelMarker[] levelMarkers;
	private JButton btnAdd;

	// recordManager is an instance of the RecordManager from the main program
	// parentGUI is the main GUI which created an instance of this GUI
	public AddStudentGUI(RecordManager recordManager, GUI parentGUI) {
		// Sets the title of the JFrame
		super("Add Student");

		this.recordManager = recordManager;
		this.parentGUI = parentGUI;

		createGUI();
		
		// Make the frame visible
		setVisible(true);
	}

	// Creates the GUI components and configures the UI
	private void createGUI() {
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Create a new panel which will store all of the swing components
		JPanel pane = new JPanel();
		
		// Use a grid bag layout
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel lblAddStudent = new JLabel("Add Student");
		lblAddStudent.setFont(new Font("Verdana", Font.BOLD, 20));

		JLabel lblName = new JLabel("Name:");
		JLabel lblStudentNo = new JLabel("Student No:");
		JLabel lblDOB = new JLabel("Date of Birth:");
		JLabel lblSubject = new JLabel("Subject:");
		JLabel lblLevelSelect = new JLabel("Levels Completed:");

		// Create an array which will store the labels for each level
		JLabel[] lblLevels = new JLabel[Student.MAX_LEVEL];

		// Add all of the labels to the array
		for (int i = 0; i < lblLevels.length; i++) {
			lblLevels[i] = new JLabel("Level " + (i + 1) + ":");
		}

		txtName = new JTextField(TEXT_FIELD_COLS);
		dateStudentDOB = new JDatePicker();
		txtStudentNo = new JTextField(TEXT_FIELD_COLS);
		txtSubject = new JTextField(TEXT_FIELD_COLS);

		// Initialise the level selector
		levelSelector = new JComboBox<Integer>();
		// Add all of the possible levels to the drop down
		for (int i = 0; i <= Student.MAX_LEVEL; i++) {
			levelSelector.addItem(i);
		}
		
		// Create an array to store all of the JLevelMarkers
		levelMarkers = new JLevelMarker[Student.MAX_LEVEL];

		// Add all of the JLevelMarkers to the array
		for (int i = 0; i < levelMarkers.length; i++) {
			levelMarkers[i] = new JLevelMarker();
		}

		btnAdd = new JButton("Add Student");
		
		// Create all of the actionListeners
		levelSelector.addActionListener(this);
		btnAdd.addActionListener(this);
		

		// Add all label components to the JPanel
		c = GUI.createConstraint(0, 0, 3, 1, 0.0, 0.1, GridBagConstraints.CENTER);
		c.insets = new Insets(10, 0, 10, 0);
		pane.add(lblAddStudent, c);

		c = GUI.createConstraint(0, 1, 1, 1, 0.2, 0.1, GridBagConstraints.HORIZONTAL);
		c.insets = new Insets(0, 4, 0, 4);
		pane.add(lblName, c);
		c.gridy = 2;
		pane.add(lblDOB, c);
		c.gridy = 3;
		pane.add(lblStudentNo, c);
		c.gridy = 4;
		pane.add(lblSubject, c);
		c.gridy = 5;
		pane.add(lblLevelSelect, c);

		// Add the labels for each level e.g Level 1, Level2, ...
		for (JLabel lblLevel : lblLevels) {
			c.gridy++;
			pane.add(lblLevel, c);
		}

		// Add the rest of the components to the JPanel
		c = GUI.createConstraint(1, 1, 2, 1, 0.8, 0.1, GridBagConstraints.HORIZONTAL);
		pane.add(txtName, c);
		c.gridy = 2;
		pane.add(dateStudentDOB, c);
		c.gridy = 3;
		pane.add(txtStudentNo, c);
		c.gridy = 4;
		pane.add(txtSubject, c);

		c.gridy = 5;
		pane.add(levelSelector, c);

		// Add all of the JLevelMarkers to the panel
		for (JLevelMarker levelMarker : levelMarkers) {
			c.gridy++;
			pane.add(levelMarker, c);
		}

		// The button needs to be in the next vertical position down
		// (Needs to be dynamic due to an unknown amount of possible student levels)
		c.gridy++;

		c = GUI.createConstraint(0, c.gridy, 3, 1, 0.0, 0.1, GridBagConstraints.BOTH);
		pane.add(btnAdd, c);

		// Add the JPanel to the JFrame
		add(pane);
		// Pack the JFrame
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// User wishes to now add the student to the table
		if (e.getSource().equals(btnAdd)) {
			// If all inputs were valid
			if (isValidInput()) {
				// Gather all of the input information
				String name = txtName.getText();
				Date dob = dateStudentDOB.getDate();
				int studentNo = Integer.valueOf(txtStudentNo.getText());
				String subject = txtSubject.getText();

				// Store the students marks in an array
				int levelCompleted = (Integer) levelSelector.getSelectedItem();
				int[] marks = new int[levelCompleted];

				// Get all of the marks input to the JLevelMarkers
				for (int i = 0; i < marks.length; i++) {
					marks[i] = levelMarkers[i].getMark();
				}

				// Create the new student
				Student newStudent = new Student(name, dob, studentNo, subject, marks);
				// Add the student to the list of other students
				recordManager.addStudent(newStudent);
				// Update the main table
				parentGUI.addStudentRow(newStudent);

				// We're finished with this add student frame,
				// notify the user that the student has been added
				// and then close this frame
				JOptionPane.showMessageDialog(this, "Student added");
				dispose();
			}

		// User changed what level the student has completed
		} else if (e.getSource().equals(levelSelector)) {
			// Disable all of the level markers
			for (JLevelMarker levelMarker : levelMarkers) {
				levelMarker.setEnabled(false);
			}

			int selectedLevel = (Integer) levelSelector.getSelectedItem();
			
			// Enable all of the level markers up to and including the level marker for the selected level
			for (int i = 0; i < selectedLevel; i++) {
				levelMarkers[i].setEnabled(true);
			}
		}
	}

	// Determines whether the inputs specified are valid or not
	private boolean isValidInput() {
		boolean isValid = false;

		String name = txtName.getText();
		Date dob = dateStudentDOB.getDate();
		String subject = txtSubject.getText();

		// Check the validity of the student number
		boolean validStudentNo = true;
		// Stores the studentNo if it is valid
		int studentNo;
		try {
			// Try to convert the input from a string to integer
			studentNo = Integer.valueOf(txtStudentNo.getText());
			// There was an error converting from the string to integer (Invalid
			// student number)
		} catch (NumberFormatException nfe) {
			validStudentNo = false;
			studentNo = -1;
		}

		// If the user didn't specify a date
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a name");
			// The user gave us an invalid student number
		} else if (!validStudentNo) {
			JOptionPane.showMessageDialog(this, "Invalid student number");
			// Student has already been entered in to the system
		} else if (recordManager.studentExists(studentNo)) {
			JOptionPane.showMessageDialog(this, "That student already exists in the system");
			// Date will be null if an invalid date was specified
		} else if (dob == null) {
			JOptionPane.showMessageDialog(this, "Invalid date specified");
			// The user did not enter a subject
		} else if (subject.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a subject");
			// All data is valid
		} else {
			isValid = true;
		}

		return isValid;
	}
}
