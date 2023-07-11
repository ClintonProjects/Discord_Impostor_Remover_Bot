package Listeners;

import java.lang.reflect.Member;
import java.util.concurrent.TimeUnit;

import DB.DatabaseSingleton;
import DB.Read;
import Discord.Roles;
import EventService.ReactionListenerService;
import Model.UserOfConcernReact;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		//System.out.println("onMessageReactionAdd");
		new ReactionListenerService().ReactionBan(event);
	}
}
