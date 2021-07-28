package mainPackage;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import services.GitPATFileAccessService;
import services.GitPATFileAccessServiceImpl;

/**
 * Class to represent the GUI of the application and provides functionalities to
 * control the whole application via GUI
 * 
 * @author Harsh Sharma
 *
 */
public class GitPATRetreiverWindow {

	// the main-frame window
	static JFrame mainWindow = new JFrame("Git PAT Retriever");

	// browse your location button
	static JButton browseLocation = new JButton("Browse Your Text File");

	// the text field
	static JTextField personalAccessToken = new JTextField(22);

	// button to copy the text
	static JButton copyPAT = new JButton("Copy To Clipboard");

	// the actual "personal access token value"
	static String tokenValue = null;

	/**
	 * method to - initialize main frame and it's components
	 */
	public static void initiateFrameWindow() {
		personalAccessToken.setEditable(false);

		mainWindow.add(browseLocation);
		mainWindow.add(personalAccessToken);
		mainWindow.add(copyPAT);

		mainWindow.setResizable(false);
		mainWindow.setLocation(200, 100);
		mainWindow.setVisible(true);
		mainWindow.setSize(300, 150);
		mainWindow.setLayout(new FlowLayout());
		mainWindow.setDefaultCloseOperation(3);
		return;
	}

	/**
	 * method to - setting up the button to copy the token value
	 */
	public static void setButtonActions() {
		browseLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String gitPatFileLocation = null;
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.showOpenDialog(mainWindow);
					gitPatFileLocation = fileChooser.getSelectedFile().getAbsolutePath();
//					System.out.println(gitPatFileLocation);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(mainWindow,
							"Error : No file selected, kindly select an appropriate file!");
				}
				initiatePermanentFileProcess(gitPatFileLocation);
			}
		});

		copyPAT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String path1 = GitPATFileAccessServiceImpl.defaultWindowsFileSystemLocation;
				String path2 = GitPATFileAccessServiceImpl.defaultLinuxFileSystemLocation;
				String finalPath = null;
				if (!path1.equals("")) {
					finalPath = path1;
				} else if (!path2.equals("")) {
					finalPath = path2;
				}
				finalPath += "/Documents/PermanentPath.txt";
				GitPATFileAccessServiceImpl.location = finalPath;
				GitPATFileAccessService PATService = new GitPATFileAccessServiceImpl();
				try {
					tokenValue = PATService.getPersonalAccessToken();
					personalAccessToken.setText(PATService.getPATCorrespondingAsteriskValue());
					copyToClipboard();
				} catch (Exception e) {
					personalAccessToken.setText("No valid PAT token found...!");
				}
			}
		});
	}

	/**
	 * method to - check whether user have selected the git PAT file or not if yes
	 * then create the permanent file in the default directory otherwise do nothing
	 * 
	 * @param gitPatFileLocation
	 */
	public static void initiatePermanentFileProcess(String gitPatFileLocation) {
		if (gitPatFileLocation != null && !gitPatFileLocation.isEmpty()) {
			String operatingSystem = System.getProperty("os.name");
			if (operatingSystem.equals("Windows")) {
				savePermanentPathInAFile(gitPatFileLocation, gitPatFileLocation);
			} else if (operatingSystem.equals("Linux")) {
				savePermanentPathInAFile(gitPatFileLocation, gitPatFileLocation);
			}
		}
	}

	/**
	 * method to - save the permanent git file path in a text file in default
	 * directory of the corresponding operating system
	 * 
	 * @param destination - the default directory path of the operating system
	 * @param content     - the user's git PAT file location needs to be saved for
	 *                    future access
	 */
	public static void savePermanentPathInAFile(String destination, String content) {
		try {
			int index = destination.indexOf("Documents");
			destination = destination.substring(0, index + 9);
//			System.out.println("destination = "+destination);
			destination += "/PermanentPath.txt";
			GitPATFileAccessServiceImpl.location = destination;
			File file = new File(destination);
			// for precautions
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			if (file.exists()) {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				fw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow, "Error : While saving the PAT file location !");
		}
		return;
	}

	/**
	 * method to - copying the fetched token value to the clipboard
	 */
	public static void copyToClipboard() {
		StringSelection stringSelection = new StringSelection(tokenValue);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
		return;
	}
}
