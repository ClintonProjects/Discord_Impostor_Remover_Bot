package Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import java.awt.*;
import java.time.Instant;

public class CustomEmbedBuilder {

	private final EmbedBuilder builder;

	public CustomEmbedBuilder() {
		builder = new EmbedBuilder();
	}

	public CustomEmbedBuilder setTitle(String title) {
		builder.setTitle(title);
		return this;
	}

	public CustomEmbedBuilder setDescription(String description) {
		builder.setDescription(description);
		return this;
	}

	public CustomEmbedBuilder setColor(Color color) {
		builder.setColor(color);
		return this;
	}

	public CustomEmbedBuilder addField(String name, String value, boolean inline) {
		builder.addField(name, value, inline);
		return this;
	}

	public CustomEmbedBuilder setAuthor(User user, String url) {
		String name = user.getName();
		String iconUrl = user.getEffectiveAvatarUrl();
		builder.setAuthor(name, url, iconUrl);
		return this;
	}

	public CustomEmbedBuilder setFooter(String text, User user) {
		String iconUrl = user.getEffectiveAvatarUrl();
		builder.setFooter(text, iconUrl);
		return this;
	}

	// New footer method that automatically includes the current time
	public CustomEmbedBuilder setFooterWithTime(String text, User user) {
		String url = null;
		if (user != null)
			url = user.getEffectiveAvatarUrl();

		String timeText = text + " | Created at: " + Instant.now();
		builder.setFooter(timeText, url != null ? url : null);
		return this;
	}

	public CustomEmbedBuilder setThumbnail(User user) {
		String url = user.getEffectiveAvatarUrl();
		builder.setThumbnail(url);
		return this;
	}

	public CustomEmbedBuilder setImage(String url) {
		builder.setImage(url);
		return this;

	}
	public EmbedBuilder build() {
		return builder;
	}
}
