package Discord;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;
import DB.DatabaseObjects;
import DB.DatabaseSingleton;
import Model.AddUnquieItemObject;
import Model.BannedUsernames;
import Model.WhiteListUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;

public class ImposterChecker {

	@SuppressWarnings("null")
	boolean checkIndividualUsername(Member member) {
		DatabaseObjects dbObj = DatabaseSingleton.getInstance();
		boolean exception = dbObj.search("WhiteList")
				.checkIfInDB(new WhiteListUser(member.getId(), member.getGuild().getId()));

		if (exception == true)
			return false;

		List<JsonNode> listOfNodes = dbObj.read("BannedUsernames").readJsonNodesFromFile();
		for (JsonNode i : listOfNodes) {
			BannedUsernames BU = (BannedUsernames) dbObj.db("BannedUsernames").JsonNodeToObj(i, BannedUsernames.class);
			if (BU.getServerId().equals(member.getGuild().getId())) {
				String name = member.getUser().getName().toLowerCase();
				String effname = member.getUser().getEffectiveName().toLowerCase();
				String nickName = member.getNickname() == null ? "" : member.getNickname().toLowerCase();
				String bannedUsername = BU.getUsername().toLowerCase();
				if (name.contains(bannedUsername) || effname.contains(bannedUsername)
						|| nickName.contains(bannedUsername))
					return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private void kickMember(Member member) {
		member.getUser().openPrivateChannel().queue(privateChannel -> {
			CustomEmbedBuilder builder = new CustomEmbedBuilder().setColor(Color.RED)
					.setTitle(member.getGuild().getName())
					.setDescription("You have been kicked due to username violation").setThumbnail(member.getUser())
					.setFooterWithTime("User kicked at ", null);
			privateChannel.sendMessageEmbeds(builder.build().build()).queue(message -> {
				member.kick("Username violation:").queue();
			});
		});
	}

	public void genericUserEvent(GenericUserUpdateEvent<String> event, String reason) {
		event.getJDA().getGuilds().forEach(guild -> {
			Member m = guild.retrieveMember(event.getUser()).complete();
			new ImposterChecker().checkUsernameKickFactory(m, reason);
		});
	}

	public void checkUsernameKickFactory(Member member, String reason) {
		if (checkIndividualUsername(member)) {
			kickMember(member);
			new EmbedFactory().kickEmbed(member, true, reason);
		}
	}

	public void addUsernameToDB(MessageReceivedEvent event, AddUnquieItemObject addUnquieItemObject) {
		boolean added = DatabaseSingleton.getInstance().create(addUnquieItemObject.getDbName())
				.insertUniqueObject(addUnquieItemObject.getObject());
		databaseEntry(added, event, addUnquieItemObject);
	}

	public void removeUsernameToDB(MessageReceivedEvent event, AddUnquieItemObject addUnquieItemObject) {
		boolean deleted = DatabaseSingleton.getInstance().delete(addUnquieItemObject.getDbName())
				.deleteEntrys(addUnquieItemObject.getObject());
		databaseEntry(deleted, event, addUnquieItemObject);
	}

	public void databaseEntry(boolean resultOfEntry, MessageReceivedEvent event,
			AddUnquieItemObject addUnquieItemObject) {
		if (resultOfEntry)
			new EmbedFactory().replyToMessageEmbed(addUnquieItemObject.getAccept(), Color.GREEN, event);
		else
			new EmbedFactory().replyToMessageEmbed(addUnquieItemObject.getDeclined(), Color.YELLOW, event);
	}
}
