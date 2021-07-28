package services;

import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Concrete class for serving services to the whole application
 * 
 * @author Harsh Sharma
 *
 */
public class GitPATFileAccessServiceImpl implements GitPATFileAccessService {

	// the number of asterisks to be displayed
	int tokenLength = 0;

	// default directory of windows O.S.
	public static String defaultWindowsFileSystemLocation = "";

	// efault directory of linux O.S.
	public static String defaultLinuxFileSystemLocation = "";

	// the location where permanent text file will be saved..
	public static String location = "";

	/**
	 * method to - check whether a permanent file exists and if it has some valid
	 * content in it
	 * 
	 * @return String type value
	 */
	public String checkForPermanentFile(String location) {
		// Base Case
		if (location.equals("")) {
			return null;
		}
		String path = null;
		if (!location.equals("")) {
			try {
				File file = new File(location);
				if (file.exists()) {
					path = this.readFileContents(file);
				} else {
					path = location;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	/**
	 * method to - read contents of the given file instance
	 * 
	 * @param file - File type instance
	 * @return String type value
	 */
	public String readFileContents(File file) {
		String gitPATFilePath = null;
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String content = sc.nextLine();
				if (!content.isEmpty()) {
					gitPATFilePath = content;
					return gitPATFilePath;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gitPATFilePath;
	}

	/**
	 * method to retrieve Personal Access Token from stored file
	 * 
	 * @return String type value representing PAT access token
	 */
	@Override
	public String getPersonalAccessToken() {
		String path = this.checkForPermanentFile(location);
		if (path == null) {
			String windowsPath = GitPATFileAccessServiceImpl.defaultWindowsFileSystemLocation;
			String linuxPath = GitPATFileAccessServiceImpl.defaultLinuxFileSystemLocation;
			if (!windowsPath.equals("")) {
				windowsPath += "/Github PAT.txt";
				path = windowsPath;
			} else if (!linuxPath.equals("")) {
				linuxPath += "/Github PAT.txt";
				path = windowsPath;
			}
		}
		File file = new File(path);
		String keyword = "Personal Access Token : ";
		String personalAccessToken = null;
		try {
			if (file.exists()) {
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String data = sc.nextLine();
					if (data.contains(keyword)) {
						personalAccessToken = data;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Error : No such file exists !");
			}
		} catch (Exception e) {
			System.out.println("Error - No such file found !");
		}

		if (personalAccessToken != null) {
			this.tokenLength = personalAccessToken.substring(keyword.length()).length();
		}

		return personalAccessToken.substring(keyword.length());
	}

	@Override
	/**
	 * method to get asterisk formed string of PAT
	 * 
	 * @return String type value representing stream of asterisks
	 */
	public String getPATCorrespondingAsteriskValue() {
		String displayValue = "";
		while (this.tokenLength-- > 0) {
			displayValue += "*";
		}
		return displayValue;
	}

}
