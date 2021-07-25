package mainPackage;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import services.GitPATFileAccessService;
import services.GitPATFileAccessServiceImpl;

public class GitPATRetreiverWindow {
	
	//the main-frame window
	static JFrame mainWindow = new JFrame("Git PAT Retriever");
	
	//the text field 
	static JTextField personalAccessToken = new JTextField(22);
	
	//button to copy the text
	static JButton copyPAT = new JButton("Copy To Clipboard");
	
	//the actual "personal access token value"
	static String tokenValue = null;
	
	/**
	 * Default constructor to fetch and set the PAT value ready
	 * to display and use in the main frame window using the 
	 * service layer
	 */
	public GitPATRetreiverWindow() {
		GitPATFileAccessService PATService = new GitPATFileAccessServiceImpl();
		tokenValue = PATService.getPersonalAccessToken();
		personalAccessToken.setText(PATService.getPATCorrespondingAsteriskValue());
	}
	
	
	/**
	 * method to - initialize main frame and it's components
	 */
	public static void initiateFrameWindow() {
		personalAccessToken.setEditable(false);
		mainWindow.setResizable(false);
		mainWindow.setLocation(200, 100);
		mainWindow.setVisible(true);
		mainWindow.setSize(300, 120);
		mainWindow.setLayout(new FlowLayout());
		mainWindow.setDefaultCloseOperation(3);
		
		mainWindow.add(personalAccessToken);
		mainWindow.add(copyPAT);
		return;
	}
	
	
	/**
	 * method to - setting up the button to copy the token value
	 */
	public static void setButtonActions() {
		copyPAT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				copyToClipboard();
			}
		});
	}
	
	
	/**
	 * method to - copying the fetched token value to the clipboard
	 */
	public static void copyToClipboard() {
		StringSelection stringSelection = new StringSelection (tokenValue);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
		return;
	}
}
