package Discord;

import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;

public class ChannelCreate {

	public void ensureImposterBotCategoryAndChannels(Guild guild) {
		List<Category> categories = guild.getCategoriesByName("imposter-bot", true);
		Category imposterBotCategory;
		if (categories.isEmpty()) {
			imposterBotCategory = guild.createCategory("imposter-bot").complete();
			Role everyoneRole = guild.getPublicRole();
			Role adminRole = new Roles().getOrCreateAdminRole(guild);
			createCategoryWithAdminView(imposterBotCategory, adminRole, everyoneRole);
		} else
			imposterBotCategory = categories.get(0);
		if (!channelExistsInCategory(imposterBotCategory, "imposter-logs"))
			imposterBotCategory.createTextChannel("imposter-logs").queue();
		if (!channelExistsInCategory(imposterBotCategory, "user-of-concern"))
			imposterBotCategory.createTextChannel("user-of-concern").queue();
	}

	private boolean channelExistsInCategory(Category category, String channelName) {
		return category.getTextChannels().stream().anyMatch(channel -> channel.getName().equalsIgnoreCase(channelName));
	}

	private void createCategoryWithAdminView(Category category, Role adminRole, Role everyoneRole) {
		category.upsertPermissionOverride(everyoneRole).deny(Permission.VIEW_CHANNEL).queue();
		category.upsertPermissionOverride(adminRole).grant(Permission.VIEW_CHANNEL).queue();
	}

}
