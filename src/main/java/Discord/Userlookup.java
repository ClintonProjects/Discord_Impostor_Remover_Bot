package Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.RestAction;

public class Userlookup {

	public void userLookUpHandler(String message, SlashCommandInteractionEvent event) {
		String id = message.split(" ")[2];
		System.out.println(id);
		JDA jda = event.getJDA();
		RestAction<User> retrieveUser = jda.retrieveUserById(id);
		retrieveUser.queue((user) -> handleUserLookup(event, user), (failure) -> {
			if (failure instanceof ErrorResponseException) {
				ErrorResponseException exception = (ErrorResponseException) failure;
				if (exception.getErrorResponse().getCode() == 10013) {
					event.getChannel().sendMessage("No user found with the ID " + id).queue();
					return;
				}
			}
			event.getChannel().sendMessage("Failed to retrieve user information.").queue();
		});
	}

	private void handleUserLookup(SlashCommandInteractionEvent event, User user) {
		CustomEmbedBuilder customEmbed = new EmbedFactory().createUserEmbed(user, null, null, "User information");
		event.getChannel().sendMessageEmbeds(customEmbed.build().build()).queue();
	}
}
