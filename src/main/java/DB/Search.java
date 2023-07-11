package DB;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Search extends Read {

	public Search(String fileName) {
		super(fileName);
	}

	public List<JsonNode> findJsonNodesByNameAndValue(String name, String value) {
		ObjectMapper objectMapper = new ObjectMapper();
		try (Stream<String> lines = Files.lines(getFilePath())) {
			return lines.map(line -> {
				try {
					JsonNode jsonNode = objectMapper.readTree(line);
					return (jsonNode.has(name) && jsonNode.get(name).asText().equals(value)) ? jsonNode : null;
				} catch (JsonProcessingException e) {
					return null; // Ignore invalid JSON lines
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public boolean checkIfInDB(Object object) {
		JsonNode node = new ObjectMapper().valueToTree(object);
		String read = new Read(fileName).readFormattedFileContents();
		List<JsonNode> itemsInDB = jsonStringToList(read).stream().filter(i -> i.equals(node))
				.collect(Collectors.toList());
		return !itemsInDB.isEmpty();
	}

	public List<JsonNode> findMatchingJsonNodes(List<JsonNode> jsonNodes, JsonNode targetNode) {
		return jsonNodes.stream().filter(jsonNode -> jsonNode.equals(targetNode)).collect(Collectors.toList());
	}

}
