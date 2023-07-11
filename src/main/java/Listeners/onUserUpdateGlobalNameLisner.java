package Listeners;

import Discord.ImposterChecker;
import net.dv8tion.jda.api.events.user.update.UserUpdateGlobalNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onUserUpdateGlobalNameLisner extends ListenerAdapter {

	@Override
	public void onUserUpdateGlobalName(UserUpdateGlobalNameEvent event) {
		System.out.println("onUserUpdateGlobalName");
		new ImposterChecker().genericUserEvent(event, "[On Name Change] User has been kicked because of username voilation.");
	}

}
