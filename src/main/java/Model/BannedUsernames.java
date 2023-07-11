package Model;

public class BannedUsernames {

	public String username;
	public String serverId;

	public BannedUsernames() {
	}

	public BannedUsernames(String username, String serverId) {
		this.username = username;
		this.serverId = serverId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
