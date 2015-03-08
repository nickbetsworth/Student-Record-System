package studentrecord;

import java.io.Serializable;
import java.util.Date;

/*
 * Student
 * 	Stores information about a single student including:
 * 		Name
 * 		Student Number
 * 		Date of Birth
 * 		Subject
 * 		Marks for each year completed
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 5128715244352268096L;
	
	// Define the maximum level a student can reach (in this system)
	public static final int MAX_LEVEL = 3;
	// Define a value out of the range of typical marks which we can use to say
	// the user has not completed x year
	public static final int INCOMPLETE_MARK = -1;

	// Declare all of the variables which will store the students information
	private String name;
	private int studentNumber;
	private Date dob;
	private String subject;

	private int[] marks;

	public Student(String name, Date dob, int studentNumber, String subject, int[] marks) {
		setName(name);
		setDob(dob);
		setStudentNumber(studentNumber);
		setSubject(subject);
		setMarks(marks);
	}

	// Getter for the students name
	public String getName() {
		return name;
	}

	// Setter for the students name
	public void setName(String name) {
		this.name = name;
	}

	// Getter for the students number
	public int getStudentNumber() {
		return studentNumber;
	}

	// Setter for the students number
	private void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	// Getter for date of birth
	public Date getDob() {
		return dob;
	}

	// Setter for date of birth
	private void setDob(Date dateOfBirth) {
		this.dob = dateOfBirth;
	}

	// Getter for subject
	public String getSubject() {
		return subject;
	}

	// Setter for subject
	public void setSubject(String subject) {
		this.subject = subject;
	}

	// Getter for the students age
	public int getAge() {
		// Get the students DOB in milliseconds
		long dobMillis = dob.getTime();

		Date now = new Date();
		// Get the current time in milliseconds
		long currentMillis = now.getTime();

		long timeElapsed = currentMillis - dobMillis;
		double age = timeElapsed / (1000.0 * 60.0 * 60.0 * 24.0 * 365.0);

		return (int) Math.floor(age);
	}
	
	// Get the number of years the student has completed
	public int getYearsComplete() {
		return marks.length;
	}
	
	// Gets the mark for a given year
	public int getMark(int year) {
		// If the student has completed this year return the mark they obtained
		if (year <= marks.length) {
			return marks[year - 1];
		// The student has not completed the year, return the incomplete
		// mark we defined
		} else {
			return INCOMPLETE_MARK;
		}
	}

	// Setter for the array of marks
	private void setMarks(int[] marks) {
		this.marks = marks;
	}

	// Getter for the students average mark
	public double getAverageMark() {
		// If the student has any marks
		// Avoids division by zero errors and null pointer exceptions
		if (marks.length > 0) {
			double totalMark = 0;

			for (int mark : marks) {
				totalMark += mark;
			}

			double averageMark = totalMark / marks.length;

			return averageMark;
		// The student has no marks
		} else {
			return 0;
		}
	}

}
