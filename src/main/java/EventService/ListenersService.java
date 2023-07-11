package EventService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import DB.DatabaseSingleton;
import DB.Delete;
import DB.Read;
import DB.Search;
import Discord.ImposterChecker;
import Model.AddUniqueItemObjectBuilder;
import Model.AddUnquieItemObject;
import Model.BannedUsernames;
import Model.WhiteListUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ListenersService {

	public <T> void addUsernameUid(MessageReceivedEvent event, String message, String db, String nameList,
			Supplier<T> supplier) {
		T identifier = supplier.get();
		AddUnquieItemObject addUnquieItemObject = new AddUnquieItemObject(db, identifier,
				"The user " + message.split(" ")[1] + " has been added to the " + nameList + " list for this server.",
				"This user is already on the " + nameList + " list for this server.");
		new ImposterChecker().addUsernameToDB(event, addUnquieItemObject);
	}

	public <T> void removeUsernameUid(MessageReceivedEvent event, String message, String db, String nameList,
			Supplier<T> supplier, String entryToDelete) {
		T identifier = supplier.get();
		AddUnquieItemObject addUniqueItem = new AddUniqueItemObjectBuilder.Builder().withDbName(db)
				.withObject(identifier)
				.withAccept("The user " + message.split(" ")[1] + " has been removed from the " + nameList + " list.")
				.withDeclined("The user " + message.split(" ")[1] + " was not found for the " + nameList + " list.")
				.withName(entryToDelete).withValue(message.split(" ")[1]).build();
		new ImposterChecker().removeUsernameToDB(event, addUniqueItem);
	}

	public void addException(MessageReceivedEvent event, String message) {
		if (Pattern.matches(".*\\d+.*", message.split(" ")[1]))
			addUsernameUid(event, message, "WhiteList", "white",
					() -> new WhiteListUser(message.split(" ")[1], event.getGuild().getId()));
		else
			event.getChannel().sendMessage("You must enter a vaild discord unique id").queue();
	}

	public void removeException(MessageReceivedEvent event, String message) {
		if (Pattern.matches(".*\\d+.*", message.split(" ")[1]))
			removeUsernameUid(event, message, "WhiteList", "concern",
					() -> new WhiteListUser(message.split(" ")[1], event.getGuild().getId()), "uniqueId");
		else
			event.getChannel().sendMessage("You must enter a vaild discord unique id").queue();
	}

}
