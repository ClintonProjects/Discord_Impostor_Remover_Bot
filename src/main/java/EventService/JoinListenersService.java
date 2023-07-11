package EventService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;

import DB.DatabaseObjects;
import DB.DatabaseSingleton;
import DB.Read;
import Discord.CustomEmbedBuilder;
import Discord.EmbedFactory;

import Model.BannedUsernames;
import Model.UserOfConcernReact;
import Model.WhiteListUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class JoinListenersService {

	public void userOfConcern(GuildMemberJoinEvent event) {
		List<String> exc = DatabaseSingleton.getInstance().read("WhiteList")
				.retrieveObjectsFromFile(WhiteListUser.class).stream()
				.filter(i -> i.getUniqueId().equals(event.getUser().getId())).map(WhiteListUser::getUniqueId)
				.collect(Collectors.toList());
		if (!exc.isEmpty())
			return;

		TextChannel channel = event.getGuild().getTextChannelsByName("user-of-concern", true).get(0);
		Read read = DatabaseSingleton.getInstance().read("UsernameOfConcern");
		// this is basically cleaner version of checkIndividualUsername.
		List<String> list = read.readJsonNodesFromFile().stream()
				.map(jsonNode -> (BannedUsernames) read.JsonNodeToObj(jsonNode, BannedUsernames.class))
				.filter(i -> event.getMember().getUser().getName().toLowerCase().contains(i.getUsername())
						|| event.getMember().getUser().getEffectiveName().toLowerCase().contains(i.getUsername())
						|| (event.getMember().getNickname() == null ? ""
								: event.getMember().getNickname().toLowerCase()).contains(i.getUsername()))
				.filter(i -> event.getGuild().getId().equalsIgnoreCase(i.getServerId()))
				.map(bannedUsernames -> bannedUsernames.getUsername()).collect(Collectors.toList());

		if (list.isEmpty())
			return;

		if (channel != null) {
			EmbedBuilder embedBuilder = new EmbedFactory().createUserEmbed(event.getUser(), event.getMember(),
					event.getJDA().getSelfUser().getName(), "User of concern has joined the server").build();
			channel.sendMessageEmbeds(embedBuilder.build()).queue(message -> {
				message.addReaction(Emoji.fromUnicode("🔨")).queue();
				message.addReaction(Emoji.fromUnicode("🙋‍♂️")).queue();
				message.addReaction(Emoji.fromUnicode("\u274C")).queue();
				UserOfConcernReact userOfConcernReact = new UserOfConcernReact(message.getId(), event.getUser().getId(),
						event.getGuild().getId());
				DatabaseSingleton.getInstance().create("UserOfConcernReact").insertUniqueObject(userOfConcernReact);
			});
		}
	}
}
