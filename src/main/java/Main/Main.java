package Main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Discord.ChannelCreate;
import Discord.GuildSearch;
import Listeners.Listeners;
import Listeners.ReactionListener;
import Listeners.onGuildMemberJoinListeners;
import Listeners.onGuildMemberUpdateListeners;
import Listeners.onGuildMemberUpdateNicknameListeners;
import Listeners.onSlashCommandInteractionListener;
import Listeners.onUserUpdateGlobalNameLisner;
import Listeners.onUserUpdateNameListeners;

/*
 * Author: Clinton
 * Date: July 11, 2023
 * Description of whole project: This is discord bot which removes any user who may have name which you are not happy with in your discord server.
 */

public class Main {

	//These shouldn't be hard coded in.
	public static final String TESTTOKEN = "PUT YOUR TEST SERVER TOKEN HERE";
	public static final String MAINTOKEN = "PUT YOUR MAIN TOKEN HERE";

	public static void main(String[] args) throws InterruptedException, IOException {
		JDA jda = JDABuilder.createDefault(MAINTOKEN)
				.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(new onGuildMemberJoinListeners(), new onGuildMemberUpdateListeners(),
						new onGuildMemberUpdateNicknameListeners(), new onSlashCommandInteractionListener(),
						new onUserUpdateGlobalNameLisner(), new onUserUpdateNameListeners(), new ReactionListener())
				.build().awaitReady();

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> GuildSearch.scanMembers(jda), 0, 12,
				TimeUnit.HOURS);

		new ChannelCreate().ensureImposterBotCategoryAndChannels(jda.getGuilds().get(0));
	}
}
