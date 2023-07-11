package Model;

public class UserOfConcernReact {

	private String messageId;
	private String userId;
	private String serverId;
	
	public UserOfConcernReact() {
		
	}

	public UserOfConcernReact(String messageId, String userId, String serverId) {
		super();
		this.messageId = messageId;
		this.userId = userId;
		this.serverId = serverId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
}
