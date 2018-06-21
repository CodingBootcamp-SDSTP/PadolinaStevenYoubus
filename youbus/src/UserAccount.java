public class UserAccount
{
	final int ID;
	private String userID;
	private String email;
	private String username;
	private String password;
	private String type;
	private String sessionID;

	public UserAccount(int id, String userID, String email, String username, String password, String type) {
		ID = id;
		this.userID = userID;
		this.email = email;
		this.username = username;
		this.password = password;
		this.type = type;
	}

	public String getUserID() {
		return(userID);
	}

	public String getEmail() {
		return(email);
	}

	public String getUsername() {
		return(username);
	}

	public String getPassword() {
		return(password);
	}

	public String getType() {
		return(type);
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
