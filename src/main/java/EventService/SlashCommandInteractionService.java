package EventService;

import java.awt.Color;

import Discord.CustomEmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashCommandInteractionService {

	public void commands(SlashCommandInteractionEvent event) {
		event.deferReply().queue();
		event.getHook().sendMessageEmbeds(new CustomEmbedBuilder().setTitle("Commands List").setColor(Color.YELLOW)
				.addField("/lookup <UID>", "Allows you to find information about a user.", false)
				.addField("!ban <username>",
						"This will ban any user containing the username 'cat'. For example, it would ban all users with 'cat' in their name. **(Admin Only)**",
						false)
				.addField("!rban <username>",
						"Removes a ban from a user previously banned using the '!ban' command. **(Admin Only)**", false)
				.addField("!exception <discord unique id>",
						"Allows you to add an exception for a user, so that if they have a specific UID, they will not be banned. **(Admin Only)**",
						false)
				.addField("!rexception <discord unique id>",
						"Removes an exception for a user previously added using the '!exception' command. **(Admin Only)**",
						false)
				.addField("!concern <username>",
						"Add a user to the users of concern (non-auto ban list). **(Admin Only)**",
						false)
				.addField("!rconcern <username>",
						"Removes a user to the users of concern (non-auto ban list). **(Admin Only)**",
						false)
				.setAuthor(event.getJDA().getSelfUser(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
				.setImage("https://i.ytimg.com/vi/KQBdRuoMQBc/maxresdefault.jpg")
				.setFooterWithTime("Commands ", event.getUser()).build().build()).queue();
	}

}
