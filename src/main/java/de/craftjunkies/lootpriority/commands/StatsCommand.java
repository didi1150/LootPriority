package de.craftjunkies.lootpriority.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import de.craftjunkies.lootpriority.LootPriority;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		if (sender instanceof Player player) {
			player.sendMessage(Component.text("Kills: " + LootPriority.KILL_COUNT.get(player.getUniqueId()))
					.color(NamedTextColor.GOLD));
		}

		return false;
	}

}
