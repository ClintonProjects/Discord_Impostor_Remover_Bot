package DB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Create extends DB {

	public Create(String fileName) {
		super(fileName);
	}

	public synchronized void addObjectToJsonArray(Object object) {
		JsonNode node = new ObjectMapper().valueToTree(object);
		List<JsonNode> itemsInDB = jsonStringToList(new Read(fileName).readFormattedFileContents());
		itemsInDB.add(node);
		fileWrite(itemsInDB.toString());
	}

	public synchronized boolean insertUniqueObject(Object object) {
		JsonNode node = new ObjectMapper().valueToTree(object);
		String read = new Read(fileName).readFormattedFileContents();
		List<JsonNode> itemsInDB = new Search(fileName).findMatchingJsonNodes(jsonStringToList(read), node);
		if (itemsInDB.isEmpty()) {
			addObjectToJsonArray(object);
			return true;
		}
		return false;
	}
}
