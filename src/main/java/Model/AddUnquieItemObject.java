package Model;

public class AddUnquieItemObject {

	// String data;
	String dbName;
	// object your adding to the db
	Object object;
	// Accepted embed String
	String accept;
	// declined embed String
	String declined;
	// name for delete
	String name;
	// value for delete
	String value;

	public AddUnquieItemObject() {
		super();
	}

	public AddUnquieItemObject(String dbName, Object object, String accept, String declined) {
		super();
		this.dbName = dbName;
		this.object = object;
		this.accept = accept;
		this.declined = declined;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getDeclined() {
		return declined;
	}

	public void setDeclined(String declined) {
		this.declined = declined;
	}

}
