package Model;

public class WhiteListUser {

	String uniqueId;
	String serverId;

	public WhiteListUser() {

	}

	public WhiteListUser(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public WhiteListUser(String uniqueId, String serverId) {
		this.uniqueId = uniqueId;
		this.serverId = serverId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
