package Listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import Discord.CustomEmbedBuilder;
import Discord.Userlookup;
import EventService.SlashCommandInteractionService;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class onSlashCommandInteractionListener extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		String command = event.getName();
		if (command.equals("commands")) {
			new SlashCommandInteractionService().commands(event);
		} else if (command.equals("lookup")) {
			String message = event.getCommandString();
			new Userlookup().userLookUpHandler(message, event);
		}
	}

	public List<CommandData> queueForCommands() {
		List<CommandData> commandData = new ArrayList<>();
		commandData.add(Commands.slash("commands", "get a list of commands"));
		commandData.add(Commands.slash("lookup", "get information about a user by discord id.")
				.addOption(OptionType.STRING, "discordid", "Specify the user Unique discord id", true));
		return commandData;
	}

	@Override
	public void onGuildReady(@NotNull GuildReadyEvent event) {
		event.getGuild().updateCommands().addCommands(queueForCommands()).queue();
	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		event.getJDA().updateCommands().addCommands(queueForCommands()).queue();
	}
}