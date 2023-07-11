package Listeners;

import Discord.ImposterChecker;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildMemberUpdateListeners extends ListenerAdapter {
	
	@Override
	public void onGuildMemberUpdate(GuildMemberUpdateEvent event) {
		System.out.println("onGuildMemberUpdate");
		new ImposterChecker().checkUsernameKickFactory(event.getMember(), "[On Name update] User has been kicked because of username voilation.");
	}
}
