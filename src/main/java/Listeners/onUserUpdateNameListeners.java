package Listeners;

import java.util.List;

import Discord.ImposterChecker;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onUserUpdateNameListeners extends ListenerAdapter {
	
	@Override
	public void onUserUpdateName(UserUpdateNameEvent event) {
		//System.out.println("onUserUpdateName");
		new ImposterChecker().genericUserEvent(event, "[On Name Change] User has been kicked because of username voilation.");
	}
}