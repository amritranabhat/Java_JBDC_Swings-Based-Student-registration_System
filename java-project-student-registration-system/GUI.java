import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;
import java.io.*;

/**
 * GUI class declares and manages a set of graphical user
 * interfaces .
 *
 *  Group Activity
 *   @author Nepa1 Batch10
 */

public class GUI extends JFrame
{
	/**
	 * This action command assigns the default size of the main frame.
	 */
	public static final Dimension FRAME_SIZE = new Dimension(1000,1000);
	/**
	 * This action command assigns minimum size of the main frame.
	 */
	public static final Dimension MIN_FRAME_SIZE = new Dimension(100,300);
	
	/**
	 * This action command assigns default distance of the main frame from the X origin.
	 */
	public static final Point FRAME_ORIGIN = new Point(450,250);
	 
	/**
	 * This action command is used to create a new student record array.
	 */
	public static final String NEW = "New";
	/**
	 * This action command is used to open a new file.
	 */
	public static final String OPEN = "Open";
	
	/**
	 * This action command is used to save to the current file.
	 */
	public static final String SAVE = "Save";
	
	/**
	 * This action command is used to specify a new save file.
	 */
	public static final String SAVE_AS = "Save As...";
	
	/**
	 * This action command is used to end the program.
	 */
	public static final String QUIT = "Quit";
	
	/**
	 * This action command is used to initiate the record creation process.
	 */
	public static final String ADD_STUDENT = "Add Student";
	
	/**
	 * This action command is used to initiate the student deletion process.
	 */
	public static final String DELETE_STUDENT = "Delete Student";
	

	
	/**
	 * This action command is used to initiate a heap sort on the students by name.
	 */
	public static final String SORT_NAME = "Sort by Name";
	
	/**
	 * This action command is used to initiate a heap sort on the students by ID.
	 */
	public static final String SORT_ID = "Sort by ID";
	


	/**
	 * This mode status is used while the system has no student records loaded.
	 */
	public static final int NO_STUDENT_DATA_MODE = 0;
	
	/**
	 * This mode status is used while an unsorted array is loaded.
	 */
	public static final int ARRAY_UNSORTED_MODE = 1;
	
	/**
	 * This mode status is used while a completely sorted array is loaded.
	 */
	public static final int ARRAY_SORTED_MODE = 2;
	
	/**
	 * Text fields used to display statistical information.
	 */
	private JTextField[] statsFields;
	
	/**
	 * The main display area for student information.
	 */
	private JTextArea displayArea;
	
	/**
	 * The menu items used in this GUI.
	 */
	private JMenuItem[][] items;
	
	/**
	 * The operation mode this GUI is currently in.
	 */
	private int mode;
	
	/**
	 * The constructor for this GUI.
	 */
	public GUI(EventListener listener)
	{
		Container contentPane;
		JMenuBar menuBar;
		JMenu[] menus;
		JScrollPane scrollPane;
		JPanel displayPanel;
		
		setSize(FRAME_SIZE);
		setMinimumSize(MIN_FRAME_SIZE);
		setResizable(false);
		setTitle("Student Registration System");
		setLocation(FRAME_ORIGIN);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener((WindowListener) listener);
		
		contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		// MENU BAR
		String[] menuNames = {"File", "Edit", "Process"};
		String[][] itemNames = {
			{"New", "Open", "Save", "Save As...", "Quit"},
			{"Add Student", "Delete Student"},
			{"Sort by Name", "Sort by ID"} };
		String[][] commands = {
			{NEW, OPEN, SAVE, SAVE_AS, QUIT},
			{ADD_STUDENT, DELETE_STUDENT},
			{SORT_NAME, SORT_ID} };
		
		assert menuNames.length == itemNames.length :
			"menuNames and itemNames do not have matching lengths.";
		assert itemNames.length == commands.length :
			"itemNames and commands do not have matching lengths.";
		for (int i = 0; i < itemNames.length; i++)
			assert itemNames[i].length == commands[i].length :
				"itemNames[" + i + "] and commands[" + i + "] do not have"
				+ " matching lengths.";
			
		menuBar = new JMenuBar();
		menus = new JMenu[menuNames.length];
		items = new JMenuItem[menus.length][];
		for (int i = 0; i < menus.length; i++)
		{
			menus[i] = new JMenu(menuNames[i]);
			items[i] = new JMenuItem[itemNames[i].length];
			for (int j = 0; j < items[i].length; j++)
			{
				items[i][j] = new JMenuItem(itemNames[i][j]);
				menus[i].add(items[i][j]);
				items[i][j].addActionListener((ActionListener) listener);
				items[i][j].setActionCommand(commands[i][j]);
				items[i][j].setVisible(true);
			} // end JMenuItem for loop
			menus[i].setVisible(true);
			menuBar.add(menus[i]);
		} // end JMenu for loop
		setJMenuBar(menuBar);
		//menuBar.setVisible(true);
		
		// DISPLAY AREA
		displayPanel = new JPanel();
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.LINE_AXIS));
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setFont(new Font("Courier", Font.PLAIN, 12));
		displayArea.setMinimumSize(new Dimension(400,50));
		scrollPane = new JScrollPane(displayArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayPanel.add(scrollPane);
		

		
		// FINAL ARRANGEMENT
		contentPane.add(displayPanel);
		displayPanel.setVisible(true);
		
		changeMode(NO_STUDENT_DATA_MODE);
		setVisible(true);
	} // end of constructor
	
	/**
	 * Getter method for the GUI's current display mode.
	 * @return The current mode, represented as an integer.
	 */
	public int getMode()
	{
		return mode;
	} // end of getMode()

	/**
	 * Changes the interface's accessibility depending on current requirements.
	 * @param newMode An integer representing the desired mode of operation.
	 */
	public void changeMode(int newMode)
	{
		setFocusHere();
		this.mode = newMode;
		if (mode == NO_STUDENT_DATA_MODE)
		{
			items[0][2].setEnabled(false);
			items[0][3].setEnabled(false);
			items[1][0].setEnabled(false);
			items[1][1].setEnabled(false);
			items[2][0].setEnabled(false);
			items[2][1].setEnabled(false);
                        displayArea.setText("");
		} else if (mode == ARRAY_UNSORTED_MODE) {
			items[0][2].setEnabled(true);
			items[0][3].setEnabled(true);
			items[1][0].setEnabled(true);
			items[1][1].setEnabled(true);
			items[2][0].setEnabled(true);
			items[2][1].setEnabled(true);

		} else if (mode == ARRAY_SORTED_MODE) {
			items[0][2].setEnabled(true);
			items[0][3].setEnabled(true);
			items[1][0].setEnabled(true);
			items[1][1].setEnabled(true);
			items[2][0].setEnabled(true);
			items[2][1].setEnabled(true);

		} // end else-if
	} // end of changeMode

        public void changeSortName() {
                        items[2][0].setEnabled(true);
			items[2][1].setEnabled(false);

        }
        
        public void changeSortID() {
                        items[2][0].setEnabled(true);
			items[2][1].setEnabled(true);

        }
               

	/**
	 * Enables the primary GUI and moves it to the front.
	 */
	public void setFocusHere()
	{
		setEnabled(true);
		toFront();
	} // end of setFocusHere
	
	/**
	 * Asks the user for an input file and returns it.
	 * @return The chosen file.
	 */
	public File selectOpenFile()
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogTitle("Select Input File");
		chooser.showOpenDialog(null);
		File f = new File(chooser.getSelectedFile(), "");
		
		return f;
	} // end of selectOpenFile
	
	/**
	 * Asks user to select a file to save to and returns it.
	 * @return The chosen file.
	 */
	public File selectSaveFile()
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogTitle("Select Save File");
		chooser.showSaveDialog(null);
		File f = new File(chooser.getSelectedFile(), "");

		return f;
	} // end of selectSaveFile
	
	/**
	 * Clears the statistics display.
	 */
	public void clearStats()
	{
		for (int i = 0; i < 4; i++)
			statsFields[0].setText("");
	} // end of clearStats
	

	
	/**
	 * Formats and displays incoming student information.
	 * @param s A table of student information.
	 */
	public void displayText(String[][] s)
	{
		displayArea.setText("");
		String temp;
		temp = String.format(" |%24s| |%30s| |%36s| |%25s| |%21s|  |%23s| |%14s| |%40s|",
			"Name", "ID", "Asso.Course","Phone no","City", "State", "Zipcode", "Registration Date");
		displayArea.append(temp + "\n");
		for (int i = 0; i < s.length; i++)
		{
			temp = String.format(" |%20s| |%20s| |%30s| |%20s| |%20s|  |%20s| |%10s| |%40s|",
				s[i][0], s[i][1], s[i][2], s[i][3], s[i][4], s[i][5], s[i][6], s[i][7]);
			displayArea.append(temp);
			if (i+1 != s.length)
				displayArea.append("\n");
		} // end for
	} // end of displayText

	public static void main(String[] args)
	{ System.out.println("This is not the main method, Please run RegistrationSystem.java");
	} // end of main
} // end of RegistrationSystemGUI