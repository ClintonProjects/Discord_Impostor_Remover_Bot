package DB;

public class DatabaseSingleton {

	private static DatabaseObjects INSTANCE;

	// double-checked locking , better performance then lazy, it's why I choice this
	// one,
	// I am aware this can be broken by reflection however.
	public static DatabaseObjects getInstance() {
		if (INSTANCE == null)
			synchronized (DatabaseSingleton.class) {
				if (INSTANCE == null)
					INSTANCE = new DatabaseObjects();
			}
		return INSTANCE;
	}
}
