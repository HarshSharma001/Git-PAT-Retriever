package mainPackage;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileSystemView;

import services.GitPATFileAccessServiceImpl;

/**
 * The main class of this application
 * 
 * @author Harsh Sharma
 *
 */
public class MainClass {

	/**
	 * method to - identify and set the default location of git PAT text file
	 * corresponding to the operating system
	 * 
	 * @return - nothing
	 */
	public static void identifyDefaultLocationsCorrespondingToOperatingSystem() {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.equals("Windows")) {
			GitPATFileAccessServiceImpl.defaultWindowsFileSystemLocation = FileSystemView.getFileSystemView()
					.getDefaultDirectory().getPath();
		} else if (operatingSystem.equals("Linux")) {
			GitPATFileAccessServiceImpl.defaultLinuxFileSystemLocation = FileSystemView.getFileSystemView()
					.getDefaultDirectory().getPath();
//			System.out.println(GitPATFileAccessServiceImpl.
//			defaultLinuxFileSystemLocation);
		}
		return;
	}

	/**
	 * method to - start the application and do some basic initiation processes to
	 * make the application up and running efficiently
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
		identifyDefaultLocationsCorrespondingToOperatingSystem();
		new GitPATRetreiverWindow();
		GitPATRetreiverWindow.initiateFrameWindow();
		GitPATRetreiverWindow.setButtonActions();
	}
}
