package Discord;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class GuildSearch {

	// Retrieve all members from a JDA instance
	public static ArrayList<Member> getAllMembers(JDA jda) {
		ArrayList<Member> members = new ArrayList<>();
		// Iterate over each guild and load its members
		for (Guild guild : jda.getGuilds())
			guild.loadMembers().get().forEach(member -> {
				members.add(member);
			});
		return members;
	}

	// Scan members and perform checks
	public static void scanMembers(JDA jda) {
		ImposterChecker imposterChecker = new ImposterChecker();
		// Iterate over each member obtained from getAllMembers
		for (Member member : getAllMembers(jda)) {
			// Check if the member's username violates any rules
			if (imposterChecker.checkIndividualUsername(member)) {
				// Check if the member has a higher role than the bot and is not a bot itself
				if (!new Roles().hasHigherRoleThenBot(member) && !member.getUser().isBot()) {
					// Perform additional checks on the suspicious member
					imposterChecker.checkUsernameKickFactory(member, "[Server Scan] - User been kicked because username voilation");
				}
			}
		}
	}
}
