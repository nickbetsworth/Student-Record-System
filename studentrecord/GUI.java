package studentrecord;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/*
 * GUI
 * 	Main Interface for the student record system
 * 	Displays all of the students in a table
 * 	Also allows the user to access other parts of the program
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	// Define the size of this JFrame
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 500;

	// Stores an instance of the recordManager
	private RecordManager recordManager;

	// Used to store and model the student records
	private DefaultTableModel tableModel;
	private JTable studentTable;

	// Stores a list of all the column names for the table
	private Vector<String> colNames;

	// Declare all of the swing components
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveStudents;
	private JMenuItem loadStudents;
	private JMenu studentMenu;
	private JMenuItem addStudent;
	private JMenuItem removeStudent;

	// Create the file chooser for when a user wishes
	// to either save or load a student file
	private JFileChooser fileChooser;

	public GUI(RecordManager recordManager) {
		// Set the title of the JFrame
		super("Student Record System");

		this.recordManager = recordManager;

		createGUI();
		
		// Show the JFrame
		setVisible(true);
	}

	// Create the GUI
	private void createGUI() {
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create the panel which will contain all of our swing components
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Create a menu
		menuBar = new JMenuBar();

		// Set the JMenuBar for our JFrame to the JMenuBar we just created
		this.setJMenuBar(menuBar);

		// Create all of the components for the menu
		fileMenu = new JMenu("File");
		saveStudents = new JMenuItem("Save Students");
		loadStudents = new JMenuItem("Load Students");

		studentMenu = new JMenu("Students");
		addStudent = new JMenuItem("Add Student");
		removeStudent = new JMenuItem("Remove Student");

		// Create all of the action listeners for the menu items
		saveStudents.addActionListener(this);
		loadStudents.addActionListener(this);
		addStudent.addActionListener(this);
		removeStudent.addActionListener(this);

		// Add all of the components to the menu
		fileMenu.add(saveStudents);
		fileMenu.add(loadStudents);

		studentMenu.add(addStudent);
		studentMenu.add(removeStudent);

		menuBar.add(fileMenu);
		menuBar.add(studentMenu);

		// Initialise the file chooser
		fileChooser = new JFileChooser();

		// Create all of the column names for the table
		colNames = new Vector<String>();
		colNames.add("Name");
		colNames.add("Age");
		colNames.add("Student Number");
		colNames.add("Subject");
		colNames.add("Levels Completed");
		
		// Add a column for each level
		for (int i = 1; i <= Student.MAX_LEVEL; i++) {
			colNames.add("Level " + i + " Mark");
		}
		
		colNames.add("Average Mark");

		// Create the table which will display all of the student records
		studentTable = new JTable();
		
		// Call the reloadTable function to apply a TableModel to the table
		reloadTable();
		
		// Disable the studentTable so that the data can not be edited
		studentTable.setEnabled(false);
		// Add a JScrollPane to the table in case the set of data is too large to be displayed
		JScrollPane scrollPane = new JScrollPane(studentTable);

		c = createConstraint(0, 0, 1, 1, 0.5, 1, GridBagConstraints.BOTH);
		pane.add(scrollPane, c);

		// Add the JPanel to the JFrame
		add(pane);
		// Pack the JFrame
		pack();
	}

	// Function used when adding components to our JPanel using the GridBagLayout
	// Not 100% necessary but cleans up the code in some parts
	public static GridBagConstraints createConstraint(int gridx, int gridy,
			int gridwidth, int gridheight, double weightx, double weighty,
			int fill) {

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = fill;

		return c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// The user wishes to save the students
		if (e.getSource().equals(saveStudents)) {
			// If the user successfully chooses a file they wish to save to
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File chosenFile = fileChooser.getSelectedFile();

				// Call the saveStudents function with the file the user selected
				recordManager.saveStudents(chosenFile);
			}
		// The user wishes to load students from a file
		} else if (e.getSource().equals(loadStudents)) {
			// If the user successfully chooses a file they wish to load from
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File chosenFile = fileChooser.getSelectedFile();

				// Call the loadStudents function with the file the user selected
				recordManager.loadStudents(chosenFile);
				// Reload the table as the data set has changed
				reloadTable();
			}
		// The user wishes to begin adding a new student to the record system
		} else if (e.getSource().equals(addStudent)) {
			// Open up an instance of the AddStudentGUI
			// No need to assign it to a variable as it will
			// Not be required again
			new AddStudentGUI(recordManager, this);
		// The user wishes to remove a student from the record system
		} else if (e.getSource().equals(removeStudent)) {
			// Surround with a try catch block because of a possible
			// NumberFormatException
			try {
				// Get the user to input the student number of the student they wish to remove
				int studentNumber = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the students number"));

				// Attempt to remove the student specified from the record manager
				recordManager.removeStudent(studentNumber);

				// Reload the table as it has been changed
				reloadTable();
			// The user did not enter an integer
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid student number");
			}
		}
	}

	// Add a new student to the table
	public void addStudentRow(Student student) {
		tableModel.addRow(getStudentsInformation(student));
	}

	// Create a new table model
	public void reloadTable() {
		tableModel = new DefaultTableModel(colNames, 0);

		ArrayList<Student> students = recordManager.getStudents();

		// Loop through the ArrayList of students and add a row for each one
		for (Student student : students) {
			tableModel.addRow(getStudentsInformation(student));
		}

		// Set the DefaultTableModel for our JTable to the table model we just created
		studentTable.setModel(tableModel);
	}
	
	// Returns a Vector<String> of the students information
	// Not useful inside the student class as it's in a very specific order
	// Only suited to this JTable
	public Vector<String> getStudentsInformation(Student student) {
		Vector<String> studentInfo = new Vector<String>();
		studentInfo.add(student.getName());
		studentInfo.add(String.valueOf(student.getAge()));
		studentInfo.add(String.valueOf(student.getStudentNumber()));
		studentInfo.add(student.getSubject());
		studentInfo.add(String.valueOf(student.getYearsComplete()));

		for (int i = 0; i < Student.MAX_LEVEL; i++) {
			if (i < student.getYearsComplete()) {
				studentInfo.add(String.valueOf(student.getMark(i + 1)) + "%");
			} else {
				studentInfo.add("");
			}
		}

		// Create a DecimalFormat to round the marks to 2 decimal places
		DecimalFormat df = new DecimalFormat("00.00");
		studentInfo.add(df.format(student.getAverageMark()) + "%");
		
		return studentInfo;
	}

}
