package Listeners;

import Discord.CustomEmbedBuilder;
import Discord.EmbedFactory;
import Discord.ImposterChecker;
import Discord.Roles;
import Discord.Userlookup;
import EventService.ListenersService;
import Model.AddUnquieItemObject;
import Model.BannedUsernames;
import Model.WhiteListUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.io.IOException;

import DB.DatabaseSingleton;
import DB.Delete;

public class Listeners extends ListenerAdapter {

	public final String PREFIX = "!";

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		boolean isAdmin = new Roles().hasAdminPermission(event.getMember());
		String message = event.getMessage().getContentRaw();

		if (message.startsWith(PREFIX + "ban") && isAdmin)
			new ListenersService().addUsernameUid(event, message, "BannedUsernames", "ban",
					() -> new BannedUsernames(message.split(" ")[1], event.getGuild().getId()));
		else if (message.startsWith(PREFIX + "rban") && isAdmin)
			new ListenersService().removeUsernameUid(event, message, "BannedUsernames", "ban",
					() -> new BannedUsernames(message.split(" ")[1], event.getGuild().getId()), "username");
		else if (message.startsWith(PREFIX + "exception") && isAdmin)
			new ListenersService().addException(event, message);
		else if (message.startsWith(PREFIX + "rexception") && isAdmin)
			new ListenersService().removeException(event, message);
		else if (message.startsWith(PREFIX + "concern") && isAdmin)
			new ListenersService().addUsernameUid(event, message, "UsernameOfConcern", "concern",
					() -> new BannedUsernames(message.split(" ")[1], event.getGuild().getId()));
		else if (message.startsWith(PREFIX + "rconcern") && isAdmin)
			new ListenersService().removeUsernameUid(event, message, "UsernameOfConcern", "concern",
					() -> new BannedUsernames(message.split(" ")[1], event.getGuild().getId()), "username");

	}
}
