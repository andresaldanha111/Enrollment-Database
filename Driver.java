import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * The class "Driver" designs the entire UI
 */

public class Driver extends JFrame {	
	private Connection myConn;
	private Statement myStmt;
	
	private final int WINDOW_WIDTH = 500; // Window width
	private final int WINDOW_HEIGHT = 400; // Window height
	
	private JMenuBar menuBar;
	
	private JMenu createMenu;
	private JMenu searchMenu;
	private JMenu gradesMenu;
	private JMenu reportMenu;
	private JMenu sortMenu;
	
	private JMenuItem createStudent;
	private JMenuItem createCourse;
	private JMenuItem createEnrollment;
	private JMenuItem createDepartment;
	private JMenuItem createInstructor;
	
	private JMenuItem searchStudent;
	private JMenuItem searchCourse;
	private JMenuItem searchEnrollment;
	private JMenuItem searchDepartment;
	private JMenuItem searchInstructor;
	
	private JMenuItem byStudent;
	private JMenuItem byCourse;
	
	private JMenuItem report;
	
	private JPanel home;
	private JLabel message;

	private CreateStudentPanel cStudent;
	private ViewStudentPanel vStudent;
	private SearchStudentPanel sStudent;
	
	private CreateCoursePanel cCourse;
	private ViewCoursePanel vCourse;
	private SearchCoursePanel sCourse;
	
	private CreateEnrollmentPanel cEnrollment;
	private ViewEnrollmentPanel vEnrollment;
	private SearchEnrollmentPanel sEnrollment;
	
	private ViewGradesStuPanel sGrades;
	private ViewGradesCPanel cGrades;
	
	private ReportPanel reportPan;
	
	private CreateInstructorPanel cInstr;
	private SearchInstructorPanel sInstr;
	private ViewInstructorPanel vInstr;
	private CreateDepartmentPanel cDept;
	private SearchDepartmentPanel sDept;
	private ViewDepartmentPanel vDept;
	
	
	/**
	 * The following methods are to build the menu
	 */	
	public Driver() throws Exception {
		String[] connectionInfo = new String[3];
		try {
			File myObj = new File("app.config");
			Scanner myReader = new Scanner(myObj);
			for(int i = 0; i < 3; i++)
				connectionInfo[i] = myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		myConn = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
		myStmt = myConn.createStatement();
		System.out.println("Successfully Connected.");
		
		setTitle("Records");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildMenuBar();
		
		displayHomePanel();
	}
	
	public void buildMenuBar() {
		menuBar = new JMenuBar();
		
		buildCreateMenu();
		buildSearchMenu();
		buildGradesMenu();
		buildReportMenu();
		
		menuBar.add(createMenu);
		menuBar.add(searchMenu);
		menuBar.add(gradesMenu);
		menuBar.add(reportMenu);
		
		setJMenuBar(menuBar);
	}
	
	public void buildCreateMenu() {
		createStudent = new JMenuItem("Student");
		createCourse = new JMenuItem("Course");
		createEnrollment = new JMenuItem("Enrollment");
		createDepartment = new JMenuItem("Department");
		createInstructor = new JMenuItem("Instructor");
		
		createStudent.addActionListener(new CreateStudentListener());
		createCourse.addActionListener(new CreateCourseListener());
		createEnrollment.addActionListener(new CreateEnrollmentListener());
		createDepartment.addActionListener(new CreateDepartmentListener());
		createInstructor.addActionListener(new CreateInstructorListener());
		
		createMenu = new JMenu("Create");
		
		createMenu.add(createStudent);
		createMenu.add(createCourse);
		createMenu.add(createEnrollment);
		createMenu.add(createDepartment);
		createMenu.add(createInstructor);
	}
	
	public void buildSearchMenu() {
		searchStudent = new JMenuItem("Student");
		searchCourse = new JMenuItem("Course");
		searchEnrollment = new JMenuItem("Enrollment");
		searchDepartment = new JMenuItem("Department");
		searchInstructor = new JMenuItem("Instructor");
		
		searchStudent.addActionListener(new SearchStudentListener());
		searchCourse.addActionListener(new SearchCourseListener());
		searchEnrollment.addActionListener(new SearchEnrollmentListener());
		searchDepartment.addActionListener(new SearchDepartmentListener());
		searchInstructor.addActionListener(new SearchInstructorListener());
		
		searchMenu = new JMenu("Search");
		
		searchMenu.add(searchStudent);
		searchMenu.add(searchCourse);
		searchMenu.add(searchEnrollment);
		searchMenu.add(searchDepartment);
		searchMenu.add(searchInstructor);
	}
	
	public void buildGradesMenu() {
		byStudent = new JMenuItem("By Student");
		byCourse = new JMenuItem("By Course");
		
		byStudent.addActionListener(new ByStudentListener());
		byCourse.addActionListener(new ByCourseListener());
		
		gradesMenu = new JMenu("Grade Management");
		
		gradesMenu.add(byStudent);
		gradesMenu.add(byCourse);
	}
	
	public void buildReportMenu() {
		report = new JMenuItem("Report");
		report.addActionListener(new ReportListener());
		
		reportMenu = new JMenu("Report");
		reportMenu.add(report);
	}	
	
	
	/**
	 * The following action listeners respond to the menu items
	 */
	
	private class CreateStudentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayCreateStudentPanel();
		}
	}
	
	private class CreateCourseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayCreateCoursePanel();
		}
	}
	
	private class CreateEnrollmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cEnrollment = new CreateEnrollmentPanel();
			displaySearchStudentPanel(true, false);
		}
	}
	
	private class CreateDepartmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayCreateDepartmentPanel();
		}
	}
	
	private class CreateInstructorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayCreateInstructorPanel();
		}
	}
	
	private class SearchStudentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchStudentPanel(false, false);
		}
	}
	
	private class SearchCourseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchCoursePanel(false, false);
		}
	}
	
	private class SearchEnrollmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchEnrollmentPanel();
		}
	}
	
	private class SearchDepartmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchDepartmentPanel();
		}
	}
	
	private class SearchInstructorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchInstructorPanel();
		}
	}
	
	private class ByStudentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchStudentPanel(false, true);
		}
	}
	
	private class ByCourseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchCoursePanel(false, true);
		}
	}
	
	private class ReportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displaySearchReportPanel();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Driver app = new Driver();
	}
	
	
	
	/**
	 * The following methods clear the frame and add a new panel
	 */
	public void displayHomePanel() {
		getContentPane().removeAll();
		
		home = new JPanel();
		home.setLayout(new BorderLayout());
		
		message = new JLabel("Menu", SwingConstants.CENTER);
		home.add(message);
		add(home, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void displayCreateInstructorPanel() {
		getContentPane().removeAll();
		
		cInstr = new CreateInstructorPanel();
		add(cInstr, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateDepartmentPanel() {
		getContentPane().removeAll();
		
		cDept = new CreateDepartmentPanel();
		add(cDept, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateStudentPanel() {
		getContentPane().removeAll();
		
		cStudent = new CreateStudentPanel();
		add(cStudent, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateStudentPanel(String[] info) {
		getContentPane().removeAll();
		
		cStudent = new CreateStudentPanel();
		cStudent.setEditHeader();
		cStudent.setStid(Integer.parseInt(info[0]));
		cStudent.setTextFields(info);
		
		add(cStudent, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayViewStudentPanel(int stid, boolean enrollment) {
		getContentPane().removeAll();
		
		vStudent = new ViewStudentPanel(stid, enrollment);
		add(vStudent, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchStudentPanel(boolean enrollment, boolean grades) {
		getContentPane().removeAll();
		
		sStudent = new SearchStudentPanel(enrollment, grades);
		add(sStudent, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateCoursePanel() {
		getContentPane().removeAll();
		
		cCourse = new CreateCoursePanel();
		add(cCourse, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateCoursePanel(String[] info) {
		getContentPane().removeAll();
		
		cCourse = new CreateCoursePanel();
		cCourse.setEditHeader();
		cCourse.setCid(Integer.parseInt(info[0]));
		cCourse.setTextFields(info);
		
		add(cCourse, BorderLayout.CENTER);

		setVisible(true);
	}

	public void displayViewCoursePanel(int cid, boolean enrollment) {
		getContentPane().removeAll();
		
		vCourse = new ViewCoursePanel(cid, enrollment);
		add(vCourse, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchCoursePanel(boolean enrollment, boolean grades) {
		getContentPane().removeAll();
		
		sCourse = new SearchCoursePanel(enrollment, grades);
		add(sCourse, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchReportPanel() {
		getContentPane().removeAll();
		
		sCourse = new SearchCoursePanel(false, true);
		sCourse.forReport();
		add(sCourse, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchCoursePanel(boolean enrollment, int id) {
		getContentPane().removeAll();
		
		sCourse = new SearchCoursePanel(enrollment, false);
		sCourse.setId(id);
		add(sCourse, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayCreateEnrollmentPanel() {
		getContentPane().removeAll();
		
		add(cEnrollment, BorderLayout.CENTER);

		setVisible(true);
	}

	public void displayViewEnrollmentPanel(int eid) {
		getContentPane().removeAll();
		
		vEnrollment = new ViewEnrollmentPanel(eid);
		add(vEnrollment, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayViewDepartmentPanel(int did) {
		getContentPane().removeAll();
		
		vDept = new ViewDepartmentPanel(did);
		add(vDept, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayViewInstructorPanel(int iid) {
		getContentPane().removeAll();
		
		vInstr = new ViewInstructorPanel(iid);
		add(vInstr, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchDepartmentPanel() {
		getContentPane().removeAll();
		
		sDept = new SearchDepartmentPanel();
		add(sDept, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchInstructorPanel() {
		getContentPane().removeAll();
		
		sInstr = new SearchInstructorPanel();
		add(sInstr, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displaySearchEnrollmentPanel() {
		getContentPane().removeAll();
		
		sEnrollment = new SearchEnrollmentPanel();
		add(sEnrollment, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayViewStuGradesPanel(int stid) {
		getContentPane().removeAll();
		
		sGrades = new ViewGradesStuPanel(stid);
		add(sGrades, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayViewCGradesPanel(int cid) {
		getContentPane().removeAll();
		
		cGrades = new ViewGradesCPanel(cid);
		add(cGrades, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void displayReportPanel(int cid, String year) {
		getContentPane().removeAll();
		
		reportPan = new ReportPanel(cid, year);
		add(reportPan, BorderLayout.CENTER);

		setVisible(true);
	}	
	
	
	/**
	 * The following classes design the various panels
	 * that make up the UI
	 */
	private class CreateDepartmentPanel extends JPanel {

		private JTextField textField;
		private JLabel label;
		private JLabel header;
		
		private JButton saveButton;
		private JButton cancelButton;
	
		private int did;
		
		public CreateDepartmentPanel() {
			setLayout(null);
			
			did = 0;
			
			header = new JLabel("CREATE NEW DEPARTMENT");
			header.setBounds(160, 25, 200, 25);
			add(header);
			
			textField = new JTextField(20);
			textField.setBounds(200, 70, 150, 25);
			add(textField);
			
			label = new JLabel("Name");
			label.setBounds(100, 70, 150, 25);
			add(label);
			
			saveButton = new JButton("Save");
			saveButton.setBounds(120, 200, 90, 25);
			saveButton.addActionListener(new SaveDepartmentListener());
			add(saveButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setBounds(220, 200, 90, 25);
			cancelButton.addActionListener(new CancelListener());
			add(cancelButton);
		}
		
		public void setDid(int id) {
			did = id;
		}

		/**
		 * The following action listener class responds to "Save" button
		 * It writes the info in the text fields to a binary file
		 */
		private class SaveDepartmentListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					//Note: did = 0 when a new dept is being created. 
					//Did is not 0 when dept is being edited.
					if(Integer.valueOf(did) == 0) {
						String sqlStatement = "INSERT INTO Department (did, name) VALUES (" + did + ", '" + textField.getText() + "')";
						System.out.println(sqlStatement);
						
						myStmt.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
						
						ResultSet result = myStmt.getGeneratedKeys();
						result.next();
						System.out.println(result.getInt(1));
						did = result.getInt(1);
			
					}
					else {
						String sqlStatement = "UPDATE Department SET Name = '" + textField.getText() + "' WHERE Did =" + did;
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement);
					}
				}	catch(Exception sqlError) {}
	
				displayViewDepartmentPanel(did);

			}
		}
		
		
		/**
		 * The following action listener class responds to the "Cancel" button
		 * It calls the method to display the original Menu Panel
		 */
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}

	}
	
	private class ViewDepartmentPanel extends JPanel {
		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		public ViewDepartmentPanel(int id) {
			setLayout(null);
			
			textFields = new JTextField[2];
			labels = new JLabel[2];
			
			header = new JLabel("DEPARTMENT INFO");
			header.setBounds(175, 25, 150, 25);
			add(header);
			
			String[] labelText = {"Department ID", "Name: "};
			
			try {
				CallableStatement getDept = myConn.prepareCall("{CALL getDepartment(" + id + ")}");
				ResultSet result = getDept.executeQuery();
				result.next();
				
				for(int i = 0; i < textFields.length; i++) {
					textFields[i] = new JTextField(20);
					textFields[i].setBounds(200, 70 + 30*i, 150, 25);
					textFields[i].setEditable(false);
					textFields[i].setText(result.getString(i+1));
					add(textFields[i]);
					
					labels[i] = new JLabel(labelText[i]);
					labels[i].setBounds(100, 70 + 30*i, 150, 25);
					add(labels[i]);
				}
				
			}	catch(Exception sql) {System.out.println(sql.getMessage());}		
			
		}

	}
	
	private class SearchDepartmentPanel extends JPanel {
		private JLabel prompt;
		private JComboBox idList;
		private JButton searchButton;

		public SearchDepartmentPanel() {			
			setLayout(null);
			
			prompt = new JLabel("Select Department ID:");
			prompt.setBounds(110, 115, 125, 14);
			add(prompt);
			
			ArrayList<Integer> ids = new ArrayList<>();
		
			try {
				String sqlStatement = "SELECT Did FROM Department";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				
				while(result.next())
					ids.add(result.getInt(1));
			} catch(Exception sql) {}
			
			idList = new JComboBox(ids.toArray());
			idList.setBounds(240, 113, 50, 20);
			add(idList);
				
			searchButton = new JButton("Search");
			searchButton.setBounds(165, 175, 90, 25);
			searchButton.addActionListener(new SearchDepartmentListener());
			add(searchButton);

		}
		
		private class SearchDepartmentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayViewDepartmentPanel((Integer)idList.getSelectedItem());
			}
		}
	}
	
	private class CreateInstructorPanel extends JPanel {
		private JTextField textField;
		private JLabel[] labels;
		private JLabel header;
		private JComboBox dept;
		
		private JButton saveButton;
		private JButton cancelButton;
	
		private int iid;
		
		public CreateInstructorPanel() {
			setLayout(null);
			
			textField = new JTextField();
			labels = new JLabel[2];
			
			header = new JLabel("CREATE NEW INSTRUCTOR");
			header.setBounds(160, 25, 200, 25);
			add(header);
	
			String[] labelText = {"Name:", "Dept:"};
			
			for(int i = 0; i < labels.length; i++) {				
				labels[i] = new JLabel(labelText[i]);
				labels[i].setBounds(100, 70 + 30*i, 150, 25);
				add(labels[i]);
			}
			
			textField = new JTextField(20);
			textField.setBounds(200, 70, 150, 25);
			add(textField);
			
			
			ArrayList<String> deptNames = new ArrayList<>();
			
			try {
				String sqlStatement = "SELECT * FROM Department";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					deptNames.add(result.getInt(1) + " | " + result.getString(2));
			} catch(Exception sql) {}
			
			dept = new JComboBox(deptNames.toArray());
			dept.setBounds(200, 100, 150, 25);
			add(dept);
	
			saveButton = new JButton("Save");
			saveButton.setBounds(120, 200, 90, 25);
			saveButton.addActionListener(new SaveInstructorListener());
			add(saveButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setBounds(220, 200, 90, 25);
			cancelButton.addActionListener(new CancelListener());
			add(cancelButton);
			
			iid = 0;
		}
		
		public void setIid(int id) {
			iid = id;
		}

		/**
		 * The following action listener class responds to "Save" button
		 * It writes the info in the text fields to a binary file
		 */
		private class SaveInstructorListener implements ActionListener {
		
			public void actionPerformed(ActionEvent e) {
				
				try {
					//Note: did = 0 when a new dept is being created. 
					//Did is not 0 when dept is being edited.
					if(Integer.valueOf(iid) == 0) {
						String sqlStatement = "INSERT INTO Instructor (iid, name, did) VALUES (" + iid + ", '" + textField.getText() + "', " + (dept.getSelectedIndex() + 1) + ")";
						System.out.println(sqlStatement);
						
						myStmt.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
						
						ResultSet result = myStmt.getGeneratedKeys();
						result.next();
						System.out.println(result.getInt(1));
						iid = result.getInt(1);
					}
					else {
						String sqlStatement = "UPDATE Instructor SET Name = '" + textField.getText() + "', Did = " + (dept.getSelectedIndex() + 1) + " WHERE Iid =" + iid;
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement);
					}
				}	catch(Exception sqlError) {}
	
				displayViewInstructorPanel(iid);

			}
		}
		
		/**
		 * The following action listener class responds to the "Cancel" button
		 * It calls the method to display the original Menu Panel
		 */
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}

	}
	
	private class ViewInstructorPanel extends JPanel {
		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		public ViewInstructorPanel(int id) {
			setLayout(null);
			
			textFields = new JTextField[3];
			labels = new JLabel[3];
			
			header = new JLabel("INSTRUCTOR INFO");
			header.setBounds(175, 25, 150, 25);
			add(header);
			
			String[] labelText = {"Instructor ID: ", "Name: ", "Department: "};
			
			try {
				String sqlStatement = "SELECT * FROM Instructor WHERE Iid = " + id;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				result.next();
				
				for(int i = 0; i < textFields.length; i++) {
					textFields[i] = new JTextField(20);
					textFields[i].setBounds(200, 70 + 30*i, 150, 25);
					textFields[i].setEditable(false);
					textFields[i].setText(result.getString(i+1));
					add(textFields[i]);
					
					labels[i] = new JLabel(labelText[i]);
					labels[i].setBounds(100, 70 + 30*i, 150, 25);
					add(labels[i]);
				}
			}	catch(Exception sql) {}		
			
		}

	}
	
	private class SearchInstructorPanel extends JPanel {
		private JLabel prompt;
		private JComboBox idList;
		private JButton searchButton;

		public SearchInstructorPanel() {			
			setLayout(null);
			
			prompt = new JLabel("Select Instructor ID:");
			prompt.setBounds(110, 115, 125, 14);
			add(prompt);
			
			ArrayList<Integer> ids = new ArrayList<>();
			
			try {
				String sqlStatement = "SELECT Iid FROM Instructor";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					ids.add(result.getInt(1));
			} catch(Exception sql) {}
			
			idList = new JComboBox(ids.toArray());
			idList.setBounds(240, 113, 50, 20);
			add(idList);
				
			searchButton = new JButton("Search");
			searchButton.setBounds(165, 175, 90, 25);
			searchButton.addActionListener(new SearchInstructorListener());
			add(searchButton);

		}

		private class SearchInstructorListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayViewInstructorPanel((Integer)idList.getSelectedItem());
			}
		}
	}
	
	private class CreateStudentPanel extends JPanel {

		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton saveButton;
		private JButton cancelButton;
	
		private int stid;
		
		public CreateStudentPanel() {
			setLayout(null);
			
			stid = 0;
			
			textFields = new JTextField[4];
			labels = new JLabel[4];
			
			header = new JLabel("CREATE NEW STUDENT");
			header.setBounds(160, 25, 150, 25);
			add(header);
	
			String[] labelText = {"Name:", "Address: ", "City: ", "State: "};
			
			for(int i = 0; i < textFields.length; i++) {
				textFields[i] = new JTextField(20);
				textFields[i].setBounds(200, 70 + 30*i, 150, 25);
				add(textFields[i]);
				
				labels[i] = new JLabel(labelText[i]);
				labels[i].setBounds(100, 70 + 30*i, 150, 25);
				add(labels[i]);
			}
	
			saveButton = new JButton("Save");
			saveButton.setBounds(120, 200, 90, 25);
			saveButton.addActionListener(new SaveStudentListener());
			add(saveButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setBounds(220, 200, 90, 25);
			cancelButton.addActionListener(new CancelListener());
			add(cancelButton);
		}
		
		public void setStid(int id) {
			stid = id;
		}
		
		public void setTextFields(String[] info) {
			for(int i = 0; i < textFields.length; i++)
				textFields[i].setText(info[i + 1]);
		}
		
		public void setEditHeader() {
			header.setText("EDIT STUDENT");
			header.setBounds(180, 25, 150, 25);
		}
		
		/**
		 * The following action listener class responds to "Save" button
		 * It writes the info in the text fields to a binary file
		 * Then, it calls the method that displays the View Student Panel
		 */
		
		private class SaveStudentListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					//Note: stid = 0 when a new student is being created. 
					//Stid is not 0 when student is being edited.
					if(Integer.valueOf(stid) == 0) {
						String sqlStatement = "INSERT INTO Student (stid, name, address, city, state) VALUES (" + stid;
						for(int i = 0; i < 4; i++)
							sqlStatement += ", '" + textFields[i].getText() + "'";
						sqlStatement += ")";
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
						ResultSet result = myStmt.getGeneratedKeys();
						while(result.next()) {
							System.out.println(result.getInt(1));
							stid = result.getInt(1);
						}
					}
					else {						
						String[] cols = {"Name", "Address", "City", "State"};
						String sqlStatement = "UPDATE Student SET ";
						for(int i = 0; i < 4; i++) {
							sqlStatement += cols[i] + " = " + "'" + textFields[i].getText() + "'";
							if(i < 3)
								sqlStatement += ", ";
						}
						sqlStatement += " WHERE Stid = " + stid;
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement);
					}

				}	catch(Exception sqlError) {}
	
				displayViewStudentPanel(stid, false);

			}
		}
		
		/**
		 * The following action listener class responds to the "Cancel" button
		 * It calls the method to display the original Menu Panel
		 * A student is not created and information in the textfields aren't written to the file
		 */
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}

	}
	
	private class ViewStudentPanel extends JPanel {
		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton button1;
		private JButton button2;
	
		private int stid;

		public ViewStudentPanel(int id, boolean forEnrollment) {
			setLayout(null);
			
			stid = id;
			
			textFields = new JTextField[5];
			labels = new JLabel[5];
			
			header = new JLabel("STUDENT INFO");
			header.setBounds(180, 25, 150, 25);
			add(header);
			
			String[] labelText = {"Student ID", "Name:", "Address: ", "City: ", "State: "};

			try {
				//Get Student Info from database to display
				String sqlStatement = "SELECT * FROM Student WHERE Stid=" + id;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				result.next();
				
				for(int i = 0; i < textFields.length; i++) {
					textFields[i] = new JTextField(20);
					textFields[i].setBounds(200, 70 + 30*i, 150, 25);
					textFields[i].setEditable(false);
					textFields[i].setText(result.getString(i + 1));
					add(textFields[i]);
					
					labels[i] = new JLabel(labelText[i]);
					labels[i].setBounds(100, 70 + 30*i, 150, 25);
					add(labels[i]);
				}
			}	catch(Exception sql) {System.out.println(sql.getMessage());}
			
			if(forEnrollment) {
				button1 = new JButton("Continue");
				button1.addActionListener(new ContinueEnrollmentListener());
				
				button2 = new JButton("Cancel");
				button2.addActionListener(new CancelListener());
			}
			else {
				button1 = new JButton("Edit");
				button2 = new JButton("Remove");
				button1.addActionListener(new EditStudentListener());
				button2.addActionListener(new RemoveListener());
			}
			
			button1.setBounds(120, 230, 90, 25);
			add(button1);

			button2.setBounds(220, 230, 90, 25);
			add(button2);
		}
		
		/**
		 * This Action Listener class responds to the "Edit" button
		 * It allows the user to edit the information of the student
		 */
		
		private class EditStudentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String[] info = new String[textFields.length];
				for(int i = 0; i < textFields.length; i++) {
					info[i] = textFields[i].getText();
				}
				displayCreateStudentPanel(info);
			}
		}
		
		private class RemoveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

				try {				
					String sqlStatement = "DELETE FROM Student WHERE Stid = " + stid;
					myStmt.executeUpdate(sqlStatement);
				} catch(Exception sql) {}
				displayHomePanel();
			}
		}
		
		/**
		 * This Action Listener class responds to the "Continue" button
		 * 
		 * The button is only displayed if this panel is being used to
		 * display student information during the Create Enrollment process
		 */
		
		private class ContinueEnrollmentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				cEnrollment.setStid(stid);
				displaySearchCoursePanel(true, false);
			}
		}
		
		/**
		 * This Action Listener class responds to the "Cancel" button
		 * 
		 * The button is only displayed if this panel is being used to
		 * display student information during the Create Enrollment process
		 * 
		 * It calls the method to display the original Menu Panel
		 * An enrollment is not created and nothing is written to the file
		 */
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}
		
	}

	private class SearchStudentPanel extends JPanel {
		private JLabel prompt;
		private JComboBox idList;
		private JButton searchButton;
		
		private boolean forEnrollment;
		private boolean forGrades;


		public SearchStudentPanel(boolean enrollment, boolean grades) {
			forEnrollment = enrollment;
			forGrades = grades; 
			
			setLayout(null);
			
			prompt = new JLabel("Select Student ID:");
			prompt.setBounds(128, 115, 113, 14);
			add(prompt);
		
			ArrayList<Integer> ids = new ArrayList<>();
			try {
				String sqlStatement = "SELECT Stid FROM Student";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					ids.add(result.getInt(1));
			} catch(Exception sql) {}
			
			idList = new JComboBox(ids.toArray());
			idList.setBounds(240, 113, 50, 20);
			add(idList);
				
			searchButton = new JButton("Search");
			searchButton.setBounds(165, 175, 90, 25);
			searchButton.addActionListener(new SearchStudentListener());
			add(searchButton);

		}
		
		/** 
		 * This action listener class responds to the "Search" button
		 * 
		 * If the user is using the panel to search grades by student,
		 * then it calls the method to display the selected student's grades
		 * 
		 * If the user is using panel just to search a student's info
		 * then it calls the method which displays the ViewStudent Panel
		 */
		
		private class SearchStudentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(forGrades)
					displayViewStuGradesPanel((Integer)idList.getSelectedItem());
				else
					displayViewStudentPanel((Integer)idList.getSelectedItem(), forEnrollment);
			}
		}
		
	}

	private class CreateCoursePanel extends JPanel {
		private JLabel header;
		private JTextField textField;
		private JLabel[] labels;
		private JComboBox instr;
		private JComboBox dept;
		
		private JButton saveButton;
		private JButton cancelButton;
	
		private int cid;
		
		public CreateCoursePanel() {
			setLayout(null);
			
			cid = 0;
			
			textField = new JTextField();
			labels = new JLabel[3];
			
			header = new JLabel("CREATE NEW COURSE");
			header.setBounds(160, 25, 150, 25);
			add(header);
	
			String[] labelText = {"Course Name:", "Department: ", "Instructor: "};
			
			for(int i = 0; i < labels.length; i++) {				
				labels[i] = new JLabel(labelText[i]);
				labels[i].setBounds(100, 70 + 30*i, 150, 25);
				add(labels[i]);
			}
			
			textField = new JTextField(20);
			textField.setBounds(200, 70, 150, 25);
			add(textField);
			
			ArrayList<String> deptNames = new ArrayList<>();
			
			try {
				String sqlStatement = "SELECT * FROM Department";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					deptNames.add(result.getInt(1) + " | " + result.getString(2));
			} catch(Exception sql) {}
			
			dept = new JComboBox(deptNames.toArray());
			
			dept.setSelectedIndex(-1);
			dept.addActionListener(new selectedDeptListener());
			dept.setBounds(200, 100, 150, 25);
			add(dept);			
			
			instr = new JComboBox();
			instr.setBounds(200, 130, 150, 25);
			add(instr);
	
			saveButton = new JButton("Save");
			saveButton.setBounds(120, 200, 90, 25);
			saveButton.addActionListener(new SaveCourseListener());
			add(saveButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setBounds(220, 200, 90, 25);
			cancelButton.addActionListener(new CancelListener());
			add(cancelButton);

		}
		
		public void setCid(int id) {
			cid = id;
		}
		
		public void setTextFields(String[] info) {
			textField.setText(info[1]);
			dept.setSelectedItem(info[2]);
			instr.setSelectedItem(info[3]);
		}
		
		public void setEditHeader() {
			header.setText("EDIT COURSE");
			header.setBounds(180, 25, 150, 25);
		}
		
		private class selectedDeptListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				instr.removeAllItems();

				try {
					String sqlStatement = "SELECT Iid, Name FROM Instructor WHERE Did = " + (dept.getSelectedIndex() + 1);
					System.out.println(sqlStatement);
					ResultSet result = myStmt.executeQuery(sqlStatement);
					while(result.next())
						instr.addItem(result.getInt(1) + " | " + result.getString(2));
				} catch(Exception sql) {}
			}
		}
		
		/**
		 * The following action listener class responds to "Save" button
		 * It writes the info in the text fields to a binary file
		 * Then, it calls the method that displays the View Course Panel
		 */
		
		private class SaveCourseListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				int did;
				if(dept.getSelectedItem() == null)
					did = 0;
				else
					did = dept.getSelectedIndex() + 1;
		
				int iid;
				if(instr.getSelectedItem() == null)
					iid = 0;
				else
					iid = Integer.parseInt(((String)instr.getSelectedItem()).substring(0,1));
				
				
				try {
					//Note: cid = 0 when a new course is being created. 
					//Cid is not 0 when course is being edited.
					if(Integer.valueOf(cid) == 0) {
						String sqlStatement = "INSERT INTO Course (cid, cname, did, iid) VALUES (" + cid;
						sqlStatement += ", '" + textField.getText() + "', ";
						sqlStatement += did + " , " + iid + ")";
						System.out.println(sqlStatement);
						
						myStmt.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
						ResultSet result = myStmt.getGeneratedKeys();
						while(result.next()) {
							System.out.println(result.getInt(1));
							cid = result.getInt(1);
						}
					}
					else {						
						String sqlStatement = "UPDATE Course SET Cname = '" + textField.getText() + "', Did = " + did + ", Iid = " + iid;
						sqlStatement += " WHERE Cid = " + cid;
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement);
					}

				}	catch(Exception sqlError) {System.out.println(sqlError.getMessage());}
				
				displayViewCoursePanel(cid, false);

			}
		}
		
		/**
		 * The following action listener class responds to the "Cancel" button
		 * It calls the method to display the original Menu Panel
		 * A course is not created and information in the textfields aren't written to the file
		 */
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}

	}

	private class ViewCoursePanel extends JPanel {
		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton button1;
		private JButton button2;
		
		private int cid;
		String[] cinfo;
		
		public ViewCoursePanel(int id, boolean forEnrollment) {
			setLayout(null);
			
			cid = id;
			cinfo = new String[4];

			textFields = new JTextField[4];
			labels = new JLabel[4];
			
			header = new JLabel("COURSE INFO");
			header.setBounds(180, 25, 150, 25);
			add(header);
			
			try {
				//Get Course Info from database to display
				String sqlStatement = "SELECT * FROM Course WHERE Cid = " + id;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				result.next();

				//Get Instructor and Department Name to go along did and iid
				int did = result.getInt(3);
				int iid = result.getInt(4);

				cinfo[0] = cid + "";
				cinfo[1] = result.getString(2);
				
				if(did == 0)
					cinfo[2] = "No Department Selected";
				else {
					sqlStatement = "SELECT Name FROM Department WHERE Did = " + did;
					System.out.println(sqlStatement);
					result = myStmt.executeQuery(sqlStatement);
					result.next();
					cinfo[2] = did + " | " + result.getString(1);
				}
				
				if(iid == 0)
					cinfo[3] = "No Instructor Selected";
				else {
					sqlStatement = "SELECT Name FROM Instructor WHERE Iid = " + iid;
					System.out.println(sqlStatement);
					result = myStmt.executeQuery(sqlStatement);
					result.next();
					cinfo[3] = iid + " | " + result.getString(1);
				}

				String[] labelText = {"Course ID", "Course Name: ", "Department: ", "Instructor: "};
				
				for(int i = 0; i < textFields.length; i++) {
					textFields[i] = new JTextField(20);
					textFields[i].setBounds(200, 70 + 30*i, 150, 25);
					textFields[i].setEditable(false);
					textFields[i].setText(cinfo[i]);
					add(textFields[i]);

					labels[i] = new JLabel(labelText[i]);
					labels[i].setBounds(100, 70 + 30*i, 150, 25);
					add(labels[i]);
				}
			
			}	catch(Exception sql) {System.out.println(sql.getMessage());}
			
			if(forEnrollment) {
				button1 = new JButton("Continue");
				button1.addActionListener(new ContinueEnrollmentListener());
				
				button2 = new JButton("Cancel");
				button2.addActionListener(new CancelListener());
			}
			else {
				button1 = new JButton("Edit");
				button2 = new JButton("Remove");
				button1.addActionListener(new EditCourseListener());
				button2.addActionListener(new RemoveListener());
			}
			
			button1.setBounds(120, 230, 90, 25);
			add(button1);

			button2.setBounds(220, 230, 90, 25);
			add(button2);
		}
		
		/**
		 * This Action Listener class responds to the "Edit" button
		 * It allows the user to edit the information of the course
		 */		
		private class EditCourseListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayCreateCoursePanel(cinfo);
			}
		}
		
		private class RemoveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {				
					String sqlStatement = "DELETE FROM Course WHERE Cid = " + cid;
					myStmt.executeUpdate(sqlStatement);
				} catch(Exception sql) {}
				displayHomePanel();
			}
		}
		
		/**
		 * This Action Listener class responds to the "Cancel" button
		 * 
		 * The button is only displayed if this panel is being used to
		 * display student information during the Create Enrollment process
		 * 
		 * It calls the method to display the original Menu Panel
		 * An enrollment is not created and nothing is written to the file
		 */		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}
		
		/**
		 * This Action Listener class responds to the "Continue" button
		 * 
		 * The button is only displayed if this panel is being used to
		 * display course information during the Create Enrollment process
		 */		
		private class ContinueEnrollmentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				cEnrollment.setCid(cid);
				displayCreateEnrollmentPanel();
			}
		}
		
	}

	private class SearchCoursePanel extends JPanel {
		private JLabel prompt;
		private JComboBox idList;
		private JButton searchButton;
		private JLabel prompt2;
		private JTextField yearField;
		
		private boolean forEnrollment;
		private boolean forGrades;
		private boolean forReport;

		public SearchCoursePanel(boolean enrollment, boolean grades) {
			forEnrollment = enrollment;
			forGrades = grades;
			forReport = false;
			
			setLayout(null);
			
			prompt = new JLabel("Select Course ID:");
			prompt.setBounds(128, 115, 113, 14);
			add(prompt);
			
			if(grades) {
				prompt2 = new JLabel("Year:");
				prompt2.setBounds(160, 142, 113, 14);
				prompt2.setVisible(false);
				add(prompt2);
				
				yearField = new JTextField();
				yearField.setBounds(205, 140, 60, 22);
				yearField.setVisible(false);
				add(yearField);
			}
			
			ArrayList<Integer> ids = new ArrayList<>();
			try {
				String sqlStatement = "SELECT Cid FROM Course";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					ids.add(result.getInt(1));
			} catch(Exception sql) {}

			
			idList = new JComboBox(ids.toArray());
			idList.setBounds(240, 113, 50, 20);
			add(idList);
				
			searchButton = new JButton("Search");
			searchButton.setBounds(165, 175, 90, 25);
			searchButton.addActionListener(new SearchCourseListener());
			add(searchButton);

		}
		
		public void setId(int id) {
			idList.setSelectedIndex(id - 1);
		}
		
		public void forReport() {
			forReport = true;
			prompt2.setVisible(true);
			yearField.setVisible(true);
		}
		
		/** 
		 * This action listener class responds to the "Search" button
		 * 
		 * If the user is using the panel to search grades by course,
		 * then it calls the method to display the selected course's grades
		 * 
		 * If the user is using panel just to search a course's info
		 * then it calls the method which displays the ViewCourse Panel
		 */		
		private class SearchCourseListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int index = (Integer)idList.getSelectedItem();
				if(index > 0) {
					if(forReport)
						displayReportPanel(index, yearField.getText());
					else if(forGrades)
						displayViewCGradesPanel(index);
					else
						displayViewCoursePanel(index, forEnrollment);
				}
			}
		}
	
	}

	private class CreateEnrollmentPanel extends JPanel {
		private JTextField textField;
		private JComboBox semBox;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton saveButton;
		private JButton cancelButton;
	
		private int eid;
		private int stid;
		private int cid;
		private String grade;
		
		public CreateEnrollmentPanel() {			
			setLayout(null);
			
			textField = new JTextField();
			labels = new JLabel[2];
			
			header = new JLabel("CREATE ENROLLMENT");
			header.setBounds(160, 25, 150, 25);
			add(header);
	
			String[] labelText = {"Year: ", "Semester: "};
			String[] semesters = {"Fall", "Winter", "Spring", "Summer"};
			
			for(int i = 0; i < labels.length; i++) {				
				labels[i] = new JLabel(labelText[i]);
				labels[i].setBounds(100, 70 + 30*i, 150, 25);
				add(labels[i]);
			}
			
			textField = new JTextField(20);
			textField.setBounds(200, 70, 150, 25);
			add(textField);
			
			semBox = new JComboBox(semesters);
			semBox.setBounds(200, 100, 150, 25);
			add(semBox);
	
			saveButton = new JButton("Save");
			saveButton.setBounds(120, 200, 90, 25);
			saveButton.addActionListener(new SaveEnrollmentListener());
			add(saveButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setBounds(220, 200, 90, 25);
			cancelButton.addActionListener(new CancelListener());
			add(cancelButton);
			
			eid = 0;
			grade = "*";
			
		}
		
		public void setEid(int id) {
			eid = id;
		}
		
		public void setStid(int id) {
			stid = id;
		}
		
		public void setCid(int id) {
			cid = id;
		}
		
		public void setGrade(String g) {
			grade = g;
		}
		
		public void setEditHeader() {
			header.setText("EDIT ENROLLMENT");
			header.setBounds(180, 25, 150, 25);
		}
		
		public void setTextFields(String year, String semester) {
			textField.setText(year);
			semBox.setSelectedItem(semester);
		}
		
		/**
		 * The following action listener class responds to "Save" button
		 * It writes the info in the text fields to a binary file
		 * Then, it calls the method that displays the View Enrollment Panel
		 */
		
		
		private class SaveEnrollmentListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {				
				try {
					//Note: eeid = 0 when a new course is being created. 
					//Eid is not 0 when course is being edited.
					if(Integer.valueOf(eid) == 0) {
						String sqlStatement = "INSERT INTO Enrollment (Eid, Stid, Cid, Year, Semester, Grade) VALUES (" + eid;
						sqlStatement += ", " + stid + ", " + cid + ", " + textField.getText();
						sqlStatement += ", '" + semBox.getSelectedItem() + "', '" + grade + "')";
						System.out.println(sqlStatement);
						
						myStmt.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
						ResultSet result = myStmt.getGeneratedKeys();
						while(result.next()) {
							System.out.println(result.getInt(1));
							eid = result.getInt(1);
						}
					}
					else {						
						String sqlStatement = "UPDATE Enrollment SET Year = '" + textField.getText() + "', Semester = '" + semBox.getSelectedItem() + "'";
						sqlStatement += " WHERE Eid = " + eid;
						System.out.println(sqlStatement);
						myStmt.executeUpdate(sqlStatement);
					}
				}	catch(Exception sql) {}
				
				displayViewEnrollmentPanel(eid);

			}
		}
		
		/**
		 * The following action listener class responds to the "Cancel" button
		 * It calls the method to display the original Menu Panel
		 * A course is not created and information in the textfields aren't written to the file
		 */
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}
	}

	private class ViewEnrollmentPanel extends JPanel {
		private JTextField[] textFields;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton editButton;
		
		private String[] eInfo;
		
		public ViewEnrollmentPanel(int id) {
			setLayout(null);
			
			textFields = new JTextField[6];
			labels = new JLabel[6];
			eInfo = new String[6];
			
			header = new JLabel("ENROLLMENT INFO");
			header.setBounds(175, 25, 150, 25);
			add(header);
			
			String[] labelText = {"Enrollment ID", "Student ID: ", "Course ID: ", "Year: ", "Semester: ", "Grade: "};
			
			try {
				//Get Enrollment Info from database to display
				String sqlStatement = "SELECT * FROM Enrollment WHERE Eid = " + id;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				result.next();

				//Get Instructor and Department Name to go along did and iid
				int stid = result.getInt(2);
				int cid = result.getInt(3);

				eInfo[0] = id + "";
				eInfo[3] = result.getInt(4) + "";
				eInfo[4] = result.getString(5);
				eInfo[5] = result.getString(6);

				sqlStatement = "SELECT Name FROM Student WHERE Stid = " + stid;
				System.out.println(sqlStatement);
				try {
					result = myStmt.executeQuery(sqlStatement);
					result.next();
					eInfo[1] = stid + " | " + result.getString(1);
				}
				catch(SQLException e) {
					eInfo[1] = stid + " *Deleted Student*";
				}

				sqlStatement = "SELECT Cname FROM Course WHERE Cid = " + cid;
				System.out.println(sqlStatement);
				try {
					result = myStmt.executeQuery(sqlStatement);
					result.next();
					eInfo[2] = cid + " | " + result.getString(1);
				}
				catch(SQLException e) {
					eInfo[2] = cid + " *Deleted Course*";
				}


				for(int i = 0; i < textFields.length; i++) {
					textFields[i] = new JTextField(20);
					textFields[i].setBounds(200, 70 + 30*i, 150, 25);
					textFields[i].setEditable(false);
					textFields[i].setText(eInfo[i]);
					add(textFields[i]);
					
					labels[i] = new JLabel(labelText[i]);
					labels[i].setBounds(100, 70 + 30*i, 150, 25);
					add(labels[i]);
				}
			}	catch(Exception sql) {System.out.println(sql.getMessage());}
			

			editButton = new JButton("Edit");
			editButton.setBounds(180, 270, 90, 25);
			editButton.addActionListener(new EditEnrollmentListener());
			add(editButton);
		
			
		}
		
		/**
		 * This Action Listener class responds to the "Edit" button
		 * It allows the user to edit the information of the enrollment
		 */		
		private class EditEnrollmentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				cEnrollment = new CreateEnrollmentPanel();
				cEnrollment.setEditHeader();
				cEnrollment.setEid(Integer.parseInt(eInfo[0]));
				cEnrollment.setTextFields(eInfo[3], eInfo[4]);
				displayCreateEnrollmentPanel();
			}
		}
		
	}

	private class SearchEnrollmentPanel extends JPanel {
		private JLabel prompt;
		private JComboBox idList;
		private JButton searchButton;

		public SearchEnrollmentPanel() {			
			setLayout(null);
			
			prompt = new JLabel("Select Enrollment ID:");
			prompt.setBounds(110, 115, 125, 14);
			add(prompt);
			
			ArrayList<Integer> ids = new ArrayList<>();
			try {
				String sqlStatement = "SELECT Eid FROM Enrollment";
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
	
				while(result.next())
					ids.add(result.getInt(1));
			} catch(Exception sql) {}
			
			idList = new JComboBox(ids.toArray());
			idList.setBounds(240, 113, 50, 20);
			add(idList);
				
			searchButton = new JButton("Search");
			searchButton.setBounds(165, 175, 90, 25);
			searchButton.addActionListener(new SearchEnrollmentListener());
			add(searchButton);

		}
		
		/** 
		 * This action listener class responds to the "Search" button
		 * It calls the method which displays the ViewCourse Panel
		 */
		private class SearchEnrollmentListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayViewEnrollmentPanel((Integer)idList.getSelectedItem());
			}
		}
	}

	private class ViewGradesStuPanel extends JPanel {
		private int index;
		private int stid;
		
		private JTextField[] textFields;
		private JComboBox gradeSelect;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton nextButton;
		private JButton editButton;
		private JButton undoButton;
		
		ArrayList<Enrollment> enrollments;
		
		public ViewGradesStuPanel(int id) {
			stid = id;
			enrollments = new ArrayList<>();
			
			try {
				String sqlStatement = "SELECT * FROM Enrollment WHERE Stid = " + stid;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				
				while(result.next()) {
					enrollments.add(new Enrollment(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5), result.getString(6)));
				}
				
				if (enrollments.size() == 0) {
					header = new JLabel("This student was not enrolled in any courses, and therefore has no grades.");
					add(header, SwingConstants.CENTER);
				}
				else {
					setLayout(null);
					
					String[] validGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F", "*"};
					gradeSelect = new JComboBox(validGrades);
					
					textFields = new JTextField[3];
					labels = new JLabel[4];
					
					//load student, in order to get the student name from stid
					String name = new String();
					sqlStatement = "SELECT Name FROM Student WHERE Stid = " + stid;
					System.out.println(sqlStatement);
					try {
						ResultSet nameResult = myStmt.executeQuery(sqlStatement);
						nameResult.next();
						name = nameResult.getString(1);
					}
					catch(SQLException e) {
						name = "*Deleted Student*";
					}
					
					header = new JLabel("Grades for " + name + " - ID: " + stid);
					header.setBounds(130, 25, 200, 50);
					add(header);
					
					String[] labelText = {"Course: ", "Year: ", "Semester: ", "Grade: "};
					
					for(int i = 0; i < textFields.length; i++) {
						textFields[i] = new JTextField(20);
						textFields[i].setBounds(200, 70 + 30*i, 150, 25);
						textFields[i].setEditable(false);
						add(textFields[i]);
					}
					
					for(int i = 0; i < labels.length; i++) {
						labels[i] = new JLabel(labelText[i]);
						labels[i].setBounds(100, 70 + 30*i, 150, 25);
						add(labels[i]);
					}
					
					gradeSelect.setBounds(200, 160, 150, 25);
					gradeSelect.addActionListener(new GradeListener());
					add(gradeSelect);

					editButton = new JButton("Confirm Grade Change");
					editButton.setBounds(120, 200, 200, 25);
					editButton.addActionListener(new EditGradeListener());
					add(editButton);
					
					undoButton = new JButton("Undo");
					undoButton.setBounds(120, 230, 200, 25);
					undoButton.addActionListener(new UndoListener());
					add(undoButton);
					
					nextButton = new JButton("View Next Grade");
					nextButton.setBounds(120, 200, 200, 25);
					nextButton.addActionListener(new NextListener());
					add(nextButton);
					
					index = -1;
					nextGrade();
				}
				
			}	catch(Exception sql) {}
		
		}
		
		public void nextGrade() {
			index++;
			
			editButton.setVisible(false);
			undoButton.setVisible(false);
			nextButton.setVisible(true);
			
			if(index == enrollments.size() - 1) {
				nextButton.setText("Exit");
				nextButton.removeActionListener(nextButton.getActionListeners()[0]);
				nextButton.addActionListener(new ExitListener());
				//add(nextButton);
			}
			
			//load old course list
			String cname = new String();
			int cid = enrollments.get(index).getCid();
			
			try {
				String sqlStatement = "SELECT Cname FROM Course WHERE Cid = " + cid;
				System.out.println(sqlStatement);
				ResultSet courseResult = myStmt.executeQuery(sqlStatement);
				courseResult.next();
				cname = courseResult.getString(1);
			}
			catch(SQLException e) {
				cname = "*Deleted Course*";
			}
			

			textFields[0].setText(cname + " (ID: " + cid + ")");
			textFields[1].setText(enrollments.get(index).getYear() + "");
			textFields[2].setText(enrollments.get(index).getSemester());
			gradeSelect.setSelectedItem(enrollments.get(index).getGrade());
		}

		private class NextListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				nextGrade();
			}
		}

		private class EditGradeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String newGrade = (String)gradeSelect.getSelectedItem();
				enrollments.get(index).setGrade(newGrade);
				String sqlStatement = "UPDATE Enrollment SET Grade = '" + newGrade + "' WHERE Eid = " + enrollments.get(index).getEid();
				System.out.println(sqlStatement);
				try {
					myStmt.executeUpdate(sqlStatement);
					JOptionPane.showMessageDialog(new JFrame(), "Grade Changed!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				index--;
				nextGrade();
			}
		}
		
		private class UndoListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				index--;
				nextGrade();
			}
		}
		
		private class GradeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(!enrollments.get(index).getGrade().equals(gradeSelect.getSelectedItem())) {
					editButton.setVisible(true);
					undoButton.setVisible(true);
					nextButton.setVisible(false);
				}
			}
		}
		
		private class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}
	}

	private class ViewGradesCPanel extends JPanel {
		private int index;
		private int cid;
		
		private JTextField[] textFields;
		private JComboBox gradeSelect;
		private JLabel[] labels;
		private JLabel header;
		
		private JButton nextButton;
		private JButton editButton;
		private JButton undoButton;
		
		ArrayList<Enrollment> enrollments;
		
		public ViewGradesCPanel(int id) {
			cid = id;
			enrollments = new ArrayList<Enrollment>();
			
			try {
				String sqlStatement = "SELECT * FROM Enrollment WHERE Cid = " + cid;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				
				while(result.next()) {
					enrollments.add(new Enrollment(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5), result.getString(6)));
				}
				
				if (enrollments.size() == 0) {
					header = new JLabel("There are no students enrolled in this course.");
					add(header, SwingConstants.CENTER);
				}
				else {
					setLayout(null);
					
					String[] validGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F", "*"};
					gradeSelect = new JComboBox(validGrades);
					
					textFields = new JTextField[3];
					labels = new JLabel[4];
					
					//load course in order to get the course name from cid
					String name = new String();
					sqlStatement = "SELECT Cname FROM Course WHERE cid = " + cid;
					System.out.println(sqlStatement);
					try {
						ResultSet nameResult = myStmt.executeQuery(sqlStatement);
						nameResult.next();
						name = nameResult.getString(1);
					}
					catch(SQLException e) {
						name = "*Deleted Course*";
					}
					
					header = new JLabel("Grades for " + name + " - ID: " + cid);
					header.setBounds(130, 25, 200, 50);
					add(header);
					
					String[] labelText = {"Student: ", "Year: ", "Semester: ", "Grade: "};
					
					for(int i = 0; i < textFields.length; i++) {
						textFields[i] = new JTextField(20);
						textFields[i].setBounds(200, 70 + 30*i, 150, 25);
						textFields[i].setEditable(false);
						add(textFields[i]);
					}
					
					for(int i = 0; i < labels.length; i++) {
						labels[i] = new JLabel(labelText[i]);
						labels[i].setBounds(100, 70 + 30*i, 150, 25);
						add(labels[i]);
					}
					
					gradeSelect.setBounds(200, 160, 150, 25);
					gradeSelect.addActionListener(new GradeListener());
					add(gradeSelect);

					editButton = new JButton("Confirm Grade Change");
					editButton.setBounds(120, 200, 200, 25);
					editButton.addActionListener(new EditGradeListener());
					add(editButton);
					
					undoButton = new JButton("Undo");
					undoButton.setBounds(120, 230, 200, 25);
					undoButton.addActionListener(new UndoListener());
					add(undoButton);
					
					nextButton = new JButton("View Next Grade");
					nextButton.setBounds(120, 200, 200, 25);
					nextButton.addActionListener(new NextListener());
					add(nextButton);
					
					index = -1;
					nextGrade();
				}
			} catch(Exception e) {}
		}
		
		public void nextGrade() {
			index++;
			
			editButton.setVisible(false);
			undoButton.setVisible(false);
			nextButton.setVisible(true);
			
			if(index == enrollments.size() - 1) {
				nextButton.setText("Exit");
				nextButton.removeActionListener(nextButton.getActionListeners()[0]);
				nextButton.addActionListener(new ExitListener());
				//add(nextButton);
			}
			
			//load student
			String name = new String();
			int stid = enrollments.get(index).getStid();
			
			try {
				String sqlStatement = "SELECT Name FROM Student WHERE Stid = " + stid;
				System.out.println(sqlStatement);
				ResultSet courseResult = myStmt.executeQuery(sqlStatement);
				courseResult.next();
				name = courseResult.getString(1);
			}
			catch(SQLException e) {
				name = "*Deleted Student*";
			}

			textFields[0].setText(name + " (ID: " + stid + ")");
			textFields[1].setText(enrollments.get(index).getYear() + "");
			textFields[2].setText(enrollments.get(index).getSemester());
			gradeSelect.setSelectedItem(enrollments.get(index).getGrade());
		}
		
		private class NextListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				nextGrade();
			}
		}
			
		private class EditGradeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String newGrade = (String)gradeSelect.getSelectedItem();
				enrollments.get(index).setGrade(newGrade);
				String sqlStatement = "UPDATE Enrollment SET Grade = '" + newGrade + "' WHERE Eid = " + enrollments.get(index).getEid();
				System.out.println(sqlStatement);
				try {
					myStmt.executeUpdate(sqlStatement);
					JOptionPane.showMessageDialog(new JFrame(), "Grade Changed!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				index--;
				nextGrade();
			}
		}
		
		private class UndoListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				index--;
				nextGrade();
			}
		}
		
		private class GradeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(!enrollments.get(index).getGrade().equals(gradeSelect.getSelectedItem())) {
					editButton.setVisible(true);
					undoButton.setVisible(true);
					nextButton.setVisible(false);
				}
			}
		}
		
		private class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				displayHomePanel();
			}
		}
		
	}

	private class ReportPanel extends JPanel {
		private int year;
		private ArrayList<Enrollment> enrollments;
		
		private JTextArea info;
		private JLabel header;
		private JLabel header2;
		
		public ReportPanel(int cid, String y) {
			setLayout(null);
			if(y.equals(""))
				year = -1;
			else
				year = Integer.parseInt(y);

			enrollments = new ArrayList<Enrollment>();

			try {				
				String sqlStatement = "SELECT * FROM Enrollment WHERE Cid = " + cid;
				System.out.println(sqlStatement);
				ResultSet result = myStmt.executeQuery(sqlStatement);
				
				while(result.next()) {
					enrollments.add(new Enrollment(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5), result.getString(6)));
				}
				
				if(year >= 0) {
					for(int i = 1; i < enrollments.size(); i++) {
						if(enrollments.get(i).getYear() != year) {
							enrollments.remove(i);
							i--;
						}
					}
				}
			
				//load course in order to get the course name from cid
				String name = new String();
				sqlStatement = "SELECT Cname FROM Course WHERE cid = " + cid;
				System.out.println(sqlStatement);
				try {
					ResultSet nameResult = myStmt.executeQuery(sqlStatement);
					nameResult.next();
					name = nameResult.getString(1);
				}
				catch(SQLException e) {
					name = "*Deleted Course*";
				}
				
				if(year >= 0) {
					header2 = new JLabel("Year: " + year);
					header2.setBounds(180, 40, 200, 25);
					add(header2);
				}

				header = new JLabel("Grades for " + name + " (Course ID: " + cid + ")");
				header.setBounds(120, 20, 200, 25);
				add(header);
				
				String report = new String();

				for(int index = 0; index < enrollments.size(); index++) {
					int stid = enrollments.get(index).getStid();
					//load student in order to get the student name from stid
					sqlStatement = "SELECT Name FROM Student WHERE stid = " + enrollments.get(index).getStid();
					System.out.println(sqlStatement);
					try {
						ResultSet nameResult = myStmt.executeQuery(sqlStatement);
						nameResult.next();
						name = nameResult.getString(1) + " (ID: " + stid + ")\t\t";
					}
					catch(SQLException e) {
						name = "*Deleted Student* (ID: " + stid + ")\t";
					}
					
					report += name;
					report += "Grade: " + enrollments.get(index).getGrade() + "\n\n";
				}
				
				info = new JTextArea(report);
				info.setBounds(30, 80, 400, 200);
				info.setEditable(false);
				add(info);
			}	
			catch(Exception sql) {}

		}
		
	}
		
	
}
//end of GUI class

/**
 * This class is used for keeping track of enrollment objects when
 * managing grades.
 */

class Enrollment {
	private int eid;
    private int stid;
    private int cid;
    private int year;
    private String semester;
    private String grade;
    
    //This constructor assigns values to all variables that make enrollment data
    public Enrollment(int e, int id, int c, int y, String s, String g) {
        eid = e;
    	stid = id;
        cid = c;
        year = y;
        semester = s;
        grade = g;
    }

	public int getEid() {
    	return eid;
    }
    
    public int getStid() {
        return stid;
    }
    
    public int getCid() {
        return cid;
    }
    
    public int getYear() {
        return year;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String g) {
        grade = g;
    }
    
    public void setSemster(String s) {
    	semester = s;
    }
    
    public void setYear(int y) {
    	year = y;
    }
    
    public void setCid(int id) {
		cid = id;
	}
    
    //This method displays all the data of an enrollment
    public String toString() {
    	String output = "\nDisplaying Enrollement Info:"
		    			+ "\n\tEnrollment ID: " + eid
		    			+ "\n\tStudent ID: " + stid
		    			+ "\n\tCourse ID: " + cid
		    			+ "\n\tYear: " + year
		    			+ "\n\tSemester: " + semester
		    			+ "\n\tGrade: " + grade;
    	return output;
    }
    
    //This method displays certain data of enrollment
    //It utilized when the user wants to view grades
    public String[] gradeReportS() {
    	String[] info = {year + "", semester, grade};
    	return info;
    }
    
    public String[] getInfo() {
    	String[] info = {eid + "", stid + "", cid + "", year + "", semester, grade};
    	return info;
    }
    
}
