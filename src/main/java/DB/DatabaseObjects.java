package DB;

import java.io.IOException;

public class DatabaseObjects {

	/*
	 * This maybe a strange design choice, so I want to explain it, The use of the
	 * 'static' keyword here ensures that these objects are instantiated only once.
	 * In Java, every time a new object of a class is created, all instance
	 * variables are initialized and recreated. However, with the 'static' keyword,
	 * these variables are initialized only once and shared among all objects of the
	 * class. This is beneficial in practice, especially when using the Singleton
	 * design pattern, as it guarantees a single instance of an object. Here, these
	 * objects should only ever be created once because the DatabaseObjects class is
	 * intended to be used as a Singleton.
	 */

	private static DB db;
	private static Delete delete;
	private static Search search;
	private static Create create;
	private static Read read;
	private static Update update;

	public DatabaseObjects() {
		// note this creates an empty text file.
		db = new DB("");
		delete = new Delete("");
		search = new Search("");
		create = new Create("");
		read = new Read("");
		update = new Update("");
	}

	public DB db(String database) {
		db.setFileName(database);
		return db;
	}

	public Delete delete(String database) {
		delete.setFileName(database);
		return delete;
	}

	public Search search(String database) {
		search.setFileName(database);
		return search;
	}

	public Create create(String database) {
		create.setFileName(database);
		return create;
	}

	public Read read(String database) {
		read.setFileName(database);
		return read;
	}

	public Update update(String database) {
		update.setFileName(database);
		return update;
	}
}
