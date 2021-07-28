package services;

/**
 * Abstract class for serving services to the whole application
 * 
 * @author Harsh Sharma
 *
 */
public interface GitPATFileAccessService {

	/**
	 * method to retrieve Personal Access Token from stored file
	 * 
	 * @return String type value representing PAT access token
	 */
	public String getPersonalAccessToken();

	/**
	 * method to get asterisk formed string of PAT
	 * 
	 * @return String type value representing stream of asterisks
	 */
	public String getPATCorrespondingAsteriskValue();
}
