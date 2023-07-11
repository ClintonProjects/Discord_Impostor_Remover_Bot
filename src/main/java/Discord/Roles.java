package Discord;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Roles {

	public boolean hasHigherRoleThenBot(Member member) {
		Guild guild = member.getGuild();
		Member selfMember = guild.getSelfMember(); // This represents the bot itself
		Role botHighestRole = selfMember.getRoles().stream().max(Comparator.comparing(Role::getPosition))
				.orElseThrow(NoSuchElementException::new);
		return member.getRoles().stream().anyMatch(role -> role.getPosition() > botHighestRole.getPosition());
	}

	public static boolean hasRoleByName(Member member, String roleName) {
		for (Role role : member.getRoles())
			if (role.getName().equalsIgnoreCase(roleName))
				return true;
		return false;
	}

	public boolean hasAdminPermission(Member member) {
		for (Role role : member.getRoles()) {
			if (role.hasPermission(Permission.ADMINISTRATOR)) {
				return true;
			}
		}
		return false;
	}

	public Role getOrCreateAdminRole(Guild guild) {
		List<Role> roles = guild.getRolesByName("admin", true);
		if (roles.isEmpty()) {
			return guild.createRole().setName("admin").setPermissions(Permission.ADMINISTRATOR).complete();
		} else {
			return roles.get(0);
		}
	}
}
