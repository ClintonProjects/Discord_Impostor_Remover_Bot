package DB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DB {

	private static final String FILE_EXTENSION = ".txt";
	private static final String FOLDER = "Database/";
	protected String fileName;
	private Path filePath;

	public DB(String fileName) {
		this.fileName = fileName;
		this.filePath = Paths.get(FOLDER + fileName + FILE_EXTENSION);
		createFileIfNotExists();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		this.filePath = Paths.get(FOLDER + fileName + FILE_EXTENSION);
	}

	public Path getFilePath() {
		return Paths.get(FOLDER + fileName + FILE_EXTENSION);
	}

	public boolean fileWrite(String txt) {
		try {
			Files.write(getFilePath(), txt.toString().getBytes());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return boolean if it written or not
		return false;
	}

	private void createFileIfNotExists() {
		if (!Files.exists(filePath)) {
			try {
				Files.createDirectories(filePath.getParent());
				Files.createFile(filePath);
				fileWrite("[]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<JsonNode> jsonStringToList(String jsonString) {
		List<JsonNode> jsonList = new ArrayList<>();
		try {
			JsonNode rootNode = new ObjectMapper().readTree(jsonString);
			if (rootNode != null && rootNode.isArray())
				for (JsonNode jsonNode : rootNode)
					jsonList.add(jsonNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonList;
	}

	public Object JsonNodeToObj(JsonNode node, Class<?> clazz) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Object object = objectMapper.treeToValue(node, clazz);
			return object;
		} catch (JsonProcessingException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
