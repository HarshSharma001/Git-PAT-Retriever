package services;

public interface GitPATFileAccessService {
	
	/**
	 * method to retrieve Personal Access Token from stored file
	 * @return
	 */
	public String getPersonalAccessToken();
	
	/**
	 * method to get asterisk formed string of PAT
	 * @return
	 */
	public String getPATCorrespondingAsteriskValue();
}
