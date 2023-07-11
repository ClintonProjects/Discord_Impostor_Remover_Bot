package DB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.BannedUsernames;
import Model.UserOfConcernReact;

public class Read extends DB {

	public Read(String fileName) {
		super(fileName);
	}

	public synchronized String readFormattedFileContents() {
		Path filePath = getFilePath();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return Files.lines(filePath).map(line -> {
				try {
					JsonNode jsonNode = objectMapper.readTree(line);
					return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
				} catch (JsonProcessingException e) {
					return line;
				}
			}).collect(Collectors.joining("\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public synchronized List<JsonNode> readJsonNodesFromFile() {
		String read = new Read(fileName).readFormattedFileContents();
		List<JsonNode> itemsInDB = jsonStringToList(read).stream().collect(Collectors.toList());
		return itemsInDB;
	}

	public synchronized <T> List<T> retrieveObjectsFromFile(Class<T> objectType) {
		List<T> objects = new ArrayList<>();
		for (JsonNode jsonNode : readJsonNodesFromFile()) {
			T object = objectType
					.cast(DatabaseSingleton.getInstance().db(fileName).JsonNodeToObj(jsonNode, objectType));
			if (object != null)
				objects.add(object);
		}
		return objects;
	}
}
