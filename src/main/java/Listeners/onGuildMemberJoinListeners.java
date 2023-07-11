package Listeners;

import Discord.ImposterChecker;
import EventService.JoinListenersService;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildMemberJoinListeners extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		new ImposterChecker().checkUsernameKickFactory(event.getMember(),
				"[On Join] User has been kicked because of username voilation.");
		new JoinListenersService().userOfConcern(event);
	}
}


