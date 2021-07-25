package services;

import java.io.File;
import java.util.Scanner;

public class GitPATFileAccessServiceImpl implements GitPATFileAccessService{
	
	int tokenLength = 0;

	/**
	 * method to retrieve Personal Access Token from stored file
	 * @return
	 */
	@Override
	public String getPersonalAccessToken() {
		File file = new File("/home/harsh/Documents/Github PAT.txt");
		String keyword = "Personal Access Token : ";
		String personalAccessToken = null;
		try {
			if(file.exists()) {
				Scanner sc = new Scanner(file);
				while(sc.hasNextLine()) {
					String data = sc.nextLine();
					if(data.contains(keyword)) {
						personalAccessToken = data;	
					}
				}
			}
		}catch(Exception e) {
			System.out.println("Error - No such file found !");
		}		
		
		this.tokenLength = personalAccessToken.substring(keyword.length()).length();
		return personalAccessToken.substring(keyword.length());
	}

	@Override
	public String getPATCorrespondingAsteriskValue() {
		String displayValue = "";
		while(this.tokenLength --> 0) {
			displayValue += "*";
		}
		return displayValue;
	}

}
