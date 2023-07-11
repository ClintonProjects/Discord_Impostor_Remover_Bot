package Discord;

import java.awt.Color;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EmbedFactory {

	public CustomEmbedBuilder createUserEmbed(User user, Member member, String requesterTag, String title) {
		String memberName = (member == null || member.getNickname() == null) ? "No server nickname found."
				: member.getNickname();

		CustomEmbedBuilder customEmbed = new CustomEmbedBuilder().setTitle(title).setColor(Color.YELLOW)
				.addField("Discord Mention:", user.getAsMention(), false).addField("Nickname: ", memberName, false)
				.addField("Discord Tag", user.getName(), true)
				.addField("Discord Display Name", user.getEffectiveName(), true)
				.addField("Unique ID", user.getId(), true).addField("Is Bot", String.valueOf(user.isBot()), true)
				.addField("Creation Date", user.getTimeCreated().toString(), true)
				.addField("In Current Guild", member != null ? "Yes" : "No", true)
				.setAuthor(user, user.getEffectiveAvatarUrl()) // set author with icon
				.setThumbnail(user) // get effective avatar (user's or default)
				.setFooterWithTime("Requested by: ", null); // no icon in footer
		return customEmbed;
	}

	@SuppressWarnings("null")
	public void kickEmbed(Member member, boolean auto, String reason) {
		// Get the current time
		Instant now = Instant.now();
		String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
				.format(now);
		// Chain the methods of the builder to build the embed
		CustomEmbedBuilder embed = new CustomEmbedBuilder().setTitle(reason).setColor(Color.RED)
				.setDescription("A user has been kicked: " + member.getUser().getName())
				.addField("User ID: ", member.getUser().getId(), false).setThumbnail(member.getUser())
				.addField("Auto: ", "" + auto, false);
				//.setFooterWithTime(banOrKick + " time: " + timestamp, null);
		// Send the embed to a specific channel
		member.getGuild().getTextChannelsByName("imposter-logs", false).get(0).sendMessageEmbeds(embed.build().build())
				.queue();
	}

	@SuppressWarnings("null")
	public void replyToMessageEmbed(String text, Color color, MessageReceivedEvent event) {
		User bot = event.getJDA().getSelfUser();
		CustomEmbedBuilder b = new CustomEmbedBuilder().setAuthor(bot, null).setColor(color).setDescription(text);
		event.getGuild().getTextChannelById(event.getChannel().getId()).sendMessageEmbeds(b.build().build()).queue();
	}

}
