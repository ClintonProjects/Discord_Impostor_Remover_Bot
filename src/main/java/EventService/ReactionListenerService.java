package EventService;

import java.util.concurrent.TimeUnit;

import DB.DatabaseSingleton;
import DB.Delete;
import DB.Read;
import Discord.EmbedFactory;
import Discord.Roles;
import Model.UserOfConcernReact;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class ReactionListenerService {

	public void ReactionBan(MessageReactionAddEvent event) {
		boolean isAdmin = new Roles().hasAdminPermission(event.getMember());

		if (!isAdmin | event.getUser().isBot())
			return;

		String emoji = event.getReaction().getEmoji().getName();
		MessageChannel channel = event.getJDA().getTextChannelById(event.getChannel().getId());
		Message message = channel.retrieveMessageById(event.getMessageId()).complete();
		Read read = DatabaseSingleton.getInstance().read("UserOfConcernReact");
		Delete delete = DatabaseSingleton.getInstance().delete("UserOfConcernReact");
		UserOfConcernReact messageData = read.retrieveObjectsFromFile(UserOfConcernReact.class).stream()
				.filter(i -> i.getMessageId().equalsIgnoreCase(event.getMessageId())).findFirst().get();

		if (emoji.equals("🔨") && messageData.getUserId() != null) {
			event.getGuild().retrieveMemberById(messageData.getUserId()).queue(member -> {
				User user = member.getUser();
				event.getGuild().ban(user, 0, TimeUnit.DAYS).reason("User of concern.").queue();
				message.delete().queue();
				new EmbedFactory().kickEmbed(member, false, "[Reaction] Mannual ban by user of concern");
				delete.deleteEntrys(messageData);
			});
		} else if (emoji.equals("🙋‍♂️") && messageData.getUserId() != null) {
			event.getGuild().retrieveMemberById(messageData.getUserId()).queue(member -> {
				member.kick("User of concern").queue();
				new EmbedFactory().kickEmbed(member, false, "[Reaction] Mannual kicked by user of concern");
				message.delete().queue();
				delete.deleteEntrys(messageData);
			});
			
		} else if (emoji.equals("❌")) {
			message.delete().queue();
			delete.deleteEntrys(messageData);
		}
	}
}
