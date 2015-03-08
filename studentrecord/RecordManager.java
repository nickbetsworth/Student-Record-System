package studentrecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * RecordManager
 * Stores a list of students and contains functions to manipulate this list
 */
public class RecordManager {
	// The array list which stores all students
	private ArrayList<Student> students;

	public RecordManager() {
		// Initialise the ArrayList
		students = new ArrayList<Student>();
	}

	// Checks if a student already exists in the record system
	public boolean studentExists(int studentNumber) {
		// Loop through all of the students and check if there is a 
		// Matching student number
		for (Student student : students) {
			if (student.getStudentNumber() == studentNumber) {
				return true;
			}
		}
		return false;
	}

	// Removes a student from the list of records
	// Note: 	no error is produced if an attempt is made to remove
	//			a student which does not exist in this set of data
	public void removeStudent(int studentNumber) {
		students.remove(getStudent(studentNumber));
	}

	// Adds a student to the list of students in the record system
	public void addStudent(Student newStudent) {
		students.add(newStudent);
	}

	// Returns the array list of all students in the system
	public ArrayList<Student> getStudents() {
		return students;
	}

	// Returns a student object from their student number
	// If such a student exists, otherwise returns null
	public Student getStudent(int studentNumber) {
		for (Student student : students) {
			if (student.getStudentNumber() == studentNumber) {
				return student;
			}
		}

		return null;
	}

	// Saves the student records to a file
	public void saveStudents(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fos);

			os.writeObject(students);

			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	// Loads students from the specified file
	public void loadStudents(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream is = new ObjectInputStream(fis);

			students = (ArrayList<Student>) is.readObject();

			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		// Will be run if the user specifies a non-serialised file
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid file specified");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
