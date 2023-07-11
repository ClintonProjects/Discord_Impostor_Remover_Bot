package Listeners;

import java.util.List;

import Discord.ImposterChecker;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;

public class onGuildMemberUpdateNicknameListeners extends Listeners {

	@Override
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
		System.out.println("onGuildMemberUpdateNickname");
		new ImposterChecker().checkUsernameKickFactory(event.getMember(), "[On Join] User has been kicked because of username voilation.");
	}

}


