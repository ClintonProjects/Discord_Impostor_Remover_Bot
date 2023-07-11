package DB;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.BannedUsernames;

public class Delete extends DB {

	public Delete(String fileName) {
		super(fileName);
	}

	public boolean deleteEntrys(Object object) {
		if (!new Search(fileName).checkIfInDB(object))
			return false;
		JsonNode node = new ObjectMapper().valueToTree(object);
		String read = new Read(fileName).readFormattedFileContents();
		List<JsonNode> itemsInDB = jsonStringToList(read).stream().filter(i -> !i.equals(node))
				.collect(Collectors.toList());
		return fileWrite(itemsInDB.toString());

	}
}